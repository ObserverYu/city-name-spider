package spider.handler;

import cn.hutool.http.HttpUtil;
import entity.City;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * 处理市级
 *
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
@Slf4j
public class CityHandler extends AbstractDefaultAreaHandler {

    private static class Singlon {
        private static final CityHandler singlon = new CityHandler();
    }

    public static CityHandler getInstance() {
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
        return super.analysisHtml(url, parentCode, html, "citytr");
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
