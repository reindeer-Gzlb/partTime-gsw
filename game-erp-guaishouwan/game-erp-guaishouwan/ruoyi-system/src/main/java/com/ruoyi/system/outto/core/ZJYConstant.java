package com.ruoyi.system.outto.core;

import java.util.HashMap;
import java.util.Map;

public enum ZJYConstant {

    IMPORTCODE("importcode", "zdH3jwbeTTCgzBvIBL0"),
    IMPORTID("importid", "15"),
//    KEY("key", "weacb2c7cfcxd5c31a"),
    DOWNTABLE("下架", "0"),
    UPTABLE("上架", "1"),
    DEL("删除", "2");
    //    Large_region1("安卓QQ账号", 19),
//    Large_region1("安卓QQ账号", 19),
//    Large_region2("安卓微信账号", 20),
//    Large_region3("苹果QQ账号", 21),
//    Large_region4("苹果微信账号", 22);
    private String name;
    private String value;


    private ZJYConstant(String name, String value) {
        this.name = name;

        this.value = value;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {

        return name;

    }

    public String getValue() {

        return value;

    }
}
