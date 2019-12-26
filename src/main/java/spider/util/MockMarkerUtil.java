package spider.util;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 模拟上海市打点
 *  
 * @author YuChen
 * @date 2019/12/25 14:31
 **/
 
public class MockMarkerUtil {

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(getMockDataEntity(121.65,121.20,31.35,31.00,100)));
    }

    public static List<MockMakerData> getMockDataEntity(Double maxLng,Double minLng,Double maxLat, Double minLat,Integer num){
        List<MockMakerData> res = new ArrayList<>();
        List<double[]> mockPosition = getMockPosition(maxLng, minLng, maxLat, minLat, num);
        int mockNameAndId = 0;
        for(double[] data : mockPosition){
            MockMakerData mockMakerData = new MockMakerData(data[0], data[1], "测试名字" + mockNameAndId, mockNameAndId);
            res.add(mockMakerData);
        }
        return res;
    }

    public static List<double[]> getMockPosition(Double maxLng, Double minLng, Double maxLat, Double minLat, Integer num){
        List<double[]> res = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            BigDecimal lng = RandomUtil.randomBigDecimal(new BigDecimal(String.valueOf(minLng)), new BigDecimal(String.valueOf(maxLng)));
            BigDecimal lat = RandomUtil.randomBigDecimal(new BigDecimal(String.valueOf(minLat)), new BigDecimal(String.valueOf(maxLat)));
            res.add(new double[]{lng.doubleValue(),lat.doubleValue()});
        }
        return res;
    }

    public static class  MockMakerData{

        public MockMakerData(){}

        public MockMakerData(Double lng, Double lat, String name, Integer id) {
            this.lng = lng;
            this.lat = lat;
            this.name = name;
            this.id = id;
        }

        private Double lng;

        private Double lat;

        private String name;

        private Integer id;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }


}
