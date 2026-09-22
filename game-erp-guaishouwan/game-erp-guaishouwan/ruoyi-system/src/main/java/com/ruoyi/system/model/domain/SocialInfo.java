package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "social_info")
public class SocialInfo {

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
     * 类型1抖音 2快手
     */
    private Integer type;
    /**
     * 名称
     */
    private String name;

    /**
     * 账号
     */
    private String account;
    /**
     * 粉丝
     */
    private String fans;
    /**
     * 是否启用 1启用 2停用
     */
    private Integer isEnable;

    /**
     * 头像图片
     */
    private String avatarImg;

    /**
     * pc端跳转地址
     */
    private String pcJump;

    /**
     * 移动端跳转地址
     */
    private String mobileJump;

}
