//package spider.handler.gc;
//
//import cn.hutool.core.util.StrUtil;
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
// * 处理区县级
// *
// * @author YuChen
// * @date 2019/12/24 9:50
// **/
//@Slf4j
//public class DistrictHandler extends AbstractDefaultAreaHandler {
//
//    private static class Singlon {
//        private static final DistrictHandler singlon = new DistrictHandler();
//    }
//
//    public static DistrictHandler getInstance() {
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
///*        OkHttpClient client = new OkHttpClient().newBuilder().connectionPool(new ConnectionPool(8,10,TimeUnit.MINUTES)).build();
//        Request request = new Request.Builder().url(url).build();
//        try{
//            Response response= client.newCall(request).execute();
//            String html = new String(response.body().bytes(),"GBK");
//            return super.analysisHtml(url,parentCode,html,"countytr");
//        }catch (Exception e){
//            throw new RuntimeException();
//        }*/
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
//        return super.analysisHtml(url, parentCode, html, tryTimes,"countytr","towntr");
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
//        String[] split = url.split("/");
//        int lastfileLengh = split[split.length-1].length();
//        url = url.substring(0,url.length()-lastfileLengh);
//        return url +href;
//    }
//
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
//        return TownHandler.getInstance();
//    }
//}
