package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 供货商授权表对象 supr_auth
 *
 * @author ruoyi
 * @date 2022-06-14
 */
public class SuprAuth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    private String createName;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    private String updateName;

    /**
     * 加价比例
     */
    private BigDecimal addsi;

    private List<Long> saleIdAddr;

    /** 供货商ID */
    @Excel(name = "供货商ID")
    private Long suprId;
    private String suprName;

    private Long configId;

    private String configName;

    /** 授权伙伴id集合 */
    @Excel(name = "授权伙伴id集合")
    private String saleId;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
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
    public void setSuprId(Long suprId)
    {
        this.suprId = suprId;
    }

    public Long getSuprId()
    {
        return suprId;
    }
    public void setSaleId(String saleId)
    {
        this.saleId = saleId;
    }

    public String getSaleId()
    {
        return saleId;
    }

    public String getCreateName() {
        return createName;
    }

    public List<Long> getSaleIdAddr() {
        return saleIdAddr;
    }

    public void setSaleIdAddr(List<Long> saleIdAddr) {
        this.saleIdAddr = saleIdAddr;
    }

    public String getSuprName() {
        return suprName;
    }

    public void setSuprName(String suprName) {
        this.suprName = suprName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public BigDecimal getAddsi() {
        return addsi;
    }

    public void setAddsi(BigDecimal addsi) {
        this.addsi = addsi;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("suprId", getSuprId())
            .append("saleId", getSaleId())
            .toString();
    }
}
