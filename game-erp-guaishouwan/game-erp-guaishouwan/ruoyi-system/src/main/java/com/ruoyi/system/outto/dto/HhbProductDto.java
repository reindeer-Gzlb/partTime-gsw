package com.ruoyi.system.outto.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HhbProductDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private String pic;
    private Integer game;
    private String role;
    private String area;
    private String server;
    private Integer bface;
    private Integer canreal;//健康系统实名情况不可实名：0可二次实名： 1包到二次实名： 2王者/和平/LOL/火影/枪战王者/CF/DNF/QQ飞车端游手游/逆战/英雄联盟手游必传
    private Integer param1;
    private Integer param2;
    private Integer param3;
    private Integer param4;
    private Integer param5;
    private String param;
    //扩展参数：
    //王者：
    //param1：英雄数量
    //param2：皮肤数量
    //param3：5级铭文数量
    //param4：贵族等级
    //param5：贵族积分(万)
    //
    //和平：
    //param1：套装数量
    //param2：枪皮数量
    //param3：载具数量
    //
    //LOL：
    //param1：英雄数量
    //param2：皮肤数量
}
