package spider;

import entity.City;
import spider.handler.DistrictHandler;
import spider.handler.ProvinceHandler;

import java.util.Set;

/**
 * 从国家统计局官网获取最新的省市区信息
 *  
 * @author YuChen
 * @date 2019/12/23 17:13
 **/
 
public class GetAreaMain {

    public static final String mainUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html";

    public static final String domain = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";

    /**
    * 获取主url的方法
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/23 17:42
    */
    private static String getmainUrl(){
        return mainUrl;
    }

    public static void main(String[] args) {
/*        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        ProvinceHandler provinceHandler = ProvinceHandler.getInstance();
        Set<City> allArea = new HashSet<>();
        provinceHandler.handle(getmainUrl(),"0",allArea,threadPoolExecutor);*/

        Set<City> entity = ProvinceHandler.getInstance().getEntity(getmainUrl(), "0");
        System.out.println(entity);

        Set<City> entity2 = DistrictHandler.getInstance().getEntity("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42/4208.html", "42");
        System.out.println(entity);
    }
}
