package spider;

import entity.City;
import spider.handler.*;

import java.util.Set;

/**
 * 从国家统计局官网获取最新的省市区信息
 *  
 * @author YuChen
 * @date 2019/12/23 17:13
 **/
 
public class GetAreaMain {

    // 入口url
    public static final String ENTER_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html";

    // 主路径
    public static final String DOMAIN = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";

    /**
    * 获取主url的方法
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/23 17:42
    */
    private static String getmainUrl(){
        return ENTER_URL;
    }

    public static void main(String[] args) {
/*        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        ProvinceHandler provinceHandler = ProvinceHandler.getInstance();
        Set<City> allArea = new HashSet<>();
        provinceHandler.handle(getmainUrl(),"0",allArea,threadPoolExecutor);*/

/*        Set<City> province = ProvinceHandler.getInstance().getEntity(getmainUrl(), "0");
        System.out.println(province);

        Set<City> citiy = CityHandler.getInstance().getEntity("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42.html", "42");
        System.out.println(citiy);

        Set<City> district = DistrictHandler.getInstance().getEntity("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42/4201.html", "4201");
        System.out.println(district);

        Set<City> town = TownHandler.getInstance().getEntity("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42/08/420882.html", "420882");
        System.out.println(town);*/

        Set<City> village = VillageHandler.getInstance().getEntity("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/42/08/82/420882104.html", "420882104");
        System.out.println(village);
    }
}
