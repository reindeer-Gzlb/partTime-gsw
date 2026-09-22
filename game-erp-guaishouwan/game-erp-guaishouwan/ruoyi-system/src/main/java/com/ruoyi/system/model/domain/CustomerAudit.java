package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zyy
 * @date 2023-05-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "customer_audit")
public class CustomerAudit {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 紧急联系人手机号
     */
    private String urgentPhoneNumber;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 地址
     */
    private String adress;
    /**
     * 营业执照图片地址
     */
    @TableField(value = "business_license_img")
    private String businessLicenseImg;
    /**
     * 手持身份证照片地址
     */
    @TableField(value = "hand_idCard_img")
    private String handIdCardImg;
    /**
     * 身份证正面照片地址
     */
    @TableField(value = "front_idCard_img")
    private String frontIdCardImg;
    /**
     * 身份证反面照片地址
     */
    @TableField(value = "reverse_idCard_img")
    private String reverseIdCardImg;
    /**
     * 说明备注
     */
    private String remark;
    /**
     * 审核人Id
     */
    private Long auditId;
    /**
     * 审核人名称
     */
    private String auditName;
    /**
     * 审核状态  0未审核 1审核通过 2审核拒绝
     */
    private Integer auditState;
    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 供应商折扣
     */
    @TableField(exist = false)
    private Integer discountt;

    /**
     * 利润比例
     */
    @TableField(exist = false)
    private Integer rise;

}
