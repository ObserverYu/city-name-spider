package spider.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import entity.City;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import spider.GetAreaMain;
import spider.HandlerUrlTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 抽象类,通用的一些方法抽象出来
 *
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
@Slf4j
public abstract class AbstractDefaultAreaHandler implements UrlToCityEntityHandler {


    /**
     * 处理url
     *
     * @param
     * @return
     * @author YuChen
     * @date 2019/12/24 9:49
     */
    @Override
    public void handle(String url, String parentCode, Set<City> collector, ThreadPoolExecutor threadPoolExecutor,boolean tryAgain,Integer maxTryTimes,int waitMills) {
        int tryTimes = 1;
        if(tryAgain){
            tryTimes = maxTryTimes;
        }
        String html;
        for (int i = 1; i <= tryTimes; i++) {
            html = getHtml(url);
            if(!isLegal(html)){
                try {
                    Thread.sleep(waitMills);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i == tryTimes){
                    log.error("获取 {} 次后依旧失败,url:{}",i,url);
                    GetAreaMain.errorUrl.add(url);
                }
            }else {
                break;
            }
        }
        Set<City> res = getEntity(url, parentCode, 1);
        // 收集结果
        collector.addAll(res);
        // 如果该页面有子页面 则对子页面进行处理
        UrlToCityEntityHandler sonHandler = getSonHandler();
        if (sonHandler != null) {
            for (City city : res) {
                String sonUrl = city.getUrl();
                if (StrUtil.isNotBlank(sonUrl)) {
                    if (threadPoolExecutor != null) {
                        // 如果传入了线程池  则用线程池
                        HandlerUrlTask task = new HandlerUrlTask(sonUrl, city.getCode(), collector, sonHandler, threadPoolExecutor);
                        threadPoolExecutor.submit(task);
                    } else {
                        sonHandler.handle(sonUrl, city.getCode(), collector, null);
                    }
                }
            }
            GetAreaMain.PROVINCE_FINISHED = true;
        }

    }

    /**
    * 判断本次请求获取的html是否是合法的(没有被限流)
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/26 10:58
    */
    public Boolean isLegal(String html){
        return true;
    }


    /**
         * 获取html字符串
         *
         * @param url@return
         * @author YuChen
         * @date 2019/12/26 10:38
         */
    @Override
    public String getHtml(String url) {
        HttpRequest get = HttpUtil.createGet(url);
        get.header("Cookie", GetAreaMain.COOKIE);
        get.header("User-Agent",GetAreaMain.USER_AGENT);
        get.header("Accept",GetAreaMain.ACCEPT);
        HttpResponse execute = get.execute();
        String html;
        try {
            html = new String(execute.bodyBytes(), "GBK");
        } catch (Exception e) {
            log.error("转码失败",e);
            throw new RuntimeException("转码失败");
        }
        return html;
    }


    /**
     * 抽取/获得当前页面的地区信息
     *
     * @param parentCode 当前页面生成的city的父code
     * @param url        当前解析的页面的url
     * @return 最终生成的city实体类集合
     * @author YuChen
     * @date 2019/12/23 17:45
     */
    @Override
    public Set<City> getEntity(String url, String parentCode, String html) {
        String pageType = choosePageType(html);
        return analysisHtml(url, parentCode, html, pageType);
    }

    /**
    * 根据页面信息判断页面类型
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/26 11:02
    */
    public String choosePageType(String html){
        return null;
    }


    /**
     * 处理html
     *
     * @param parentCode 当前页面生成的city的父code
     * @param url        当前解析的页面的url
     * @param html       html字符串
     * @param otherArgs  用于解析html的其他参数
     * @return 最终生成的city实体类集合
     * @author YuChen
     * @date 2019/12/24 14:44
     */
    Set<City> analysisHtml(String url, String parentCode, String html, Integer tryTimes, String... otherArgs) {
        Set<City> res = new HashSet<>();
        String pageType = choosePageType(html);
        Document doc = Jsoup.parse(html);
        List<Node> areaNodes = getAreaNode(doc, pageType);
        if (CollectionUtil.isNotEmpty(areaNodes)) {
            for (Node areaNode : areaNodes) {
                res.add(nodeToCityEntity(areaNode, parentCode, url));
            }
        } else {

        }
        return res;
    }

    /**
     * 将jsoup的节点转换为city实体
     *
     * @param areaNode   使用jsoup得到的每个城市的node节点
     * @param parentCode 当前解析的city的父code
     * @return 该节点转换出的实体
     * @author YuChen
     * @date 2019/12/24 14:44
     */
    City nodeToCityEntity(Node areaNode, String parentCode, String url) {
        String areaName = getAreaName(areaNode);
        String areaUrl = mixUrl(areaNode, url);
        String code = getCode(areaNode);
        String typeCode = getTypeCode(areaNode);
        return new City(code, parentCode, areaName, typeCode, areaUrl);
    }


    /**
     * 从页面中抽取出每个地区的node实体
     *
     * @param doc       jsoup doc对象
     * @param otherArgs 用于解析html的其他参数
     * @return 该页面所有代表地区的node节点
     * @author YuChen
     * @date 2019/12/24 14:45
     */
    List<Node> getAreaNode(Document doc, String... otherArgs) {
        Elements provincetr = doc.getElementsByClass(otherArgs[0]);
        if (provincetr.isEmpty() && otherArgs.length > 1) {
            provincetr = doc.getElementsByClass(otherArgs[1]);
        }
        return new ArrayList<>(provincetr);
    }

    /**
     * 根据节点获取地区名
     *
     * @param areaHtml 城市html节点
     * @return 地区名
     * @author YuChen
     * @date 2019/12/24 14:26
     */
    String getAreaName(Node areaHtml) {
        Node oneLine = areaHtml.childNodes().get(1)
                .childNodes().get(0);
        if (CollectionUtil.isNotEmpty(oneLine.childNodes())) {
            return oneLine.childNodes().get(0).outerHtml();
        } else {
            return oneLine.outerHtml();
        }
    }

    /**
     * 获取点击url
     *
     * @param areaHtml 城市html节点
     * @param url      当前解析的页面的url
     * @return 当前节点城市点击后的url
     * @author YuChen
     * @date 2019/12/24 14:29
     */
    String mixUrl(Node areaHtml, String url) {
        String href = areaHtml.childNodes().get(1).childNodes().get(0).attr("href");
        if (StrUtil.isBlank(href)) {
            return href;
        }
        String[] split = url.split("/");
        int lastfileLengh = split[split.length - 1].length();
        url = url.substring(0, url.length() - lastfileLengh);
        return url + href;
    }

    /**
     * 获取typeCode
     *
     * @param areaNode 城市html节点
     * @return typeCode
     * @author YuChen
     * @date 2019/12/24 15:10
     */
    String getTypeCode(Node areaNode) {
        return "";
    }

    /**
     * 从节点中获取城市code
     *
     * @param areaHtml 城市html节点
     * @return 城市code
     * @author YuChen
     * @date 2019/12/24 14:29
     */
    String getCode(Node areaHtml) {
        Node oneLine = areaHtml.childNodes().get(0)
                .childNodes().get(0);
        if (CollectionUtil.isNotEmpty(oneLine.childNodes())) {
            return oneLine.childNodes().get(0).outerHtml();
        } else {
            return oneLine.outerHtml();
        }
    }

}
