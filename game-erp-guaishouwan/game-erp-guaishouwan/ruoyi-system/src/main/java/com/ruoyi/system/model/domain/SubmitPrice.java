package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户端下单对象 submit_price
 * 
 * @author ruoyi
 * @date 2023-03-22
 */
public class SubmitPrice extends BaseEntity
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

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long userId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long accountId;

    /** 价格 */
    @Excel(name = "价格")
    private Long price;

    /** 期望价格 */
    @Excel(name = "期望价格")
    private Long qprice;

    /** 手机号 */
    @Excel(name = "手机号")
    private String tel;

    /** 简介 */
    @Excel(name = "简介")
    private String des;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nikeName;

    /** 1 提交 2 交易中 3、交易完成 */
    @Excel(name = "1 提交 2 交易中 3、交易完成")
    private String state;

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
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }
    public void setQprice(Long qprice) 
    {
        this.qprice = qprice;
    }

    public Long getQprice() 
    {
        return qprice;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setDes(String des) 
    {
        this.des = des;
    }

    public String getDes() 
    {
        return des;
    }
    public void setNikeName(String nikeName) 
    {
        this.nikeName = nikeName;
    }

    public String getNikeName() 
    {
        return nikeName;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
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
            .append("accountId", getAccountId())
            .append("price", getPrice())
            .append("qprice", getQprice())
            .append("remark", getRemark())
            .append("tel", getTel())
            .append("des", getDes())
            .append("nikeName", getNikeName())
            .append("state", getState())
            .toString();
    }
}
