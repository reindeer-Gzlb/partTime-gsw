package com.ruoyi.system.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 游戏账号审核包赔个人详情对象 account_personal_details
 * 
 * @author ruoyi
 * @date 2022-05-08
 */
@Data
public class AccountPersonalDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 游戏账号 */
    @Excel(name = "游戏账号账号表id")
    private Long accountId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别,0:男,1女 */
    @Excel(name = "性别,0:男,1女")
    private Long gender;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idcard;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 家庭地址 */
    @Excel(name = "家庭地址")
    private String address;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 父亲手机号 */
    @Excel(name = "父亲手机号")
    private String fatherPhone;

    /** 母亲手机号 */
    @Excel(name = "母亲手机号")
    private String motherPhone;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateUser;

    /** 备注 */
    @Excel(name = "备注")
    private String reamrk;

    /**
     * 进入包赔审核后存储图片
     */
    private List<InfoImg> infoImgs;

    /**
     * 上传图片list
     */
    private List<Map<String,Object>> urlArr;

}
