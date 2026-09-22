package com.ruoyi.system.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @date 2023-05-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "account_monitoring_query")
public class AccountMonitoringQuery {
    @TableId(type = IdType.AUTO)
    @JSONField(deserialize = false)
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
     * -1000：鉴权失败 -1：参数错误 -404：数据未找到 -500：数据操作失败
     */
    private Integer code;
    /**
     * 返回文字描述
     */
    private String msg;
    /**
     * 时间
     */
    private String time;

    /**
     * 查询那边返回的id
     */
    @JSONField(name = "id")
    private Integer queryId;
    /**
     * QQ
     */
    @JSONField(name = "qq")
    private String qq;
    /**
     * 手机号
     */
    @JSONField(name = "telephone")
    private String telephone;
    /**
     * 账号状态:0=正常,1=被找回,2=二变无,3=今日上限,4=账号错误
     */
    @JSONField(name = "status")
    private String status;
    /**
     * 任务状态:0=待执行,1=执行中,2=已完成,3=异常
     */
    @JSONField(name = "task_status")
    private String taskStatus;
    /**
     * 任务完成时间
     */
    @JSONField(name = "task_complete_time")
    private String taskCompleteTime;
    /**
     * 下次执行时间
     */
    @JSONField(name = "task_next_time")
    private String taskNextTime;
    /**
     * 是否启用:0=停用,1=启用
     */
    @JSONField(name = "enabled")
    private String enabled;
    /**
     * 脱敏手机号
     */
    @JSONField(name = "bind_tel")
    private String bindTel;
    /**
     * status_text
     */
    @JSONField(name = "status_text")
    private String statusText;
    /**
     * 任务主表id
     */
    private Long accountMonitoringId;
    
}
