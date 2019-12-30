package org.chen.constant;

/**
 * 爬虫相关常量
 *  
 * @author YuChen
 * @date 2019/12/30 9:52
 **/
 
public class SpiderConstant {
	/**
	 *   入口url
	 */
	public static final String ENTER_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html";

	/**
	 *   主路径
	 */
	public static final String DOMAIN = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";

	/**
	 *   反爬虫  设置的heads
	 */
	public static final String COOKIE = "_trs_uv=k4m16pij_6_115g; AD_RS_COOKIE=20080917; wzws_cid=bcdb5e634494a25a7c2e6663cfffd89cc7a2bd70dc01b3451a99ef51b80a2144495c360e8661c03fdecaa64298e6b176b5d2046626166f1c914c2ec71cddcbc8";

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";

	public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";

	/**
	 *   省页面表格class名
	 */
	public static final String CLASS_MARK_PROVINCE = "provincetr";
	/**
	 *   市页面表格class名
	 */
	public static final String CLASS_MARK_CITY = "citytr";
	/**
	 *   区县页面表格class名
	 */
	public static final String CLASS_MARK_COUNTY = "countytr";
	/**
	 *   镇街道页面表格class名
	 */
	public static final String CLASS_MARK_TOWN = "towntr";
	/**
	 *   村页面表格class名
	 */
	public static final String CLASS_MARK_VILLAGE= "villagetr";
}
