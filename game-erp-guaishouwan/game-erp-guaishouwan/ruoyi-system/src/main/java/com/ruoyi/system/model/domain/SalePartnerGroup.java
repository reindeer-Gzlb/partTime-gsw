package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 接口配置对象 sale_partner_group
 * 
 * @author ruoyi
 * @date 2023-05-06
 */
public class SalePartnerGroup extends BaseEntity
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

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String name;

    /** 分组ids */
    @Excel(name = "分组ids")
    private String configIds;

    /** 分组多配置 */
    @Excel(name = "分组多配置")
    private String configNames;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setConfigIds(String configIds) 
    {
        this.configIds = configIds;
    }

    public String getConfigIds() 
    {
        return configIds;
    }
    public void setConfigNames(String configNames) 
    {
        this.configNames = configNames;
    }

    public String getConfigNames() 
    {
        return configNames;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("name", getName())
            .append("configIds", getConfigIds())
            .append("configNames", getConfigNames())
            .toString();
    }
}
