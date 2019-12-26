package spider;

import cn.hutool.core.collection.ConcurrentHashSet;
import entity.City;
import lombok.extern.slf4j.Slf4j;
import spider.dispater.GetAreaDispatcher;
import spider.handler.CountyHtmlHandler;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 从国家统计局官网获取最新的省市区信息
 *  
 * @author YuChen
 * @date 2019/12/23 17:13
 **/
 @Slf4j
public class GetAreaMain {

    /**
     *   入口url
     */
    public static final String ENTER_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html";

    /**
     *   主路径
     */
    public static final String DOMAIN = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";

    /**
     *   任务是否完成标识 防止网络问题阻塞在提交省级任务之前导致任务未全部完成
     */
    public static volatile boolean PROVINCE_FINISHED = false;

    /**
     *   反爬虫  设置的heads
     */
    public static final String COOKIE = "_trs_uv=k4m16pij_6_115g; AD_RS_COOKIE=20080917";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";

    public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";

    /**
     *   被限流的url
     */
    public static final ConcurrentHashSet<String> errorUrl = new ConcurrentHashSet<>();

    /**
     *   省页面表格class名
     */
    public static final String CLASS_MARK_PROVINCE = "provincetr";
    /**
     *   市页面表格class名
     */
    public static final String CLASS_MARK_CITY = "provincetr";
    /**
     *   区县页面表格class名
     */
    public static final String CLASS_MARK_COUNTY = "countytr";
    /**
     *   镇街道页面表格class名
     */
    public static final String CLASS_MARK_TOWN = "towntr";
    /**
     *   村页面表格class名
     */
    public static final String CLASS_MARK_VILLAGE= "villagetr";

    /**
    * 获取主url的方法
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/23 17:42
    */
    private static String getMainUrl(){
        return ENTER_URL;
    }

    public static void main(String[] args) throws InterruptedException {
        //runWithThreadPoll();
        test();
    }

    public static void test(){
        String html = "";
        Set<City> district = CountyHtmlHandler.getInstance().getEntity("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42/4201.html", html,"42");
        System.out.println(district);
    }

    public static void runWithThreadPoll() throws InterruptedException {
        GetAreaDispatcher dispatcher = build();
        dispatcher.dispatch(getMainUrl(),"0");
        // todo 可能会阻塞在提交某个省的任务之前  导致任务不能全部执行(其他级别的不会,因为提交操作在run方法中)
        // 通过一个标记保证入口处的所有省下面的市页面的任务都已经提交完成  之后的新任务提交过程都在已提交的任务之中
        while(!GetAreaMain.PROVINCE_FINISHED){
            Thread.sleep(2000);
        }
        ThreadPoolExecutor threadPoolExecutor = dispatcher.getThreadPoolExecutor();
        ConcurrentHashSet<City> allArea = dispatcher.getCollector();
        // 保证所有省下面的市页面任务提交后  新任务都是在旧任务中被提交的
        // 因此  如果线程池中的线程数不为0   则表示仍然有任务没有完成   则仍然可能有新的任务提交
        while(threadPoolExecutor.getPoolSize() != 0){
            try {
                Thread.sleep(10*1000);
                long taskCount = threadPoolExecutor.getTaskCount();
                log.info("task count:"+String.valueOf(taskCount));
                int size = allArea.size();
                log.info("city count:"+String.valueOf(size));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(City city :allArea){
            System.out.println("name:"+city.getName()+"code:"+city.getCode());
        }
        System.out.println(allArea.size());
    }

    private static GetAreaDispatcher build(){
        // 将核心线程数设置为0  为了能够通过当前运行的线程数来判断所有任务是否都已经执行完成
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4,
                30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        ConcurrentHashSet<City> allArea = new ConcurrentHashSet<>();
        GetAreaDispatcher dispatcher = new GetAreaDispatcher(allArea, threadPoolExecutor, true, 3, 5000);
        return dispatcher;
    }
}
