package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 账号校验对象 check_account
 *
 * @author ruoyi
 * @date 2022-10-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /**
     * 账号
     */
    @Excel(name = "账号")
    private String account;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 工作类型  1、白班 2、夜班
     */
    private String worktype;

    private List<String> workTypes;

    /**
     * 环信
     */
    private String hxname;

    /**
     * 绑定userid
     */
    private String customer;
    /**
     * 绑定customerName
     */
    private String customerName;
    /**
     * 0 正常  1 失效
     */
    @Excel(name = "0 正常  1 失效")
    private String state;
    private String qqtype;

    /**
     * QQ号
     */
    private String qq;
    /**
     * 微信
     */
    private String weixin;
    /**
     * 二维码地址
     */
    private String qrCode;
    /**
     * 上班时间
     */
    private String workTime;

    /**
     * 收款账户
     */
    private String collection;

    /**
     * 上班状态：1上班 2下班
     */
    private Integer workState;

    /**
     * 客服类型1:B端客服2:C端客服3:代售客服4:包赔客服5:回收客服
     */
    private Integer type;

    /**
     * 属于哪些游戏的
     */
    private String gameIds;

    /**
     * 属于哪些游戏的
     */
    private String gameNames;

    private String[] gameIdsArr;

    private String yzString;


    /**
     * 1 主站   2 商户端
     */
    private Integer typeResource = 1;

    private Long businessId ;
    private int sort ;
}
