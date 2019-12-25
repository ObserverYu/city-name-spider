package spider.handler;

import cn.hutool.core.util.StrUtil;
import entity.City;
import spider.GetAreaMain;
import spider.HandlerUrlTask;

import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 根据父地区信息获取子信息并返回实体类
 *  
 * @author YuChen
 * @date 2019/12/23 17:33
 **/
 
public interface UrlToCityEntityHandler {

    /**
    * 处理url
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/24 9:49
    */
    default void handle(String url, String parentCode, Set<City> collector, ThreadPoolExecutor threadPoolExecutor){
        Set<City> res = getEntity(url,parentCode);
        // 收集结果
        collector.addAll(res);
        // 如果该页面有子页面 则对子页面进行处理
        UrlToCityEntityHandler sonHandler = getSonHandler();
        if(sonHandler != null){
            for(City city:res){
                String sonUrl = city.getUrl();
                if(StrUtil.isNotBlank(sonUrl)){
                    if(threadPoolExecutor != null){
                        // 如果传入了线程池  则用线程池
                        HandlerUrlTask task = new HandlerUrlTask(sonUrl,city.getCode(),collector,sonHandler,threadPoolExecutor);
                        threadPoolExecutor.submit(task);
                    }else {
                        sonHandler.handle(sonUrl,city.getCode(),collector,null);
                    }
                }
            }
            GetAreaMain.PROVINCE_FINISHED = true;
        }
    }

    /**
     * 抽取/获得当前页面的地区信息
     *
     * @param
     * @return
     * @author YuChen
     * @date 2019/12/23 17:45
     */
    Set<City> getEntity(String url, String parentCode);

    /**
    * 当前页面条目是否可点击(有子条目)
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/23 17:44
    */
    Boolean hasSonPage();

    /**
    * 获取子处理器
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/23 17:51
    */
    UrlToCityEntityHandler getSonHandler();


}
