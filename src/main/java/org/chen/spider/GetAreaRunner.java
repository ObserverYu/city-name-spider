package org.chen.spider;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.extern.slf4j.Slf4j;
import org.chen.constant.SpiderConstant;
import org.chen.entity.City;
import org.chen.spider.dispater.GetAreaDispatcher;

import java.util.Collection;
import java.util.concurrent.*;

/**
 * 从国家统计局官网获取最新的省市区信息
 *
 * @author YuChen
 * @date 2019/12/23 17:13
 **/
@Slf4j
public class GetAreaRunner {

	/**
	 * 任务是否完成标识 防止网络问题阻塞在提交省级任务之前导致任务未全部提交
	 */
	public static volatile boolean PROVINCE_FINISHED = false;

	/**
	 * 被限流的url
	 */
	public static ConcurrentHashSet<String> errorUrl = new ConcurrentHashSet<>();


	public static void main(String[] args) throws InterruptedException {
		// runWithThreadPollTest();
		// test();
		GetAreaDispatcher dispatcher = build();
		dispatcher.dispatch(getMainUrl(),"0");
		CityToMySQLInputer.inputToMySQL(dispatcher);
		ThreadPoolExecutor threadPoolExecutor = dispatcher.getThreadPoolExecutor();
		monitorAndPrint(dispatcher);
		while(threadPoolExecutor.getPoolSize() == 0){
			dispatcher.setFinished(true);
			Thread.sleep(60*1000);
			threadPoolExecutor.shutdown();
		}
	}

	/**
	 * 多线程运行测试
	 *
	 * @author YuChen
	 * @date 2019/12/30 9:56
	 */
	public static void runWithThreadPollTest() throws InterruptedException {
		GetAreaDispatcher dispatcher = build();
		// 省的父code为0
		dispatcher.dispatch(getMainUrl(), "0");
		// 可能会阻塞在提交某个省的任务之前  导致任务不能全部执行(其他级别的不会,因为提交操作在run方法中)
		// 通过一个标记保证入口处的所有省下面的市页面的任务都已经提交完成  之后的新任务提交过程都在已提交的任务之中
		while (!GetAreaRunner.PROVINCE_FINISHED) {
			Thread.sleep(2000);
		}
		monitorAndPrint(dispatcher);
	}

	public static void monitorAndPrint(GetAreaDispatcher dispatcher){
		ThreadPoolExecutor threadPoolExecutor = dispatcher.getThreadPoolExecutor();
		Collection<City> allArea = dispatcher.getCollector();
		// 保证所有省下面的市页面任务提交后  新任务都是在旧任务中被提交的
		// 因此  如果线程池中的线程数不为0   则表示仍然有任务没有完成   则仍然可能有新的任务提交
		while (threadPoolExecutor.getPoolSize() != 0) {
			try {
				Thread.sleep(10 * 1000);
				long taskCount = threadPoolExecutor.getQueue().size();
				log.info("task count:" + String.valueOf(taskCount));
				int size = allArea.size();
				log.info("city count:" + String.valueOf(size));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 构造一个默认的任务提交器
	 *
	 * @return 默认的任务提交器
	 * @author YuChen
	 * @date 2019/12/30 9:55
	 */
	public static GetAreaDispatcher build() {
		// 将核心线程数设置为0  为了能够通过当前运行的线程数来判断所有任务是否都已经执行完成
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3,
				30L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
		threadPoolExecutor.allowCoreThreadTimeOut(true);
		LinkedBlockingDeque<City> allArea = new LinkedBlockingDeque<>();
		return new GetAreaDispatcher(allArea, threadPoolExecutor, true, 3, 10000,4);
	}

	/**
	 * 获取主url的方法
	 *
	 * @return 入口url
	 * @author YuChen
	 * @date 2019/12/23 17:42
	 */
	private static String getMainUrl() {
		return SpiderConstant.ENTER_URL;
	}
}
