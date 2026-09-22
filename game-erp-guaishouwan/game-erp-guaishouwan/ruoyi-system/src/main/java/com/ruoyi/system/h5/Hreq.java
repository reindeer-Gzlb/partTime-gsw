package com.ruoyi.system.h5;

import com.ruoyi.system.model.domain.AddPrice;
import com.ruoyi.system.model.domain.RetailAddPrice;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 19:57
 */
public class Hreq {


    /**
     * userId
     */
    private Integer id;

    private String YXLX_ID;

    /**
     * 价格排序
     * 升序  asc
     * 降序 desc
     */
    private String picreOdr;

    /**
     * 综合排序
     * 升序  asc
     * 降序 desc
     */
    private Integer commonOdr;


    private List<String> gjzlist;

    /**
     * 游戏类型
     */
    private String YXLX;
    /**
     * 账号id
     */
    private String ZHID;
    /**
     * 账号id 关键字
     */
    private String ZDYGJZ;
    /**
     * 段位
     */
    private String DWGJZ;
    /**
     * 关键词
     */
    private String JJGJZ;
    /**
     * 游戏大区
     */
    private String YXDQ;
    /**
     * 价格
     */
    private String CSJGMin;
    private String CSJGMax;

    /**
     * 英雄
     */
    private String YXMin;
    private String YXMax;
    /**
     * 皮肤
     */
    private String PFMin;
    private String PFMax;
    /**
     * 套装
     */
    private String SuitMin;
    private String SuitMax;
    /**
     * 枪皮
     */
    private String GunSkinMin;
    private String GunSkinMax;
    private String publicOdr;

    private int page;
    private int rows;

    private List<String> skinList;
    private Set<Long> infoIds;


    private Long retailLogin;

    private String[] gjzList;

    List<RetailAddPrice> retailAddPrices;

    private List<AddPrice> addPriceList;

    private List<String> createIds;
    private List<String> cmdSupplyIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYXLX_ID() {
        return YXLX_ID;
    }

    public void setYXLX_ID(String YXLX_ID) {
        this.YXLX_ID = YXLX_ID;
    }

    public String getPicreOdr() {
        return picreOdr;
    }

    public void setPicreOdr(String picreOdr) {
        this.picreOdr = picreOdr;
    }

    public Integer getCommonOdr() {
        return commonOdr;
    }

    public void setCommonOdr(Integer commonOdr) {
        this.commonOdr = commonOdr;
    }

    public List<String> getGjzlist() {
        return gjzlist;
    }

    public void setGjzlist(List<String> gjzlist) {
        this.gjzlist = gjzlist;
    }

    public String getYXLX() {
        return YXLX;
    }

    public void setYXLX(String YXLX) {
        this.YXLX = YXLX;
    }

    public String getZHID() {
        return ZHID;
    }

    public void setZHID(String ZHID) {
        this.ZHID = ZHID;
    }

    public String getZDYGJZ() {
        return ZDYGJZ;
    }

    public void setZDYGJZ(String ZDYGJZ) {
        this.ZDYGJZ = ZDYGJZ;
    }

    public Long getRetailLogin() {
        return retailLogin;
    }

    public void setRetailLogin(Long retailLogin) {
        this.retailLogin = retailLogin;
    }

    public String getDWGJZ() {
        return DWGJZ;
    }

    public void setDWGJZ(String DWGJZ) {
        this.DWGJZ = DWGJZ;
    }

    public String getJJGJZ() {
        return JJGJZ;
    }

    public void setJJGJZ(String JJGJZ) {
        this.JJGJZ = JJGJZ;
    }

    public String getYXDQ() {
        return YXDQ;
    }

    public void setYXDQ(String YXDQ) {
        this.YXDQ = YXDQ;
    }

    public String getCSJGMin() {
        return CSJGMin;
    }

    public void setCSJGMin(String CSJGMin) {
        this.CSJGMin = CSJGMin;
    }

    public String getCSJGMax() {
        return CSJGMax;
    }

    public void setCSJGMax(String CSJGMax) {
        this.CSJGMax = CSJGMax;
    }

    public String getYXMin() {
        return YXMin;
    }

    public void setYXMin(String YXMin) {
        this.YXMin = YXMin;
    }

    public String getYXMax() {
        return YXMax;
    }

    public void setYXMax(String YXMax) {
        this.YXMax = YXMax;
    }

    public String getPFMin() {
        return PFMin;
    }

    public void setPFMin(String PFMin) {
        this.PFMin = PFMin;
    }

    public String getPFMax() {
        return PFMax;
    }

    public void setPFMax(String PFMax) {
        this.PFMax = PFMax;
    }

    public String getSuitMin() {
        return SuitMin;
    }

    public void setSuitMin(String suitMin) {
        SuitMin = suitMin;
    }

    public String getSuitMax() {
        return SuitMax;
    }

    public void setSuitMax(String suitMax) {
        SuitMax = suitMax;
    }

    public String getGunSkinMin() {
        return GunSkinMin;
    }

    public void setGunSkinMin(String gunSkinMin) {
        GunSkinMin = gunSkinMin;
    }

    public String getGunSkinMax() {
        return GunSkinMax;
    }

    public void setGunSkinMax(String gunSkinMax) {
        GunSkinMax = gunSkinMax;
    }

    public String getPublicOdr() {
        return publicOdr;
    }

    public void setPublicOdr(String publicOdr) {
        this.publicOdr = publicOdr;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<String> getSkinList() {
        return skinList;
    }

    public void setSkinList(List<String> skinList) {
        this.skinList = skinList;
    }

    public Set<Long> getInfoIds() {
        return infoIds;
    }

    public void setInfoIds(Set<Long> infoIds) {
        this.infoIds = infoIds;
    }

    public String[] getGjzList() {
        return gjzList;
    }

    public void setGjzList(String[] gjzList) {
        this.gjzList = gjzList;
    }

    public List<RetailAddPrice> getRetailAddPrices() {
        return retailAddPrices;
    }

    public void setRetailAddPrices(List<RetailAddPrice> retailAddPrices) {
        this.retailAddPrices = retailAddPrices;
    }

    public List<AddPrice> getAddPriceList() {
        return addPriceList;
    }

    public void setAddPriceList(List<AddPrice> addPriceList) {
        this.addPriceList = addPriceList;
    }

    public List<String> getCreateIds() {
        return createIds;
    }

    public void setCreateIds(List<String> createIds) {
        this.createIds = createIds;
    }

    public List<String> getCmdSupplyIds() {
        return cmdSupplyIds;
    }

    public void setCmdSupplyIds(List<String> cmdSupplyIds) {
        this.cmdSupplyIds = cmdSupplyIds;
    }
}
