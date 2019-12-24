package spider.handler;

import cn.hutool.http.HttpUtil;
import entity.City;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;

import java.util.Set;

/**
 * 处理市级
 *  
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
@Slf4j
public class CityHandler extends AbstractDefaultAreaHandler {

    private static class Singlon{
        private static final CityHandler singlon = new CityHandler();
    }

    public static CityHandler getInstance(){
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
        return super.analysisHtml(url,parentCode,html,"citytr");
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
        String account = areaHtml.outerHtml();
        System.out.println(account);
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return DistrictHandler.getInstance();
    }
}
