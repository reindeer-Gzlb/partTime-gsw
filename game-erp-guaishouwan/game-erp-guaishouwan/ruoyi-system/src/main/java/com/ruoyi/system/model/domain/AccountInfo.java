package com.ruoyi.system.model.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.model.vo.SelectRetail;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 游戏账号对象 account_info
 *
 * @author ruoyi
 * @date 2022-05-04
 */
@Data
public class AccountInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;
    private Long ydid;

    /** $column.columnComment */
    private Long createId;
    private Long telid;
    private List<String> createIds;
    private List<String> cmdSupplyIds;

    private Long retailId;
    private Long retailLogin;
    private Long bid;
    private Integer pageNum;
    private Integer pageSize;

    private String outId;
    private String ldvt;
    private String leishenid;

    private Integer page;
    private Integer intercount;
    private Integer pageLimit;

    private Long kjsid;

    private List<Long> ids;
    private List<String> games;

    //1  自首  2、代售
    private Integer ifself;

    private List<String> accpuntTypes;


    private Long jymid;

    private List<SelectRetail> selectRetails;

    public Long getYdid() {
        return ydid;
    }

    public void setYdid(Long ydid) {
        this.ydid = ydid;
    }

    public Long getRetailLogin() {
        return retailLogin;
    }

    public void setRetailLogin(Long retailLogin) {
        this.retailLogin = retailLogin;
    }

    public String getLdvt() {
        return ldvt;
    }

    public void setLdvt(String ldvt) {
        this.ldvt = ldvt;
    }

    public String getLeishenid() {
        return leishenid;
    }

    public void setLeishenid(String leishenid) {
        this.leishenid = leishenid;
    }

    /**
     * 当前登录人
     */
    private Long loginId;

    /** 账号 */
    @Excel(name = "账号")
    private String accountId;
    private String isshow;
    private String zid;
    private String offres;
    private Date offtime;

    private String uuid;
    private String sboid;
    private List<String> uuids;
    private List<String> exsUuids;
    private String interType;
    private String account;
    private String tel;
    private String pwd;
    private String buyremrk;
    private String buyName;

    /**
     * 营地ID
     */
    @Excel(name = "营地ID")
    private String campId;
    private String campIdLike;

    private Integer rise;

    /**
     * 标题图片
     */
    private String titleImg;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;
    private List<String> types;

    private String desQur;

    //交易猫批次id
    private Long jymBatchId;

    private List<String> desArr;

    /** 游戏大区 */
    @Excel(name = "区服")
    private String regional;

    private List<String> regionalAddr;
    private List<String> levelAddr;
    private List<Long> supplyIdAddr;
    private List<Long> salesIdAddr;

    private List<String> skinList;

    @Excel(name = "售卖类型")
    private String selltype;

    /** 段位 */
    @Excel(name = "实名认证")
    private String level;

    /** 英雄数量 */
    @Excel(name = "英雄数量")
    private Integer heroes;

    /** 皮肤数量 */
    @Excel(name = "皮肤数量")
    private Integer skins;

    /** 英雄数量 */
    @Excel(name = "属性一")
    private Integer attr1;

    /** 皮肤数量 */
    @Excel(name = "属性二")
    private Integer attr2;

    /** 套装 */
    @Excel(name = "套装")
    private Integer suit;

    /** 枪皮 */
    @Excel(name = "枪皮")
    private Integer guns;

    /** 讲解 */
    @Excel(name = "简介")
    private String des;

    /** 价格 */
    @Excel(name = "出售价格")
    private BigDecimal price;

    private BigDecimal newPrice;

    /** 供货人 */
    @Excel(name = "供货人")
    private Long supplyId;

    private String supplyName;

    private Long configId;

    private Long cmdSupplyId;
    private String cmdSupplyName;

    private String ldid;
    private String bqwid;

    /** 客服 */
    @Excel(name = "客服")
    private Long salesId;
    private String salesName;
    private Long buyUid;

    /** 销售状态
     *  1 - 99 销售状态
     *  100 - 199 回收状态,100:待回收,101:已收,102:问题账号
     *  200 - 299 审核包赔状态,200:待审核,201：审核通过,202:审核未通过
     * */
    @Excel(name = "销售状态  0、待审核 1、未出售 2、出售中 3、已售 4、问题账号 5、找回账号 6、超时未售 7、自售账号")
    private Integer saleState;
    private Integer delSaleState;

    private String saleStateName;

    private Date polishtime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updateId;

    private String updateName;

    /** 提取账号 */
    @Excel(name = "提取账号")
    private String extractAccount;

    /** 提取密码 */
    @Excel(name = "提取密码")
    private String extractPwd;

    /** 成本价格 */
    @Excel(name = "成本价格")
    private BigDecimal costPrice;

    /** 自编号 */
    @Excel(name = "账号备注")
    private String sinNum;

    /** 游戏名称 */
    @Excel(name = "自编号")
    private String gameName;

    /** 提取手机号 */
    @Excel(name = "提取手机号")
    private String extractTel;

    /** 买家ID */
    @Excel(name = "买家ID")
    private String buyId;
    /** 真实售价 */
    @Excel(name = "真实售价")
    private BigDecimal realPrice;

    /**
     * 超时未售天数
     */
    private Integer timeout;
    private String addDayTime;

    private Long auditId;

    private String auditName;
    private String auditState;

    private String note;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleTime;

    /**
     *
     */
    private  List<Long> exeIds;

    /**
     * 销售开始和结束时间
     */
    private Date saleTimeStart;
    private Date saleTimeEnd;
    /**
     * g更新开始和结束时间
     */
    private Date updateTimeStart;
    private Date updateTimeEnd;

    /**
     * 导入开始和结束时间
     */
    private Date createTimeStart;
    private Date createTimeEnd;

    /** 购买备注 */
    private String remake;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /**
     * 角色操作员
     */
    private String operType;

    private BigDecimal priceStart;
    private BigDecimal priceEnd;

    private Integer heroStart;
    private Integer heroEnd;

    private Integer skinStart;
    private Integer skinEnd;

    private Integer suitStart;
    private Integer suitEnd;

    private Integer gunsStart;
    private Integer gunsEnd;

    private List<Map<String,Object>> urlArr;

    private String sort;
    private String sortColumn;

    /**
     * 供货商权重排序
     */
    private int odr;
    /**
     * 1、特价 0、非特价
     */
    private String accType;
    private String videoUrl;
    /**
     * 特价
     */
    private BigDecimal specialPrice;
    private String videoName;
    /**
     * 1、自营号 2、代售号 3、终端数据
     */
    private String accountType;


    /** 回收状态:0:待回收，1、已回收，2、未回收 */
    @Excel(name = "回收状态:0:待回收，1、已回收，2、未回收,3、资源池状态")
    private Integer recoveryState;

    /** 回收客服 */
    @Excel(name = "回收客服")
    private Long recoveryId;

    /** 是否审核包赔,0:是，1:否 */
    @Excel(name = "是否审核包赔,0:是，1:否")
    private Integer auditFlag;

    /** 回收备注 */
    @Excel(name = "回收备注")
    private String recoveryNote;

    /** 审核备注 */
    @Excel(name = "审核备注")
    private String auditNote;

    /** 审核包赔状态:0:待审核，1、审核通过，2、未通过 */
    @Excel(name = "审核包赔状态:0:待审核，1、审核通过，2、未通过")
    private Integer verifyState;

    /** 审核包赔客服 */
    @Excel(name = "审核包赔客服")
    private Long verifyId;

    /** 回收客服名称 */
    @Excel(name = "回收客服名称")
    private String recoveryName;

    /** 审核包赔客服名称 */
    @Excel(name = "审核包赔客服名称")
    private String verifyName;

    private String orderNo;

    private String author;

    private String imgList;

    private String sdzId;

    private String kangehaoid;

    private String mhwid;

    private String YX7881id;

    public String getMhwid() {
        return mhwid;
    }

    public void setMhwid(String mhwid) {
        this.mhwid = mhwid;
    }

    /**
     * 0 未打
     * 1 已打
     */
    private int lable;

    private String userKey;

    private String acclable;

    private int del;

    private List<String> exsCreateId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
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

    public Long getRetailId() {
        return retailId;
    }

    public void setRetailId(Long retailId) {
        this.retailId = retailId;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getIntercount() {
        return intercount;
    }

    public void setIntercount(Integer intercount) {
        this.intercount = intercount;
    }

    public Integer getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }

    public Long getKjsid() {
        return kjsid;
    }

    public void setKjsid(Long kjsid) {
        this.kjsid = kjsid;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    public Integer getIfself() {
        return ifself;
    }

    public void setIfself(Integer ifself) {
        this.ifself = ifself;
    }

    public List<String> getAccpuntTypes() {
        return accpuntTypes;
    }

    public void setAccpuntTypes(List<String> accpuntTypes) {
        this.accpuntTypes = accpuntTypes;
    }

    public Long getJymid() {
        return jymid;
    }

    public void setJymid(Long jymid) {
        this.jymid = jymid;
    }

    public List<SelectRetail> getSelectRetails() {
        return selectRetails;
    }

    public void setSelectRetails(List<SelectRetail> selectRetails) {
        this.selectRetails = selectRetails;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getOffres() {
        return offres;
    }

    public void setOffres(String offres) {
        this.offres = offres;
    }

    public Date getOfftime() {
        return offtime;
    }

    public void setOfftime(Date offtime) {
        this.offtime = offtime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSboid() {
        return sboid;
    }

    public void setSboid(String sboid) {
        this.sboid = sboid;
    }

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }

    public List<String> getExsUuids() {
        return exsUuids;
    }

    public void setExsUuids(List<String> exsUuids) {
        this.exsUuids = exsUuids;
    }

    public String getInterType() {
        return interType;
    }

    public void setInterType(String interType) {
        this.interType = interType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBuyremrk() {
        return buyremrk;
    }

    public void setBuyremrk(String buyremrk) {
        this.buyremrk = buyremrk;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getCampIdLike() {
        return campIdLike;
    }

    public void setCampIdLike(String campIdLike) {
        this.campIdLike = campIdLike;
    }

    public Integer getRise() {
        return rise;
    }

    public void setRise(Integer rise) {
        this.rise = rise;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getDesQur() {
        return desQur;
    }

    public void setDesQur(String desQur) {
        this.desQur = desQur;
    }

    public Long getJymBatchId() {
        return jymBatchId;
    }

    public void setJymBatchId(Long jymBatchId) {
        this.jymBatchId = jymBatchId;
    }

    public List<String> getDesArr() {
        return desArr;
    }

    public void setDesArr(List<String> desArr) {
        this.desArr = desArr;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public List<String> getRegionalAddr() {
        return regionalAddr;
    }

    public void setRegionalAddr(List<String> regionalAddr) {
        this.regionalAddr = regionalAddr;
    }

    public List<String> getLevelAddr() {
        return levelAddr;
    }

    public void setLevelAddr(List<String> levelAddr) {
        this.levelAddr = levelAddr;
    }

    public List<Long> getSupplyIdAddr() {
        return supplyIdAddr;
    }

    public void setSupplyIdAddr(List<Long> supplyIdAddr) {
        this.supplyIdAddr = supplyIdAddr;
    }

    public List<Long> getSalesIdAddr() {
        return salesIdAddr;
    }

    public void setSalesIdAddr(List<Long> salesIdAddr) {
        this.salesIdAddr = salesIdAddr;
    }

    public List<String> getSkinList() {
        return skinList;
    }

    public void setSkinList(List<String> skinList) {
        this.skinList = skinList;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getHeroes() {
        return heroes;
    }

    public void setHeroes(Integer heroes) {
        this.heroes = heroes;
    }

    public Integer getSkins() {
        return skins;
    }

    public void setSkins(Integer skins) {
        this.skins = skins;
    }

    public Integer getAttr1() {
        return attr1;
    }

    public void setAttr1(Integer attr1) {
        this.attr1 = attr1;
    }

    public Integer getAttr2() {
        return attr2;
    }

    public void setAttr2(Integer attr2) {
        this.attr2 = attr2;
    }

    public Integer getSuit() {
        return suit;
    }

    public void setSuit(Integer suit) {
        this.suit = suit;
    }

    public Integer getGuns() {
        return guns;
    }

    public void setGuns(Integer guns) {
        this.guns = guns;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getCmdSupplyId() {
        return cmdSupplyId;
    }

    public void setCmdSupplyId(Long cmdSupplyId) {
        this.cmdSupplyId = cmdSupplyId;
    }

    public String getCmdSupplyName() {
        return cmdSupplyName;
    }

    public void setCmdSupplyName(String cmdSupplyName) {
        this.cmdSupplyName = cmdSupplyName;
    }

    public String getLdid() {
        return ldid;
    }

    public void setLdid(String ldid) {
        this.ldid = ldid;
    }

    public String getBqwid() {
        return bqwid;
    }

    public void setBqwid(String bqwid) {
        this.bqwid = bqwid;
    }

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public Long getBuyUid() {
        return buyUid;
    }

    public void setBuyUid(Long buyUid) {
        this.buyUid = buyUid;
    }

    public Integer getSaleState() {
        return saleState;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
    }

    public Integer getDelSaleState() {
        return delSaleState;
    }

    public void setDelSaleState(Integer delSaleState) {
        this.delSaleState = delSaleState;
    }

    public String getSaleStateName() {
        return saleStateName;
    }

    public void setSaleStateName(String saleStateName) {
        this.saleStateName = saleStateName;
    }

    public Date getPolishtime() {
        return polishtime;
    }

    public void setPolishtime(Date polishtime) {
        this.polishtime = polishtime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getExtractAccount() {
        return extractAccount;
    }

    public void setExtractAccount(String extractAccount) {
        this.extractAccount = extractAccount;
    }

    public String getExtractPwd() {
        return extractPwd;
    }

    public void setExtractPwd(String extractPwd) {
        this.extractPwd = extractPwd;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getSinNum() {
        return sinNum;
    }

    public void setSinNum(String sinNum) {
        this.sinNum = sinNum;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getExtractTel() {
        return extractTel;
    }

    public void setExtractTel(String extractTel) {
        this.extractTel = extractTel;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getAddDayTime() {
        return addDayTime;
    }

    public void setAddDayTime(String addDayTime) {
        this.addDayTime = addDayTime;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public List<Long> getExeIds() {
        return exeIds;
    }

    public void setExeIds(List<Long> exeIds) {
        this.exeIds = exeIds;
    }

    public Date getSaleTimeStart() {
        return saleTimeStart;
    }

    public void setSaleTimeStart(Date saleTimeStart) {
        this.saleTimeStart = saleTimeStart;
    }

    public Date getSaleTimeEnd() {
        return saleTimeEnd;
    }

    public void setSaleTimeEnd(Date saleTimeEnd) {
        this.saleTimeEnd = saleTimeEnd;
    }

    public Date getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(Date updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public Date getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public BigDecimal getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(BigDecimal priceStart) {
        this.priceStart = priceStart;
    }

    public BigDecimal getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(BigDecimal priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Integer getHeroStart() {
        return heroStart;
    }

    public void setHeroStart(Integer heroStart) {
        this.heroStart = heroStart;
    }

    public Integer getHeroEnd() {
        return heroEnd;
    }

    public void setHeroEnd(Integer heroEnd) {
        this.heroEnd = heroEnd;
    }

    public Integer getSkinStart() {
        return skinStart;
    }

    public void setSkinStart(Integer skinStart) {
        this.skinStart = skinStart;
    }

    public Integer getSkinEnd() {
        return skinEnd;
    }

    public void setSkinEnd(Integer skinEnd) {
        this.skinEnd = skinEnd;
    }

    public Integer getSuitStart() {
        return suitStart;
    }

    public void setSuitStart(Integer suitStart) {
        this.suitStart = suitStart;
    }

    public Integer getSuitEnd() {
        return suitEnd;
    }

    public void setSuitEnd(Integer suitEnd) {
        this.suitEnd = suitEnd;
    }

    public Integer getGunsStart() {
        return gunsStart;
    }

    public void setGunsStart(Integer gunsStart) {
        this.gunsStart = gunsStart;
    }

    public Integer getGunsEnd() {
        return gunsEnd;
    }

    public void setGunsEnd(Integer gunsEnd) {
        this.gunsEnd = gunsEnd;
    }

    public List<Map<String, Object>> getUrlArr() {
        return urlArr;
    }

    public void setUrlArr(List<Map<String, Object>> urlArr) {
        this.urlArr = urlArr;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public int getOdr() {
        return odr;
    }

    public void setOdr(int odr) {
        this.odr = odr;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getRecoveryState() {
        return recoveryState;
    }

    public void setRecoveryState(Integer recoveryState) {
        this.recoveryState = recoveryState;
    }

    public Long getRecoveryId() {
        return recoveryId;
    }

    public void setRecoveryId(Long recoveryId) {
        this.recoveryId = recoveryId;
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public String getRecoveryNote() {
        return recoveryNote;
    }

    public void setRecoveryNote(String recoveryNote) {
        this.recoveryNote = recoveryNote;
    }

    public String getAuditNote() {
        return auditNote;
    }

    public void setAuditNote(String auditNote) {
        this.auditNote = auditNote;
    }

    public Integer getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(Integer verifyState) {
        this.verifyState = verifyState;
    }

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

    public String getRecoveryName() {
        return recoveryName;
    }

    public void setRecoveryName(String recoveryName) {
        this.recoveryName = recoveryName;
    }

    public String getVerifyName() {
        return verifyName;
    }

    public void setVerifyName(String verifyName) {
        this.verifyName = verifyName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        this.imgList = imgList;
    }

    public String getSdzId() {
        return sdzId;
    }

    public void setSdzId(String sdzId) {
        this.sdzId = sdzId;
    }

    public int getLable() {
        return lable;
    }

    public void setLable(int lable) {
        this.lable = lable;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getAcclable() {
        return acclable;
    }

    public void setAcclable(String acclable) {
        this.acclable = acclable;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public List<String> getExsCreateId() {
        return exsCreateId;
    }

    public void setExsCreateId(List<String> exsCreateId) {
        this.exsCreateId = exsCreateId;
    }

    public String getSelltype() {
        return selltype;
    }

    public void setSelltype(String selltype) {
        this.selltype = selltype;
    }

    public String getKangehaoid() {
        return kangehaoid;
    }

    public void setKangehaoid(String kangehaoid) {
        this.kangehaoid = kangehaoid;
    }
}
