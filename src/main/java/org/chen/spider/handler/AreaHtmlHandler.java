package org.chen.spider.handler;

import org.chen.entity.City;

import java.util.Collection;

/**
 * html处理器
 *  
 * @author YuChen
 * @date 2019/12/26 11:36
 **/
 
public interface AreaHtmlHandler {

    /**
    * 根据html获取实体
    *
    * @param url 需要解析的页面url
    * @param html 页面的html
    * @param parentCode 当前页面的父地区code
    * @return 当前页面解析出的地区实体
    * @author YuChen
    * @date 2019/12/30 9:58
    */
    Collection<City> getEntity(String url, String html, String parentCode);

    /**
    * 获取该处理器处理的url的等级
    *
    * @return 获取该处理器处理的url的等级
    * @author YuChen
    * @date 2019/12/30 16:06
    */
    Integer getLevel();
}
