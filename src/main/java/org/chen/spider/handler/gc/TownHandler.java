//package spider.handler.gc;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import City;
//import lombok.extern.slf4j.Slf4j;
//import GetAreaRunner;
//
//import java.util.Set;
//
///**
// * 处理镇级
// *
// * @author YuChen
// * @date 2019/12/24 9:50
// **/
//
//@Slf4j
//public class TownHandler extends AbstractDefaultAreaHandler {
//
//    private static class Singlon{
//        public static final TownHandler singlon = new TownHandler();
//    }
//
//    public static TownHandler getInstance(){
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
//        get.header("Cookie", GetAreaRunner.COOKIE);
//        get.header("User-Agent",GetAreaRunner.USER_AGENT);
//        get.header("Accept",GetAreaRunner.ACCEPT);
//        HttpResponse execute = get.execute();
//        String html;
//        try {
//            html = new String(execute.bodyBytes(), "GBK");
//        } catch (Exception e) {
//            log.error("转码失败",e);
//            throw new RuntimeException("转码失败");
//        }
//        return super.analysisHtml(url, parentCode, html, tryTimes,"towntr");
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
//        return VillageHandler.getInstance();
//    }
//}
