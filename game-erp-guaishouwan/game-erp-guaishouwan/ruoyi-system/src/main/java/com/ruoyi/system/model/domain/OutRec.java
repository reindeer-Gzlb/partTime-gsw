package com.ruoyi.system.model.domain;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 推送记录对象 out_rec
 *
 * @author ruoyi
 * @date 2022-06-07
 */
public class OutRec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;
    private String createName;

    /** 销售伙伴 */
    @Excel(name = "销售伙伴")
    private String saleId;

    /** 推送结果 */
    @Excel(name = "推送结果")
    private String result;

    /** 接口类型 */
    @Excel(name = "接口类型")
    private String inter;
    private String salecode;

    private Integer successCount;
    private Integer errCount;

    private String Id;

    private String req;
    private String res;
    private String type;

    private Long accountid;
    private Long supllyid;

    public String getSalecode() {
        return salecode;
    }

    public void setSalecode(String salecode) {
        this.salecode = salecode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    public Long getSupllyid() {
        return supllyid;
    }

    public void setSupllyid(Long supllyid) {
        this.supllyid = supllyid;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getRes() {
        return res;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getErrCount() {
        return errCount;
    }

    public void setErrCount(Integer errCount) {
        this.errCount = errCount;
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
    public void setResult(String result)
    {
        this.result = result;
    }

    public String getSaleId() {
        return saleId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getResult()
    {
        return result;
    }
    public void setInter(String inter)
    {
        this.inter = inter;
    }

    public String getInter()
    {
        return inter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("saleId", getSaleId())
            .append("result", getResult())
            .append("inter", getInter())
            .append("createTime", getCreateTime())
            .toString();
    }
}
