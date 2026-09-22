package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 错误信息对象 error_info
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
public class ErrorInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 账号id */
    @Excel(name = "账号id")
    private Long errId;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errMsg;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setErrId(Long errId) 
    {
        this.errId = errId;
    }

    public Long getErrId() 
    {
        return errId;
    }
    public void setErrMsg(String errMsg) 
    {
        this.errMsg = errMsg;
    }

    public String getErrMsg() 
    {
        return errMsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("errId", getErrId())
            .append("errMsg", getErrMsg())
            .toString();
    }
}
