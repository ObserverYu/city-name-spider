package spider;

import entity.City;
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
    }

/*    public List<City> start(){
        List<City> res = new ArrayList<City>();
        // 拿到所有省名映射省raceCode  省raceCode可获取省信息页面url
        Map<String,String> allProvinceNameMapCode = getAllProvinceRaceCode(mainUrl);
        // 先将省组装起来
        List<City> provinces = getAllProvince(allProvinceNameMapCode);
        res.addAll(provinces);
        allProvinceNameMapCode.forEach((k,v) -> {
            // 遍历 根据省信息获取其下的市信息
            addCityAndAreaAndOthers(k,v,res);
        });
    }

    *//**
    * 根据省信息获取该省下的所有信息
    *
    * @param raceCode 没有补0的省code
    * @return 
    * @author YuChen
    * @date 2019/12/23 17:29
    *//*
    private void addCityAndAreaAndOthers(String provinceName, String raceCode, List<City> res) {

    }

    *//**
    * 根据省名映射省raceCode信息获取省实体
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/23 17:31
    *//*
    private List<City> getAllProvince(Map<String, String> allProvinceUrl) {

    }

    *//**
    * 爬取省信息,组装成省名对raceCode的映射 (raceCode:没有补0的地区code)
    *
    * @param
    * @return
    * @author YuChen
    * @date 2019/12/23 17:32
    *//*
    private Map<String,String> getAllProvinceRaceCode() {

    }*/
}
