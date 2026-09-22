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
 * @date 2023-05-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "index_resources")
public class IndexResources {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 修改人id
     */
    private Long updateId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 系统类型 1:saas版 2:独立版
     */
    private Integer systemType;
    /**
     * 资源类型1:视频
     */
    private Integer type;
    /**
     * 资源地址
     */
    private String url;
    /**
     * 资源名字
     */
    private String name;
    /**
     * 资源封面地址
     */
    private String imgUrl;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否展示1:展示 2:不展示
     */
    private Integer isShow;
    /**
     * 备注
     */
    private String remark;
    /**
     * pc端跳转地址
     */
    private String pcJump;
    /**
     * 移动端跳转地址
     */
    private String mobileJump;


    /**
     * 1 主站   2 商户端
     */
    private Integer typeResource = 1;

    @TableField(exist = false)
    private Long businessId ;
}
