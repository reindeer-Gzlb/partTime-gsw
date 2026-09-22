package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zyy
 * @date 2023-05-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "app_version")
public class AppVersion {
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
     * app类型（1安卓 2苹果）
     */
    private Integer appType;

    /**
     * app安装包下载地址
     */
    private String appDownloadUrl;

    /**
     * app内部版本号
     */
    private Integer appVersionNum;

    /**
     * app版本名称
     */
    private String appVersionName;

    /**
     * app更新内容
     */
    private String appUpdateRemark;

    /**
     *
     */
    private String isUpdate;

}