package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售订单对象 account_order
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Data
public class AccountOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;

    /** 终端编号 */
    @Excel(name = "终端编号")
    private Long accountId;

    /** 实名状态 */
    @Excel(name = "实名状态")
    private String realName;

    private List<String> jietuList;
    private List<String> otherList;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;
    private String orderStatus;

    private Long superId;
    private String superName;
    private Long saleId;
    private String saleName;
    private String ziliao;

    private String tel;
    private String account;
    private String pwd;
    private String regional; //大区
    private String errmsg; //失败原因
    private Date orderTime; //完结时间

    private Integer heros;
    private Integer skins;
    //平台售价
    private BigDecimal price;
    //供货售价
    private BigDecimal supplyprice;
    //分销售价
    private BigDecimal shopprice;
    private String des;
    private String supyremake;
    /** 真实售价 */
    @Excel(name = "真实售价")
    private BigDecimal realPrice;
    private String dataType;

    public String getDataType() {
        return dataType;
    }

    public BigDecimal getSupplyprice() {
        return supplyprice;
    }

    public void setSupplyprice(BigDecimal supplyprice) {
        this.supplyprice = supplyprice;
    }

    public BigDecimal getShopprice() {
        return shopprice;
    }

    public void setShopprice(BigDecimal shopprice) {
        this.shopprice = shopprice;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<String> getJietuList() {
        return jietuList;
    }

    public void setJietuList(List<String> jietuList) {
        this.jietuList = jietuList;
    }

    public List<String> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<String> otherList) {
        this.otherList = otherList;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getZiliao() {
        return ziliao;
    }

    public void setZiliao(String ziliao) {
        this.ziliao = ziliao;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getHeros() {
        return heros;
    }

    public void setHeros(Integer heros) {
        this.heros = heros;
    }

    public Integer getSkins() {
        return skins;
    }

    public void setSkins(Integer skins) {
        this.skins = skins;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSupyremake() {
        return supyremake;
    }

    public void setSupyremake(String supyremake) {
        this.supyremake = supyremake;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }
}
