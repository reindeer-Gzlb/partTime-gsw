package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 time_up
 * 
 * @author ruoyi
 * @date 2023-04-01
 */
public class TimeUp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 编码 */
    @Excel(name = "编码")
    private String saleCode;

    /** 销售名称 */
    @Excel(name = "销售名称")
    private String saleName;

    /** 配置id */
    @Excel(name = "配置id")
    private Long config;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }
    public void setUpdateId(Long updateId) 
    {
        this.updateId = updateId;
    }

    public Long getUpdateId() 
    {
        return updateId;
    }
    public void setSaleCode(String saleCode) 
    {
        this.saleCode = saleCode;
    }

    public String getSaleCode() 
    {
        return saleCode;
    }
    public void setSaleName(String saleName) 
    {
        this.saleName = saleName;
    }

    public String getSaleName() 
    {
        return saleName;
    }
    public void setConfig(Long config) 
    {
        this.config = config;
    }

    public Long getConfig() 
    {
        return config;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("saleCode", getSaleCode())
            .append("saleName", getSaleName())
            .append("config", getConfig())
            .append("userId", getUserId())
            .toString();
    }
}
