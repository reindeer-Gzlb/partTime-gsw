package com.ruoyi.system.h5;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 18:37
 */
public class GetGameTypeH5 {

    @JsonProperty("DataDictionary_ID")
    private Long DataDictionary_ID;
    private String Detail_Code;
    private Long Detail_ID;
    private String Detail_Name;
    private String Detail_Value;
    private String img;

    @JsonProperty("DataDictionary_ID")
    public Long getDataDictionary_ID() {
        return DataDictionary_ID;
    }

    public void setDataDictionary_ID(Long dataDictionary_ID) {
        DataDictionary_ID = dataDictionary_ID;
    }

    @JsonProperty("Detail_Code")
    public String getDetail_Code() {
        return Detail_Code;
    }

    public void setDetail_Code(String detail_Code) {
        Detail_Code = detail_Code;
    }

    @JsonProperty("Detail_ID")
    public Long getDetail_ID() {
        return Detail_ID;
    }

    public void setDetail_ID(Long detail_ID) {
        Detail_ID = detail_ID;
    }
    @JsonProperty("Detail_Name")
    public String getDetail_Name() {
        return Detail_Name;
    }

    public void setDetail_Name(String detail_Name) {
        Detail_Name = detail_Name;
    }
    @JsonProperty("Detail_Value")
    public String getDetail_Value() {
        return Detail_Value;
    }

    public void setDetail_Value(String detail_Value) {
        Detail_Value = detail_Value;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
