package com.ruoyi.system.outto.dto.yx7881.bean;

/**
 * 商品编辑实体bean
 */
public class EditGoodsBean {

    private String goodsId;

    private Double price;

    private String stock;

    private String userId;

    private Integer pernum;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPernum() {
        return pernum;
    }

    public void setPernum(Integer pernum) {
        this.pernum = pernum;
    }
}
