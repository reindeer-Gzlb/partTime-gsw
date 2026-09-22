package com.ruoyi.system.guarantee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 包赔资料信息表(guarantee_info)表实体类
 *
 * @author zyy
 * @date 2023-12-05 18:41:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("guarantee_info")
public class GuaranteeInfo {

    @TableId(type = IdType.AUTO)
    @Excel(name = "${column.comment}")
    private Long id;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private Long createId;

    /**
     * 更新人id
     */
    @Excel(name = "更新人id")
    private Long updateId;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 回收订单id
     */
    @Excel(name = "回收订单id")
    private Long backOrderId;

    /**
     * 回收记录id
     */
    @Excel(name = "回收记录id")
    private Long backlistId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phoneNumber;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String adress;

    /**
     * 信息补充
     */
    @Excel(name = "信息补充")
    private String remark;

    /**
     * 身份证脸面照片地址
     */
    @Excel(name = "身份证脸面照片地址")
    private String faceIdcardImg;

    /**
     * 身份证徽面照片地址
     */
    @Excel(name = "身份证徽面照片地址")
    private String emblemIdcardImg;

    /**
     * 其他图片
     */
    @Excel(name = "其他图片")
    private String img;

    /**
     * ip地址
     */
    @Excel(name = "ip地址")
    private String ip;
    /**
     * ip解析的城市地址
     */
    @Excel(name = "ip解析的城市地址")
    private String ipAddr;

    /**
     * 包赔商id
     */
    @Excel(name = "包赔商id")
    private Long guaranteeId;

    /**
     * 包赔商姓名
     */
    @Excel(name = "包赔商姓名")
    private String guaranteeName;
}
