package spider.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import entity.City;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import spider.GetAreaMain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 抽象类,通用的一些方法抽象出来
 *
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
@Slf4j
public abstract class AbstractDefaultAreaHandler implements UrlToCityEntityHandler {


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
    public Set<City> getEntity(String url, String parentCode) {
        String html = HttpUtil.get(url, CharsetUtil.CHARSET_GBK);
        return analysisHtml(url, parentCode, html, "provincetr");
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
    Set<City> analysisHtml(String url, String parentCode, String html, String... otherArgs) {
        Set<City> res = new HashSet<>();
        Document doc = Jsoup.parse(html);
        List<Node> areaNodes = getAreaNode(doc, otherArgs);
        if(CollectionUtil.isNotEmpty(areaNodes)){
            for (Node areaNode : areaNodes) {
                res.add(nodeToCityEntity(areaNode, parentCode, url));
            }
        }else {
            log.error(html);
            log.error("===============限流url==================:"+url);
            GetAreaMain.errorUrl.add(url);
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
        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
            return oneLine.childNodes().get(0).outerHtml();
        }else {
            return oneLine.outerHtml();
        }
    }

/*    *//**
     * 获取点击url
     *
     * @param areaHtml 城市html节点
     * @param url      当前解析的页面的url
     * @return 当前节点城市点击后的url
     * @author YuChen
     * @date 2019/12/24 14:29
     *//*
    String mixUrl(Node areaHtml, String url) {
        String href = areaHtml.childNodes().get(1).childNodes().get(0).attr("href");
        if(StrUtil.isBlank(href)){
            return href;
        }
        return GetAreaMain.DOMAIN +href;
    }*/

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
        if(StrUtil.isBlank(href)){
            return href;
        }
        String[] split = url.split("/");
        int lastfileLengh = split[split.length-1].length();
        url = url.substring(0,url.length()-lastfileLengh);
        return url +href;
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
        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
            return oneLine.childNodes().get(0).outerHtml();
        }else {
            return oneLine.outerHtml();
        }
    }

}
