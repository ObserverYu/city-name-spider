package org.chen.spider.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.chen.entity.City;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.util.*;

/**
 * 默认的处理器
 *  
 * @author YuChen
 * @date 2019/12/26 11:38
 **/

@Slf4j
public abstract class AbstractAreaHtmlHandler implements AreaHtmlHandler {

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
    @Override
    public Collection<City> getEntity(String url, String html, String parentCode) {
        Set<City> res = null;
        Document doc = Jsoup.parse(html);
        // 获取到全部的节点
        List<Node> areaNodes = getAreaNode(doc);
        if (CollectionUtil.isNotEmpty(areaNodes)) {
            res = new HashSet<>();
            for (Node areaNode : areaNodes) {
                res.add(nodeToCityEntity(areaNode, parentCode, url));
            }
        } else {
            log.warn("未从html获取到节点,html:{}",html);
        }
        if(CollectionUtil.isEmpty(res)){
            log.warn("未从节点中获取到实体,url:{},html:{}",url,html);
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
    protected City nodeToCityEntity(Node areaNode, String parentCode, String url) {
        String areaName = getAreaName(areaNode);
        String areaUrl = getLinkUrl(areaNode, url);
        String code = getCode(areaNode);
        String typeCode = getTypeCode(areaNode);
        Integer level = getLevel();
        return new City(code, parentCode, areaName, typeCode, areaUrl,level);
    }

    /**
     * 从页面中抽取出每个地区的node实体
     *
     * @param doc       jsoup doc对象
     * @return 该页面所有代表地区的node节点
     * @author YuChen
     * @date 2019/12/24 14:45
     */
    protected abstract List<Node> getAreaNode(Document doc);

    /**
     * 根据节点获取地区名
     *
     * @param areaHtml 城市html节点
     * @return 地区名
     * @author YuChen
     * @date 2019/12/24 14:26
     */
    protected String getAreaName(Node areaHtml) {
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
    protected String getLinkUrl(Node areaHtml, String url) {
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
    protected String getTypeCode(Node areaNode) {
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
    protected String getCode(Node areaHtml) {
        Node oneLine = areaHtml.childNodes().get(0)
                .childNodes().get(0);
        if (CollectionUtil.isNotEmpty(oneLine.childNodes())) {
            return oneLine.childNodes().get(0).outerHtml();
        } else {
            return oneLine.outerHtml();
        }
    }

    /**
     * 获取该处理器处理的url的等级
     *
     * @return 获取该处理器处理的url的等级
     * @author YuChen
     * @date 2019/12/30 16:06
     */
    @Override
    public abstract Integer getLevel();
}
