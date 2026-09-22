package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 接口记录对象 inter_rec
 *
 * @author ruoyi
 * @date 2022-06-23
 */
public class InterRec extends BaseEntity
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

    /** 账号ID */
    @Excel(name = "账号ID")
    private Long accountId;

    /** 合作伙伴CODE */
    @Excel(name = "合作伙伴CODE")
    private String saleCode;

    /** 0、请求成功 1、请求失败 */
    @Excel(name = "0、请求成功 1、请求失败")
    private Integer status;

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
    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public Long getAccountId()
    {
        return accountId;
    }
    public void setSaleCode(String saleCode)
    {
        this.saleCode = saleCode;
    }

    public String getSaleCode()
    {
        return saleCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("accountId", getAccountId())
            .append("saleCode", getSaleCode())
            .append("status", getStatus())
            .toString();
    }
}
