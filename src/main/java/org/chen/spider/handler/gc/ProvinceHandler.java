//package spider.handler.gc;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import City;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.nodes.Node;
//import GetAreaRunner;
//
//import java.util.Set;
//
///**
// * 处理省级
// *
// * @author YuChen
// * @date 2019/12/24 9:50
// **/
//@Slf4j
//public class ProvinceHandler extends AbstractDefaultAreaHandler {
//
//    private static class Singlon{
//        private static final ProvinceHandler SINGLON = new ProvinceHandler();
//    }
//
//    public static ProvinceHandler getInstance(){
//        return Singlon.SINGLON;
//    }
//
//    /**
//     * 当前页面条目是否可点击(有子条目)
//     *
//     * @return 是否有子条目
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
//     * @return 子处理器
//     * @author YuChen
//     * @date 2019/12/23 17:51
//     */
//    @Override
//    public UrlToCityEntityHandler getSonHandler() {
//        return CityHandler.getInstance();
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
//        return super.analysisHtml(url, parentCode, html, tryTimes,"provincetr");
//    }
//
///*    *//**
//     * 从页面中抽取出每个地区的node实体
//     *
//     * @param doc       jsoup doc对象
//     * @param otherArgs 用于解析html的其他参数
//     * @return 该页面所有代表地区的node节点
//     * @author YuChen
//     * @date 2019/12/24 14:45
//     *//*
//    @Override
//    List<Node> getAreaNode(Document doc, String... otherArgs) {
//        List<Node> res = new ArrayList<>();
//        Elements provincetr = doc.getElementsByClass(otherArgs[0]);
//        for (Element areaLine : provincetr) {
//            List<Node> raceAreaHtmls = areaLine.childNodes();
//            for (Node raceAreaHtml : raceAreaHtmls) {
//                List<Node> areaHtmls = raceAreaHtml.childNodes();
//                res.addAll(areaHtmls);
//            }
//        }
//        return res;
//    }*/
//
//
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
//        String href = areaHtml.attr("href");
//        return href.substring(0,2);
//    }
//
//    /**
//     * 根据节点获取省名
//     *
//     * @param areaHtml 城市html节点
//     * @return 地区名
//     * @author YuChen
//     * @date 2019/12/24 14:26
//     */
//    @Override
//    public String getAreaName(Node areaHtml) {
//        return areaHtml.childNodes().get(0).outerHtml();
//    }
//
//    /**
//    * 获取该节点点击url
//    *
//    * @param areaHtml 节点
//    * @param url 当前页面url
//    * @return 该节点代表城市的url
//    * @author YuChen
//    * @date 2019/12/24 14:29
//    */
//    @Override
//    public String mixUrl(Node areaHtml, String url) {
//        String href = areaHtml.attr("href");
//        return GetAreaRunner.DOMAIN+href;
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
//        return "";
//    }
//
//
//}
