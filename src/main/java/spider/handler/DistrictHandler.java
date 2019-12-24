package spider.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import entity.City;
import org.jsoup.nodes.Node;
import spider.GetAreaMain;

import java.util.Set;

/**
 * 处理区县级
 *  
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
 
public class DistrictHandler extends AbstractDefaultAreaHandler {

    private static class Singlon{
        private static final DistrictHandler singlon = new DistrictHandler();
    }

    public static DistrictHandler getInstance(){
        return Singlon.singlon;
    }

    /**
     * 抽取/获得当前页面的地区信息
     *
     * @param parentCode@return
     * @author YuChen
     * @date 2019/12/23 17:45
     */
    @Override
    public Set<City> getEntity(String url, String parentCode) {
        String html = HttpUtil.get(url);
        return super.analysisHtml(url,parentCode,html,"countytr");
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
    String getCode(Node areaHtml) {
        Node oneLine = areaHtml.childNodes().get(0)
                .childNodes().get(0);
        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
            return oneLine.childNodes().get(0).outerHtml();
        }else {
            return oneLine.outerHtml();
        }
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
    String getAreaName(Node areaHtml) {
        Node oneLine = areaHtml.childNodes().get(1)
                .childNodes().get(0);
        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
            return oneLine.childNodes().get(0).outerHtml();
        }else {
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
    @Override
    String mixUrl(Node areaHtml, String url) {
        String href = areaHtml.childNodes().get(1).childNodes().get(0).attr("href");
        return GetAreaMain.domain +href;
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
    String getTypeCode(Node areaNode) {
        return "";
    }

    /**
     * 当前页面条目是否可点击(有子条目)
     *
     * @return
     * @author YuChen
     * @date 2019/12/23 17:44
     */
    @Override
    public Boolean hasSonPage() {
        return null;
    }

    /**
     * 获取子处理器
     *
     * @return
     * @author YuChen
     * @date 2019/12/23 17:51
     */
    @Override
    public UrlToCityEntityHandler getSonHandler() {
        return TownHandler.getInstance();
    }
}
