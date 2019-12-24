package entity;

/**
 * 省市区实体类
 *  
 * @author YuChen
 * @date 2019/12/23 17:16
 **/
 
public class City {
    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String typeCode;
    // 点击该地区时的url
    private String url;

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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof City){
            return ((City) obj).getCode().equals(this.getCode());
        }
        return super.equals(obj);
    }


    @Override
    public int hashCode(){
        return Integer.valueOf(code);
    }
}
