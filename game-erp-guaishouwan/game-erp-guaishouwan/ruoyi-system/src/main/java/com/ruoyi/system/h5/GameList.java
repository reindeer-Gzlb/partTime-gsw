package com.ruoyi.system.h5;

import lombok.Data;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 20:54
 */
@Data
public class GameList {

    private Integer rownum;

    /**
     * 账号id
     */
    private Long zhid;
    /**
     * 销售价格
     */
    private BigDecimal csjg;
    private BigDecimal oldCsjg;

    private String createDate;
    private String createName;

    /**
     * 游戏名字
     */
    private String yxmz;
    private String yxlx;
    /**
     * 营地信息
     */
    private String ydsj;
    /**
     * 游戏大区
     */
    private String yxdq;
    /**
     * 段位
     */
    private String dw;
    /**
     * 英雄数量
     */
    private Integer yx;
    /**
     * 英雄皮肤
     */
    private Integer pf;
    /**
     * 简介
     */
    private String jj;
    /**
     * 套装
     */
    private Integer suit;
    /**
     * 枪皮
     */
    private Integer gunskin;
    /**
     * 账号是否特价
     */
    private String accType;
    /**
     * 特价价格
     */
    private BigDecimal specialPrice;

    /**
     * 标题图片
     */
    private String titleImg;

    private List<String> lables;

}
