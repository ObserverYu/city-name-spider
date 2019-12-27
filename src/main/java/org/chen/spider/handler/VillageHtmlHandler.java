package org.chen.spider.handler;

import cn.hutool.core.collection.CollectionUtil;
import org.chen.spider.GetAreaMain;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 省页面处理器
 *  
 * @author YuChen
 * @date 2019/12/26 15:04
 **/
 
public class VillageHtmlHandler extends AbstarctAreaHtmlHander {

    private static class Singlon{
        private static final VillageHtmlHandler SINGLON = new VillageHtmlHandler();
    }

    public static VillageHtmlHandler getInstance(){
        return VillageHtmlHandler.Singlon.SINGLON;
    }

    /**
     * 从页面中抽取出每个地区的node实体
     *
     * @param doc jsoup doc对象
     * @return 该页面所有代表地区的node节点
     * @author YuChen
     * @date 2019/12/24 14:45
     */
    @Override
    public List<Node> getAreaNode(Document doc) {
        Elements provincetr = doc.getElementsByClass(GetAreaMain.CLASS_MARK_VILLAGE);
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
    @Override
    public String getAreaName(Node areaHtml) {
        Node oneLine = areaHtml.childNodes().get(2)
                .childNodes().get(0);
        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
            return oneLine.childNodes().get(0).outerHtml();
        }else {
            return oneLine.outerHtml();
        }
    }

    /**
     * 获取typeCode
     *
     * @param areaNode 城市html节点
     * @return typeCode
     * @author YuChen
     * @date 2019/12/24 15:10
     */
    @Override
    public String getTypeCode(Node areaNode) {
        Node oneLine = areaNode.childNodes().get(1)
                .childNodes().get(0);
        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
            return oneLine.childNodes().get(0).outerHtml();
        }else {
            return oneLine.outerHtml();
        }
    }

}
