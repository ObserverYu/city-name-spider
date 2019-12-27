package org.chen.spider.handler;

import org.chen.spider.GetAreaMain;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
 
public class ProvinceHtmlHandler extends AbstarctAreaHtmlHander {

    private static class Singlon{
        private static final ProvinceHtmlHandler SINGLON = new ProvinceHtmlHandler();
    }

    public static ProvinceHtmlHandler getInstance(){
        return ProvinceHtmlHandler.Singlon.SINGLON;
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
        List<Node> res = new ArrayList<>();
        Elements provincetr = doc.getElementsByClass(GetAreaMain.CLASS_MARK_PROVINCE);
        for (Element areaLine : provincetr) {
            List<Node> raceAreaHtmls = areaLine.childNodes();
            for (Node raceAreaHtml : raceAreaHtmls) {
                List<Node> areaHtmls = raceAreaHtml.childNodes();
                res.addAll(areaHtmls);
            }
        }
        return res;
    }

    /**
     * 从节点中获取城市code
     *
     * @param areaHtml 城市html节点
     * @return 城市code
     * @author YuChen
     * @date 2019/12/24 14:29
     */
    @Override
    public String getCode(Node areaHtml) {
        String href = areaHtml.attr("href");
        return href.substring(0,2);
    }

    /**
     * 根据节点获取省名
     *
     * @param areaHtml 城市html节点
     * @return 地区名
     * @author YuChen
     * @date 2019/12/24 14:26
     */
    @Override
    public String getAreaName(Node areaHtml) {
        return areaHtml.childNodes().get(0).outerHtml();
    }

    /**
     * 获取该节点点击url
     *
     * @param areaHtml 节点
     * @param url 当前页面url
     * @return 该节点代表城市的url
     * @author YuChen
     * @date 2019/12/24 14:29
     */
    @Override
    public String getLinkUrl(Node areaHtml, String url) {
        String href = areaHtml.attr("href");
        return GetAreaMain.DOMAIN+href;
    }
}
