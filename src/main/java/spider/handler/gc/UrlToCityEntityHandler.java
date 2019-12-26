package spider.handler.gc;

import entity.City;

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
    * 处理器入口
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/26 10:41
    */
    void handle(String url, String parentCode, Set<City> collector, ThreadPoolExecutor threadPoolExecutor,boolean tryAgain,Integer maxTryTime,int waitMills);

    /**
     * 抽取/获得当前页面的地区信息
     *
     * @param
     * @return
     * @author YuChen
     * @date 2019/12/23 17:45
     */
    Set<City> getEntity(String url, String parentCode, Integer tryTimes);

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

    /**
    * 获取html字符串
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/26 10:38
    */
    String getHtml(String url);


}
