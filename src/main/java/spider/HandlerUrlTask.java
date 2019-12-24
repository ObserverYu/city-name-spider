package spider;

import entity.City;
import spider.handler.UrlToCityEntityHandler;

import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 多线程处理任务
 *  
 * @author YuChen
 * @date 2019/12/24 10:25
 **/
 
public class HandlerUrlTask implements Runnable {

    private String url;

    private String parentCode;

    private Set<City> collector;

    private UrlToCityEntityHandler handler;

    private ThreadPoolExecutor threadPoolExecutor;

    public HandlerUrlTask(String url, String parentCode, Set<City> collector, UrlToCityEntityHandler handler, ThreadPoolExecutor threadPoolExecutor) {
        this.url = url;
        this.parentCode = parentCode;
        this.collector = collector;
        this.handler = handler;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        handler.handle(url,parentCode,collector,threadPoolExecutor);
    }
}
