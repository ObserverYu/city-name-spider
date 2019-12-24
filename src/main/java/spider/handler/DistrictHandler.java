package spider.handler;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import entity.City;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * 处理区县级
 *
 * @author YuChen
 * @date 2019/12/24 9:50
 **/
@Slf4j
public class DistrictHandler extends AbstractDefaultAreaHandler {

    private static class Singlon {
        private static final DistrictHandler singlon = new DistrictHandler();
    }

    public static DistrictHandler getInstance() {
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
/*        OkHttpClient client = new OkHttpClient().newBuilder().connectionPool(new ConnectionPool(8,10,TimeUnit.MINUTES)).build();
        Request request = new Request.Builder().url(url).build();
        try{
            Response response= client.newCall(request).execute();
            String html = new String(response.body().bytes(),"GBK");
            return super.analysisHtml(url,parentCode,html,"countytr");
        }catch (Exception e){
            throw new RuntimeException();
        }*/
        HttpRequest get = HttpUtil.createGet(url);
        HttpResponse execute = get.execute();
        String html;
        try {
            html = new String(execute.bodyBytes(), "GBK");
        } catch (Exception e) {
            log.error("转码失败",e);
            throw new RuntimeException("转码失败");
        }
        return super.analysisHtml(url, parentCode, html, "countytr");
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
        return TownHandler.getInstance();
    }
}
