package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 price_rec
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
public class PriceRec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private Long createId;

    /** $column.columnComment */
    private Long updateId;

    /** 价格 */
    @Excel(name = "价格")
    private String price;

    /** 编码 */
    @Excel(name = "编码")
    private String saleCode;

    /** 销售名称 */
    @Excel(name = "销售名称")
    private String saleName;

    /** 配置id */
    @Excel(name = "配置id")
    private Long config;
    private Long infoid;

    public Long getInfoid() {
        return infoid;
    }

    public void setInfoid(Long infoid) {
        this.infoid = infoid;
    }

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
    public void setPrice(String price) 
    {
        this.price = price;
    }

    public String getPrice() 
    {
        return price;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("price", getPrice())
            .append("saleCode", getSaleCode())
            .append("saleName", getSaleName())
            .append("config", getConfig())
            .toString();
    }
}
