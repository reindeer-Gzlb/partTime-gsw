package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 jymdata
 * 
 * @author ruoyi
 * @date 2023-03-27
 */
public class Jymdata extends BaseEntity
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

    /** 简介 */
    @Excel(name = "简介")
    private String des;
    private String shopurl;

    /** 唯一id */
    @Excel(name = "唯一id")
    private String recid;

    /** 商品编号 */
    @Excel(name = "商品编号")
    private String shopid;
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 游戏
     */
    private String game;

    /**
     * 价格
     */
    private String price;

    /** 大区 */
    @Excel(name = "大区")
    private String regional;

    public String getShopurl() {
        return shopurl;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setShopurl(String shopurl) {
        this.shopurl = shopurl;
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
    public void setDes(String des) 
    {
        this.des = des;
    }

    public String getDes() 
    {
        return des;
    }
    public void setRecid(String recid) 
    {
        this.recid = recid;
    }

    public String getRecid() 
    {
        return recid;
    }
    public void setShopid(String shopid) 
    {
        this.shopid = shopid;
    }

    public String getShopid() 
    {
        return shopid;
    }
    public void setRegional(String regional) 
    {
        this.regional = regional;
    }

    public String getRegional() 
    {
        return regional;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("des", getDes())
            .append("recid", getRecid())
            .append("shopid", getShopid())
            .append("regional", getRegional())
            .toString();
    }
}
