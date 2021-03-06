package org.chen.entity;

/**
 * 省市区实体类
 *  
 * @author YuChen
 * @date 2019/12/23 17:16
 **/
 
public class City {
    /**
     * 主键
     */
    private Long id;
    /**
     * 地区code
     */
    private String code;
    /**
     * 父地区code
     */
    private String parentCode;
    /**
     * 地区名
     */
    private String name;
    /**
     * 城乡分类代码
     */
    private String typeCode;
    /**
     * 点击该地区时的url
     */
    private String url;
    /**
     * 地区等级
     */
    private Integer level;

    public City(){}

    public City(String code, String parentCode, String name, String typeCode, String url) {
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.typeCode = typeCode;
        this.url = url;
    }

    public City(String code, String parentCode, String name, String typeCode, String url, Integer level) {
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.typeCode = typeCode;
        this.url = url;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof City){
            return ((City) obj).getCode().equals(this.getCode());
        }
        return super.equals(obj);
    }


    @Override
    public int hashCode(){

        return Long.valueOf(code).intValue();
    }
}
