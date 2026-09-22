package com.ruoyi.system.steam.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * steam任务执行对象 steam_collect_task
 *
 * @author ruoyi
 * @date 2025-03-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SteamCollectTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * steam帐号
     */
    @Excel(name = "steam帐号")
    private String steamAccount;

    /**
     * steam邮箱链接
     */
    @Excel(name = "steam邮箱链接")
    private String steamEmailReq;

    /**
     * steam邮箱
     */
    @Excel(name = "steam邮箱")
    private String steamEmail;

    /**
     * steam手机号
     */
    @Excel(name = "steam手机号")
    private String steamPhone;

    /**
     * 任务状态 1 正常 2 异常 3 回收
     */
    @Excel(name = "任务状态",readConverterExp = "1=正常,2=异常,3=回收,4=失败")
    private Integer status;

    /**
     * 邮箱状态 1 正常 2 邮箱为空 3 邮箱变化
     */
    @Excel(name = "邮箱状态",readConverterExp = "1=正常,2=邮箱为空,3=邮箱变化,4回收")
    private Integer emailStatus;

    /**
     * 手机状态 1 正常 2 手机为空 3 手机变化
     */
    @Excel(name = "手机状态",readConverterExp = "1=正常,2=手机为空,3=手机变化,4回收")
    private Integer phoneStatus;

    /**
     * 最后执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后执行时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /**
     * 起止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreateTime;

}
