package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 art_rec
 * 
 * @author ruoyi
 * @date 2023-02-17
 */
public class ArtRec extends BaseEntity
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

    /** 用户名 */
    @Excel(name = "用户名")
    private Long userId;

    /** del 、 add 、 edit */
    @Excel(name = "del 、 add 、 edit")
    private String type;

    /** 终端编号 */
    @Excel(name = "终端编号")
    private Long accountId;

    /** 配置ID */
    @Excel(name = "配置ID")
    private Long config;
    private String outid;

    /** 配置类 */
    @Excel(name = "配置类")
    private String saleCode;

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
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setConfig(Long config) 
    {
        this.config = config;
    }

    public Long getConfig() 
    {
        return config;
    }
    public void setSaleCode(String saleCode) 
    {
        this.saleCode = saleCode;
    }

    public String getSaleCode() 
    {
        return saleCode;
    }

    public String getOutid() {
        return outid;
    }

    public void setOutid(String outid) {
        this.outid = outid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("userId", getUserId())
            .append("type", getType())
            .append("accountId", getAccountId())
            .append("config", getConfig())
            .append("saleCode", getSaleCode())
            .toString();
    }
}
