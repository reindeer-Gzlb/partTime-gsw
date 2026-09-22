package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 游戏属性对象 account_attr
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
public class AccountAttr extends BaseEntity
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

    /** 属性1 */
    @Excel(name = "属性1")
    private String att1;

    /** 属性2 */
    @Excel(name = "属性2")
    private String att2;

    /** 游戏 */
    @Excel(name = "游戏")
    private String type;

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
    public void setAtt1(String att1) 
    {
        this.att1 = att1;
    }

    public String getAtt1() 
    {
        return att1;
    }
    public void setAtt2(String att2) 
    {
        this.att2 = att2;
    }

    public String getAtt2() 
    {
        return att2;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("att1", getAtt1())
            .append("att2", getAtt2())
            .append("type", getType())
            .toString();
    }
}
