package spider.handler;

import entity.City;

import java.util.Set;

/**
 * 处理镇级
 *  
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
 
public class TownHandler implements UrlToCityEntityHandler {

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
