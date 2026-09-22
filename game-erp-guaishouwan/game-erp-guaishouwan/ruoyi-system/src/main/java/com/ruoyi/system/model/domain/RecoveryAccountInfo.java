package com.ruoyi.system.model.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 回收游戏账号对象 recovery_account_info
 * 
 * @author ruoyi
 * @date 2022-06-28
 */
@Data
public class RecoveryAccountInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private Long createId;

    /** 账号 */
    @Excel(name = "账号")
    private String accountId;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;

    /** 游戏大区 */
    @Excel(name = "游戏大区")
    private String regional;

    /** 段位 */
    @Excel(name = "段位")
    private String level;

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
    @Excel(name = "讲解")
    private String des;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 供货人 */
    @Excel(name = "供货人")
    private Long supplyId;

    /** 客服 */
    @Excel(name = "客服")
    private Long salesId;

    /** 销售状态 */
    @Excel(name = "销售状态")
    private Long saleState;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updateId;

    /** $column.columnComment */
    @Excel(name = "更新人")
    private String extractAccount;

    /** $column.columnComment */
    @Excel(name = "更新人")
    private String extractPwd;

    /** 成本价格 */
    @Excel(name = "成本价格")
    private BigDecimal costPrice;

    /** $column.columnComment */
    @Excel(name = "成本价格")
    private String sinNum;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 游戏名称 */
    @Excel(name = "游戏名称")
    private String gameName;

    /** $column.columnComment */
    @Excel(name = "游戏名称")
    private String extractTel;

    /** 买家ID */
    @Excel(name = "买家ID")
    private String buyId;

    /** 真实售价 */
    @Excel(name = "真实售价")
    private BigDecimal realPrice;

    /** 购买备注 */
    @Excel(name = "购买备注")
    private String remake;

    /** 审核人ID */
    @Excel(name = "审核人ID")
    private Long auditId;

    /** 其他备注 */
    @Excel(name = "其他备注")
    private String note;

    /** 销售时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "销售时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date saleTime;

    /** 供货人排序 */
    @Excel(name = "供货人排序")
    private Integer odr;

    /** 1、特价 0、非特价 */
    @Excel(name = "1、特价 0、非特价")
    private Integer accType;

    /** 特价价格 */
    @Excel(name = "特价价格")
    private Long specialPrice;

    /** 1、自营号 2、代售号 */
    @Excel(name = "1、自营号 2、代售号")
    private Integer accountType;

    /** 回收状态:0:待回收，1、已回收，2、未回收 */
    @Excel(name = "回收状态:0:待回收，1、已回收，2、未回收")
    private Integer recoveryState;

    /** 回收客服 */
    @Excel(name = "回收客服")
    private Long recoveryId;

    /** 回收备注 */
    @Excel(name = "回收备注")
    private String recoveryNote;

    /** 审核状态:0:待待审核，1、通过，2、未通过 */
    @Excel(name = "审核状态:0:待待审核，1、通过，2、未通过")
    private Integer auditState;

    /** 是否审核包赔,0:是，1:否 */
    @Excel(name = "是否审核包赔,0:是，1:否")
    private Integer auditFlag;

    /** 审核备注 */
    @Excel(name = "审核备注")
    private String auditNote;

    /** 审核包赔状态:0:待审核，1、审核通过，2、未通过 */
    @Excel(name = "审核包赔状态:0:待审核，1、审核通过，2、未通过")
    private Integer verifyState;

    /** 审核包赔客服 */
    @Excel(name = "审核包赔客服")
    private Long verifyId;

    /** 回收客服名称 */
    @Excel(name = "回收客服名称")
    private String recoveryName;

    /** 审核包赔客服名称 */
    @Excel(name = "审核包赔客服名称")
    private String verifyName;

    /** 标题图片 */
    @Excel(name = "标题图片")
    private String titleImg;

    /** 营地id */
    @Excel(name = "营地id")
    private String campId;


    /**
     * 上传图片url
     */
    private List<Map<String,Object>> urlArr;

    /**
     * 进入包赔审核后存储图片
     */
    private List<InfoImg> infoImgs = new ArrayList<>();

    /**
     * 当前登录人
     */
    private Long loginId;

    private String sort;
    private String sortColumn;

    private List<String> regionalAddr;
    private List<String> levelAddr;
    private List<Long> supplyIdAddr;
    private List<Long> salesIdAddr;
    private String saleStateName;

    /**
     * 角色操作员
     */
    private String operType;

    private BigDecimal priceStart;
    private BigDecimal priceEnd;

    private Integer heroStart;
    private Integer heroEnd;

    private Integer skinStart;
    private Integer skinEnd;

    private Integer suitStart;
    private Integer suitEnd;

    private Integer gunsStart;
    private Integer gunsEnd;

    /**
     * 销售开始和结束时间
     */
    private Date saleTimeStart;
    private Date saleTimeEnd;
    /**
     * g更新开始和结束时间
     */
    private Date updateTimeStart;
    private Date updateTimeEnd;

    /**
     * 导入开始和结束时间
     */
    private Date createTimeStart;
    private Date createTimeEnd;

    private Integer timeout;
    private String addDayTime;
    private String secret;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    private Date recoveryTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    private Date auditTime;

    private String desQur;

    private List<String> desArr;

    private Date recoveryTimeStart;
    private Date recoveryTimeEnd;
}
