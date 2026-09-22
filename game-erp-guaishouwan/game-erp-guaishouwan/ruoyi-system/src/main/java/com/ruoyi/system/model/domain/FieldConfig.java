package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 游戏类型字段配置对象 field_config
 *
 * @author ruoyi
 * @date 2022-07-03
 */
public class FieldConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;
    private Long parentId;
    private BigDecimal valuation;
    private String imgUrl;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;

    /** 字段类型,单选，多选、输入框 */
    @Excel(name = "字段类型,单选，多选、输入框")
    private String fieldType;
    private List<String> fieldTypes;

    /** 字段名称 */
    @Excel(name = "字段名称")
    private String fieldName;

    List<Map<String,Object>> domains;

    /**
     * 0、回收页面
     * 1、编辑页面
     * 3、公用
     */
    private String cfgType;

    /** 字段值，当单选或者多选时可以输入 */
    @Excel(name = "字段值，当单选或者多选时可以输入")
    private String fieldValue;

    private List<String> fields;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setFieldType(String fieldType)
    {
        this.fieldType = fieldType;
    }

    public String getFieldType()
    {
        return fieldType;
    }
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldName()
    {
        return fieldName;
    }
    public void setFieldValue(String fieldValue)
    {
        this.fieldValue = fieldValue;
    }

    public String getFieldValue()
    {
        return fieldValue;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }

    public String getCfgType() {
        return cfgType;
    }

    public void setCfgType(String cfgType) {
        this.cfgType = cfgType;
    }

    public List<String> getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(List<String> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    public List<Map<String, Object>> getDomains() {
        return domains;
    }

    public void setDomains(List<Map<String, Object>> domains) {
        this.domains = domains;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public void setValuation(BigDecimal valuation) {
        this.valuation = valuation;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("fieldType", getFieldType())
            .append("fieldName", getFieldName())
            .append("fieldValue", getFieldValue())
            .append("sort", getSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
