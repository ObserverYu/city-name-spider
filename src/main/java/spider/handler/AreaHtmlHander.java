package spider.handler;

import entity.City;

import java.util.Set;

/**
 * html处理器
 *  
 * @author YuChen
 * @date 2019/12/26 11:36
 **/
 
public interface AreaHtmlHander {

    Set<City> getEntity(String url,String html,String parentCode);
}
