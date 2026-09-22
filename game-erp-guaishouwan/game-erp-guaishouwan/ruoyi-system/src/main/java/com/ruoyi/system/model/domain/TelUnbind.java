package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检测任务池对象 tel_unbind
 * 
 * @author ruoyi
 * @date 2023-04-19
 */
public class TelUnbind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 账号id */
    @Excel(name = "账号id")
    private Long accountId;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 识别结果 */
    @Excel(name = "识别结果")
    private String currentTel;

    /** 绑定手机号 */
    @Excel(name = "绑定手机号")
    private String checkTel;

    /** 是否已确认 */
    @Excel(name = "是否已确认 1、问题账号  2、正常账号")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setCurrentTel(String currentTel) 
    {
        this.currentTel = currentTel;
    }

    public String getCurrentTel() 
    {
        return currentTel;
    }
    public void setCheckTel(String checkTel) 
    {
        this.checkTel = checkTel;
    }

    public String getCheckTel() 
    {
        return checkTel;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("accountId", getAccountId())
            .append("account", getAccount())
            .append("currentTel", getCurrentTel())
            .append("checkTel", getCheckTel())
            .append("status", getStatus())
            .toString();
    }
}
