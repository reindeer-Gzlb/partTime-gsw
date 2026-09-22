package com.ruoyi.system.h5;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 21:56
 */

public class GameDetails {

    /**
     * 游戏编号
     */
    private Long ZHID;
    /**
     * 游戏名字
     */
    private String YXMZ;

    private Date createTime;

    private String ifCollt;

    /**
     * 游戏类型
     */
    private String YXLX;
    /**
     * 游戏大区
     */
    private String YXDQ;
    /**
     * 皮肤
     */
    private Integer PF = 0;
    /**
     * 英雄
     */
    private Integer YX = 0;

    /**
     * 价格
     */
    private BigDecimal CSJG;

    /**
     * 段位
     */
    private String DW;
    private String att1;
    private String att2;

    /**
     * 枪皮
     */
    private Integer GUN = 0;


    private List<String> IMGS;

    private String videoUrl;

    /**
     * 账号是否特价
     */
    private String accType;
    /**
     * 特价价格
     */
    private BigDecimal specialPrice;

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getAtt1() {
        return att1;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public String getIfCollt() {
        return ifCollt;
    }

    public void setIfCollt(String ifCollt) {
        this.ifCollt = ifCollt;
    }

    /**
     * 枪套装
     */
    private Integer SUIT = 0;

    @JsonProperty("SUIT")
    public Integer getSUIT() {
        return SUIT;
    }

    public void setSUIT(Integer SUIT) {
        this.SUIT = SUIT;
    }

    /**
     * 简介
     */
    private String JJ;

    @JsonProperty("IMGS")
    public List<String> getIMGS() {
        return IMGS;
    }

    public void setIMGS(List<String> IMGS) {
        this.IMGS = IMGS;
    }

    @JsonProperty("ZHID")
    public Long getZHID() {
        return ZHID;
    }

    public void setZHID(Long ZHID) {
        this.ZHID = ZHID;
    }

    @JsonProperty("YXMZ")
    public String getYXMZ() {
        return YXMZ;
    }

    public void setYXMZ(String YXMZ) {
        this.YXMZ = YXMZ;
    }

    @JsonProperty("YXLX")
    public String getYXLX() {
        return YXLX;
    }

    public void setYXLX(String YXLX) {
        this.YXLX = YXLX;
    }
    @JsonProperty("YXDQ")
    public String getYXDQ() {
        return YXDQ;
    }

    public void setYXDQ(String YXDQ) {
        this.YXDQ = YXDQ;
    }
    @JsonProperty("PF")
    public Integer getPF() {
        return PF;
    }

    public void setPF(Integer PF) {
        this.PF = PF;
    }
    @JsonProperty("YX")
    public Integer getYX() {
        return YX;
    }

    public void setYX(Integer YX) {
        this.YX = YX;
    }
    @JsonProperty("CSJG")
    public BigDecimal getCSJG() {
        return CSJG;
    }

    public void setCSJG(BigDecimal CSJG) {
        this.CSJG = CSJG;
    }
    @JsonProperty("DW")
    public String getDW() {
        return DW;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }
    @JsonProperty("GUN")
    public Integer getGUN() {
        return GUN;
    }

    public void setGUN(Integer GUN) {
        this.GUN = GUN;
    }
    @JsonProperty("JJ")
    public String getJJ() {
        return JJ;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setJJ(String JJ) {
        this.JJ = JJ;
    }
}
