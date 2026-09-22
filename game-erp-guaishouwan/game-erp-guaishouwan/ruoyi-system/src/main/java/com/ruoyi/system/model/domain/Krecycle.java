package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 krecycle
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
public class Krecycle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 期望价格 */
    @Excel(name = "期望价格")
    private String expprice;

    /** 报价 */
    @Excel(name = "报价")
    private String bprice;
    private String account;

    /** 成交价 */
    @Excel(name = "成交价")
    private String tprice;

    /** 手机号 */
    @Excel(name = "手机号")
    private String tel;

    /** 供货商 */
    @Excel(name = "供货商")
    private Long suplair;

    /** 客户端id */
    @Excel(name = "客户端id")
    private Long clientId;

    /** 回收id */
    @Excel(name = "回收id")
    private Long backId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;
    private String suplairName;
    private String clientName;

    public String getSuplairName() {
        return suplairName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setSuplairName(String suplairName) {
        this.suplairName = suplairName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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
    public void setCreateName(Long createName) 
    {
        this.createName = createName;
    }

    public Long getCreateName() 
    {
        return createName;
    }
    public void setUpdateId(Long updateId) 
    {
        this.updateId = updateId;
    }

    public Long getUpdateId() 
    {
        return updateId;
    }
    public void setExpprice(String expprice) 
    {
        this.expprice = expprice;
    }

    public String getExpprice() 
    {
        return expprice;
    }
    public void setBprice(String bprice) 
    {
        this.bprice = bprice;
    }

    public String getBprice() 
    {
        return bprice;
    }
    public void setTprice(String tprice) 
    {
        this.tprice = tprice;
    }

    public String getTprice() 
    {
        return tprice;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setSuplair(Long suplair) 
    {
        this.suplair = suplair;
    }

    public Long getSuplair() 
    {
        return suplair;
    }
    public void setClientId(Long clientId) 
    {
        this.clientId = clientId;
    }

    public Long getClientId() 
    {
        return clientId;
    }
    public void setBackId(Long backId) 
    {
        this.backId = backId;
    }

    public Long getBackId() 
    {
        return backId;
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
            .append("createId", getCreateId())
            .append("createName", getCreateName())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("expprice", getExpprice())
            .append("bprice", getBprice())
            .append("tprice", getTprice())
            .append("tel", getTel())
            .append("suplair", getSuplair())
            .append("clientId", getClientId())
            .append("backId", getBackId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .toString();
    }
}
