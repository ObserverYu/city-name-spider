//package spider.handler.gc;
//
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import entity.City;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.nodes.Node;
//import spider.GetAreaMain;
//
//import java.util.Set;
//
///**
// * 处理镇级
// *
// * @author YuChen
// * @date 2019/12/24 9:50
// **/
//@Slf4j
//public class VillageHandler extends AbstractDefaultAreaHandler {
//
//    private static class Singlon{
//        public static final VillageHandler singlon = new VillageHandler();
//    }
//
//    public static VillageHandler getInstance(){
//        return Singlon.singlon;
//    }
//
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
//        return super.analysisHtml(url, parentCode, html, tryTimes,"villagetr");
//    }
//
//    /**
//     * 根据节点获取地区名
//     *
//     * @param areaHtml 城市html节点
//     * @return 地区名
//     * @author YuChen
//     * @date 2019/12/24 14:26
//     */
//    @Override
//    String getAreaName(Node areaHtml) {
//        Node oneLine = areaHtml.childNodes().get(2)
//                .childNodes().get(0);
//        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
//            return oneLine.childNodes().get(0).outerHtml();
//        }else {
//            return oneLine.outerHtml();
//        }
//    }
//
//    /**
//     * 获取点击url
//     *
//     * @param areaHtml 城市html节点
//     * @param url      当前解析的页面的url
//     * @return 当前节点城市点击后的url
//     * @author YuChen
//     * @date 2019/12/24 14:29
//     */
//    @Override
//    String mixUrl(Node areaHtml, String url) {
//        String href = areaHtml.childNodes().get(1).childNodes().get(0).attr("href");
//        if(StrUtil.isBlank(href)){
//            return href;
//        }
//        return GetAreaMain.DOMAIN +href;
//    }
//
//    /**
//     * 获取typeCode
//     *
//     * @param areaNode 城市html节点
//     * @return typeCode
//     * @author YuChen
//     * @date 2019/12/24 15:10
//     */
//    @Override
//    String getTypeCode(Node areaNode) {
//        Node oneLine = areaNode.childNodes().get(1)
//                .childNodes().get(0);
//        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
//            return oneLine.childNodes().get(0).outerHtml();
//        }else {
//            return oneLine.outerHtml();
//        }
//    }
//
//    /**
//     * 从节点中获取城市code
//     *
//     * @param areaHtml 城市html节点
//     * @return 城市code
//     * @author YuChen
//     * @date 2019/12/24 14:29
//     */
//    @Override
//    String getCode(Node areaHtml) {
//        Node oneLine = areaHtml.childNodes().get(0)
//                .childNodes().get(0);
//        if(CollectionUtil.isNotEmpty(oneLine.childNodes())){
//            return oneLine.childNodes().get(0).outerHtml();
//        }else {
//            return oneLine.outerHtml();
//        }
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
//        return false;
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
//        return null;
//    }
//}
