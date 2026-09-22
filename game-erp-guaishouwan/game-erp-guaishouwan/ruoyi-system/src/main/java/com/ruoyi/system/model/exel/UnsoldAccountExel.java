package com.ruoyi.system.model.exel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 游戏账号对象 account_info
 *
 * @author ruoyi
 * @date 2022-05-04
 */
@Data
public class UnsoldAccountExel
{


    /** 账号 */
    @Excel(name = "编号")
    private Long id;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;

    /** 游戏大区 */
    @Excel(name = "区服")
    private String regional;

    /** 游戏名称 */
    @Excel(name = "自编号")
    private String gameName;

    /** 段位 */
    @Excel(name = "实名认证")
    private String level;

    @Excel(name = "营地ID")
    private String campId;

    /** 英雄数量 */
    @Excel(name = "英雄数量")
    private Integer heroes;

    /** 皮肤数量 */
    @Excel(name = "皮肤数量")
    private Integer skins;

    /** 套装 */
    @Excel(name = "套装")
    private Integer suit;

    /** 枪皮 */
    @Excel(name = "枪皮")
    private Integer guns;

    /** 讲解 */
    @Excel(name = "简介")
    private String des;

    /** 价格 */
    @Excel(name = "出售价格")
    private BigDecimal price;

//    /** 供货人 */
//    @Excel(name = "供货客服")
//    private String supplyName;
//
    @Excel(name = "导入时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
//
//    @Excel(name = "状态")
//    private String saleStateName;
//
//    @Excel(name = "更新人")
//    private String updateName;
//
//    @Excel(name = "更新时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
//    private Date updateTime ;

}
