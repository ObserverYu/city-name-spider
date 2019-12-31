package org.chen.spider.dispater;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.chen.constant.SpiderConstant;
import org.chen.entity.City;
import org.chen.spider.GetAreaRunner;
import org.chen.spider.handler.*;
import org.chen.spider.task.HandlerUrlTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 国家统计局爬取省市地区信息任务管理器
 *
 * @author YuChen
 * @date 2019/12/26 13:51
 **/

@Slf4j
public class GetAreaDispatcher {

	/**
	 * 收集器  需要线程安全
	 */
	private Collection<City> collector;

	/**
	 * 线程池
	 */
	private ThreadPoolExecutor threadPoolExecutor;

	/**
	 * 是否重试
	 */
	private boolean tryAgain;

	/**
	 * 最大重试次数
	 */
	private Integer maxTryTimes;

	/**
	 * 重试等待时间(毫秒)
	 */
	private int waitMills;

	/**
	 * 最细行政级别
	 */
	private Integer level;
	/**
	 * 任务是否都已完成
	 */
	private volatile boolean finished;

	public GetAreaDispatcher(Collection<City> collector, ThreadPoolExecutor threadPoolExecutor, boolean tryAgain, Integer maxTryTimes, int waitMills) {
		this.collector = collector;
		this.threadPoolExecutor = threadPoolExecutor;
		this.tryAgain = tryAgain;
		this.maxTryTimes = maxTryTimes;
		this.waitMills = waitMills;
		this.finished = false;
		this.level = 5;
	}

	public GetAreaDispatcher(Collection<City> collector, ThreadPoolExecutor threadPoolExecutor, boolean tryAgain, Integer maxTryTimes, int waitMills,int level) {
		this.collector = collector;
		this.threadPoolExecutor = threadPoolExecutor;
		this.tryAgain = tryAgain;
		this.maxTryTimes = maxTryTimes;
		this.waitMills = waitMills;
		this.finished = false;
		this.level = level;
	}


	/**
	 * 开始爬取调度
	 *
	 * @param
	 * @return
	 * @author YuChen
	 * @date 2019/12/24 9:49
	 */
	public void dispatch(String url, String parentCode) {
		Collection<City> res = getFinalEntity(url, parentCode);
		if (CollectionUtil.isEmpty(res)) {
			log.info("实体为空 url:{}",url);
			return;
		}
		// 收集结果
		collector.addAll(res);
		// 根据结果初始化新的任务并提交
		for (City city : res) {
			String sonUrl = city.getUrl();
			Integer sonLevel = city.getLevel()+1;
			if (StrUtil.isNotBlank(sonUrl) && sonLevel <= level) {
				if (threadPoolExecutor != null) {
					// 如果传入了线程池  则用线程池
					HandlerUrlTask task = new HandlerUrlTask(sonUrl, city.getCode(), this);
					threadPoolExecutor.submit(task);
				} else {
					// todo 单线程 分支再入栈  存在溢出的可能
					dispatch(sonUrl, city.getCode());
				}
			}
		}
		GetAreaRunner.PROVINCE_FINISHED = true;
	}

	/**
	 * 解析html,获取实体
	 *
	 * @param url        要解析的url
	 * @param parentCode 该url父地区code
	 * @return 解析出来的实体
	 * @author YuChen
	 * @date 2019/12/30 10:00
	 */
	private Collection<City> getFinalEntity(String url, String parentCode) {
		String html = tryGetHtml(url);
		if (StrUtil.isBlank(html)) {
			log.error("未获取到正确的html,url:{}", url);
			return null;
		}
		// 根据页面选择处理器
		AreaHtmlHandler areaHtmlHandler = getHandler(html);
		if (areaHtmlHandler == null) {
			log.error("没有合适的页面处理器,url:{}", url);
			return null;
		}
		if(areaHtmlHandler.getLevel() > level){
			return null;
		}
		return areaHtmlHandler.getEntity(url, html, parentCode);
	}

	/**
	 * 选择处理器
	 *
	 * @param html 页面内容
	 * @return 根据html的内容选择对应类型的处理器
	 * @author YuChen
	 * @date 2019/12/26 14:13
	 */
	public AreaHtmlHandler getHandler(String html) {
		Document doc = Jsoup.parse(html);
		Elements allElements = doc.getAllElements();
		for (Element element : allElements) {
			if (element.hasClass(SpiderConstant.CLASS_MARK_VILLAGE)) {
				return VillageHtmlHandler.getInstance();
			}
			if (element.hasClass(SpiderConstant.CLASS_MARK_TOWN)) {
				return TownHtmlHandler.getInstance();
			}
			if (element.hasClass(SpiderConstant.CLASS_MARK_COUNTY)) {
				return CountyHtmlHandler.getInstance();
			}
			if (element.hasClass(SpiderConstant.CLASS_MARK_CITY)) {
				return CityHtmlHandler.getInstance();
			}
			if (element.hasClass(SpiderConstant.CLASS_MARK_PROVINCE)) {
				return ProvinceHtmlHandler.getInstance();
			}
		}
		return null;
	}

	/**
	 * 根据url获取html字符串
	 *
	 * @param url 页面的url
	 * @return 网络io获取到的html
	 * @author YuChen
	 * @date 2019/12/26 14:41
	 */
	private String tryGetHtml(String url) {
		String html = null;
		int tryTimes = 1;
		if (tryAgain) {
			tryTimes = maxTryTimes;
		}
		for (int i = 1; i <= tryTimes; i++) {
			html = getRespondBodyString(url);
			if (!isLegal(html)) {
				try {
					Thread.sleep(waitMills);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i == tryTimes) {
					log.error("获取 {} 次后依旧失败,url:{},html {}:", i, url, html);
					GetAreaRunner.errorUrl.add(url);
					return null;
				}
			} else {
				break;
			}
		}
		return html;
	}

	/**
	 * 获取respond body字符串
	 *
	 * @param url 页面url
	 * @return respond body字符串
	 * @author YuChen
	 * @date 2019/12/26 14:41
	 */
	private String getRespondBodyString(String url) {
		HttpRequest get = HttpUtil.createGet(url);
		get.header("Cookie", SpiderConstant.COOKIE);
		get.header("User-Agent", SpiderConstant.USER_AGENT);
		get.header("Accept", SpiderConstant.ACCEPT);
		HttpResponse execute = get.execute();
		String html;
		try {
			html = new String(execute.bodyBytes(), "GBK");
		} catch (Exception e) {
			log.error("转码失败,url:{}", url, e);
			throw new RuntimeException("html转码失败");
		}
		return html;
	}

	/**
	 * 获取到的html是否合法
	 *
	 * @param html html内容
	 * @return 获取到的页面是否正常(有没有被限流)
	 * @author YuChen
	 * @date 2019/12/26 14:07
	 */
	private boolean isLegal(String html) {
		return html.contains("charset=gb2312");
	}

	public Collection<City> getCollector() {
		return collector;
	}

	public void setCollector(Collection<City> collector) {
		this.collector = collector;
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	public boolean isTryAgain() {
		return tryAgain;
	}

	public void setTryAgain(boolean tryAgain) {
		this.tryAgain = tryAgain;
	}

	public Integer getMaxTryTimes() {
		return maxTryTimes;
	}

	public void setMaxTryTimes(Integer maxTryTimes) {
		this.maxTryTimes = maxTryTimes;
	}

	public int getWaitMills() {
		return waitMills;
	}

	public void setWaitMills(int waitMills) {
		this.waitMills = waitMills;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
