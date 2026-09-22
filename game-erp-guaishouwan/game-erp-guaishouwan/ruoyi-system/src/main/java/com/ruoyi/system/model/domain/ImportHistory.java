package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 联调记录对象 import_history
 *
 * @author ruoyi
 * @date 2022-05-26
 */
public class ImportHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** 供应商id */
    @Excel(name = "供应商id")
    private Long userId;

    /** 导入数量 */
    @Excel(name = "导入数量")
    private Integer importCount;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplyName;
    private String recType;

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
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
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setImportCount(Integer importCount)
    {
        this.importCount = importCount;
    }

    public Integer getImportCount()
    {
        return importCount;
    }
    public void setSupplyName(String supplyName)
    {
        this.supplyName = supplyName;
    }

    public String getSupplyName()
    {
        return supplyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("userId", getUserId())
            .append("importCount", getImportCount())
            .append("supplyName", getSupplyName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
