package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zyy
 * @date 2023-05-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "account_monitoring")
public class AccountMonitoring {
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
     * 监测的QQ账号
     */
    @NotNull(message = "参数accountNumber不能为空")
    private String accountNumber;
    /**
     * 账号绑定的手机
     */
    @NotNull(message = "参数accountPhone不能为空")
    private String accountPhone;
    /**
     * 检查间隔(执行间隔，搭配间隔类型，检查间隔1，间隔类型1，代表1分钟执行一次监测)
     */
    @NotNull(message = "参数checkInterval不能为空")
    private String checkInterval;
    /**
     * 间隔类型:0=分钟,1=小时,2=天
     */
    @NotNull(message = "参数intervalType不能为空")
    private String intervalType;
    /**
     * 1代表查找回任务、2代表查转手任务、3代表一次性查找回任务
     */
    @NotNull(message = "参数taskType不能为空")
    private String taskType;
    /**
     * 0代表不开启自动查转手转换，1代表开启自动查转手转换
     */
    @NotNull(message = "参数autoSwitchType不能为空")
    private String autoSwitchType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 成功后返回的
     */
    @TableField(value = "checkId")
    private String checkId;
    /**
     * 是否停用(1 正常 0 停用)
     */
    private String enabled;
    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 账号状态
     */
    private String status;
    /**
     * 任务状态:0=待执行,1=执行中,2=已完成,3=异常
     */
    private String taskStatus;
    /**
     * 脱敏手机号
     */
    private String bindTel;

    /**
     * 删除状态 1未删除 2已删除
     */
    private Integer del;

}
