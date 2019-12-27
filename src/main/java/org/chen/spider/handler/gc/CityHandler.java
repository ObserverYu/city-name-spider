//package spider.handler.gc;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import City;
//import lombok.extern.slf4j.Slf4j;
//import GetAreaMain;
//
//import java.util.Set;
//
///**
// * 处理市级
// *
// * @author YuChen
// * @date 2019/12/24 9:50
// **/
//@Slf4j
//public class CityHandler extends AbstractDefaultAreaHandler {
//
//    private static class Singlon {
//        private static final CityHandler singlon = new CityHandler();
//    }
//
//    public static CityHandler getInstance() {
//        return Singlon.singlon;
//    }
//
//    /**
//     * 抽取/获得当前页面的地区信息
//     *
//     * @param parentCode@return
//     * @author YuChen
//     * @date 2019/12/23 17:45
//     */
//    @Override
//    public Set<City> getEntity(String url, String parentCode,Integer tryTimes) {
//        HttpRequest get = HttpUtil.createGet(url);
//        get.header("Cookie", GetAreaMain.COOKIE);
//        get.header("User-Agent",GetAreaMain.USER_AGENT);
//        get.header("Accept",GetAreaMain.ACCEPT);
//        HttpResponse execute = get.execute();
//        String html;
//        try {
//            html = new String(execute.bodyBytes(), "GBK");
//        } catch (Exception e) {
//            log.error("转码失败",e);
//            throw new RuntimeException("转码失败");
//        }
//        return super.analysisHtml(url, parentCode, html,tryTimes,"citytr");
//
//    }
//
//    /**
//     * 当前页面条目是否可点击(有子条目)
//     *
//     * @return
//     * @author YuChen
//     * @date 2019/12/23 17:44
//     */
//    @Override
//    public Boolean hasSonPage() {
//        return null;
//    }
//
//    /**
//     * 获取子处理器
//     *
//     * @return
//     * @author YuChen
//     * @date 2019/12/23 17:51
//     */
//    @Override
//    public UrlToCityEntityHandler getSonHandler() {
//        return DistrictHandler.getInstance();
//    }
//}
