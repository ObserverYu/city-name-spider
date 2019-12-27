package org.chen.spider.task;

import org.chen.spider.dispater.GetAreaDispatcher;

/**
 * 多线程处理任务
 *  
 * @author YuChen
 * @date 2019/12/24 10:25
 **/
 
public class HandlerUrlTask implements Runnable {

    private String url;

    private String parentCode;

    private GetAreaDispatcher dispatcher;

    public HandlerUrlTask(String url, String parentCode, GetAreaDispatcher dispatcher) {
        this.url = url;
        this.parentCode = parentCode;
        this.dispatcher = dispatcher;
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
        dispatcher.dispatch(url,parentCode);
    }
}
