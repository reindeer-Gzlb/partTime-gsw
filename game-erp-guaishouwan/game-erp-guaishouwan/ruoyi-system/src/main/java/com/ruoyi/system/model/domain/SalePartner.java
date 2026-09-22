package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 销售伙伴对象 sale_partner
 *
 * @author ruoyi
 * @date 2022-06-07
 */
public class SalePartner extends BaseEntity
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

    private List<String> ids;

    /** 销售伙伴 */
    @Excel(name = "销售伙伴")
    private String saleName;

    private String name;

    private String gameType;

    /** key */
    @Excel(name = "key")
    private String saleKey;

    /** 其他属性1 */
    @Excel(name = "其他属性1")
    private String other;

    /** 其他属性2 */
    @Excel(name = "其他属性2")
    private String twoOther;

    /** 其他属性3 */
    @Excel(name = "其他属性3")
    private String threeOther;

    private String status;
    private String type;

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 上浮百分比
     */
    private Long seleep;

    /**
     * 加价配置
     * @return
     */
    private String seleepConfig;


    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    /**
     * 所属供应商
     */
    private Long ofUser;

    private String updateName;
    private String createName;

    public Long getSeleep() {
        return seleep;
    }

    public void setSeleep(Long seleep) {
        this.seleep = seleep;
    }

    public Long getOfUser() {
        return ofUser;
    }

    public void setOfUser(Long ofUser) {
        this.ofUser = ofUser;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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
    public void setSaleName(String saleName)
    {
        this.saleName = saleName;
    }

    public String getSaleName()
    {
        return saleName;
    }
    public void setSaleKey(String saleKey)
    {
        this.saleKey = saleKey;
    }

    public String getSaleKey()
    {
        return saleKey;
    }
    public void setOther(String other)
    {
        this.other = other;
    }

    public String getOther()
    {
        return other;
    }
    public void setTwoOther(String twoOther)
    {
        this.twoOther = twoOther;
    }

    public String getTwoOther()
    {
        return twoOther;
    }
    public void setThreeOther(String threeOther)
    {
        this.threeOther = threeOther;
    }

    public String getThreeOther()
    {
        return threeOther;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("saleName", getSaleName())
            .append("saleKey", getSaleKey())
            .append("other", getOther())
            .append("twoOther", getTwoOther())
            .append("threeOther", getThreeOther())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getSeleepConfig() {
        return seleepConfig;
    }

    public void setSeleepConfig(String seleepConfig) {
        this.seleepConfig = seleepConfig;
    }
}
