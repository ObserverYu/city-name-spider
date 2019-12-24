package spider.handler;

import cn.hutool.http.HttpUtil;
import entity.City;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;

import java.util.Set;

/**
 * 处理省级
 *  
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
@Slf4j
public class ProvinceHandler extends AbstractDefaultAreaHandler {

    private static class Singlon{
        private static final ProvinceHandler SINGLON = new ProvinceHandler();
    }

    public static ProvinceHandler getInstance(){
        return Singlon.SINGLON;
    }

    /**
     * 当前页面条目是否可点击(有子条目)
     *
     * @return 是否有子条目
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
     * @return 子处理器
     * @author YuChen
     * @date 2019/12/23 17:51
     */
    @Override
    public UrlToCityEntityHandler getSonHandler() {
        return CityHandler.getInstance();
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
        return super.analysisHtml(url,parentCode,html,"provincetr");
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
    public String mixUrl(Node areaHtml, String url) {
        String href = areaHtml.attr("href");
        return "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/"+href;
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


}
