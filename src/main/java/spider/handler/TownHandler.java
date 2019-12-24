package spider.handler;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import entity.City;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;

import java.util.Set;

/**
 * 处理镇级
 *  
 * @author YuChen
 * @date 2019/12/24 9:50
 **/

@Slf4j
public class TownHandler extends AbstractDefaultAreaHandler {

    private static class Singlon{
        public static final TownHandler singlon = new TownHandler();
    }

    public static TownHandler getInstance(){
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
        HttpRequest get = HttpUtil.createGet(url);
        HttpResponse execute = get.execute();
        String html;
        try {
            html = new String(execute.bodyBytes(), "GBK");
        } catch (Exception e) {
            log.error("转码失败",e);
            throw new RuntimeException("转码失败");
        }
        return super.analysisHtml(url, parentCode, html, "towntr");

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
        return null;
    }
}
