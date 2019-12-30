package org.chen.spider.handler;

import org.chen.constant.SpiderConstant;
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
 
public class CountyHtmlHandler extends AbstractAreaHtmlHandler {

    private static class Singlon{
        private static final CountyHtmlHandler SINGLON = new CountyHtmlHandler();
    }

    public static CountyHtmlHandler getInstance(){
        return CountyHtmlHandler.Singlon.SINGLON;
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
    protected List<Node> getAreaNode(Document doc) {
        Elements provincetr = doc.getElementsByClass(SpiderConstant.CLASS_MARK_COUNTY);
        return new ArrayList<>(provincetr);
    }

}
