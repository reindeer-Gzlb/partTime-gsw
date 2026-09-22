package com.ruoyi.system.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 黑名单列表对象 black
 *
 * @author ruoyi
 * @date 2022-06-27
 */
public class Black extends BaseEntity
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

    /** 账号ID */
    @Excel(name = "账号ID")
    private Long accountId;

    /** 帐号 */
    @Excel(name = "帐号")
    private String account;

    /** 游戏编码 */
    @Excel(name = "游戏编码")
    private String gameSn;

    /** 游戏名称 */
    @Excel(name = "游戏名称")
    private String gameName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 微信 */
    @Excel(name = "微信")
    private String wx;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 支付宝账号 */
    @Excel(name = "支付宝账号")
    private String paymentAccount;

    /** 录入时间 */
    @Excel(name = "录入时间")
    private String addTime;

    /** channel_name */
    @Excel(name = "channel_name")
    private String channelName;

    /** 省 */
    @Excel(name = "省")
    private String provinceName;

    /** 市 */
    @Excel(name = "市")
    private String cityName;

    /** 区 */
    @Excel(name = "区")
    private String areaName;

    /** 0、请求成功 1、请求失败 */
    @Excel(name = "0、请求成功 1、请求失败")
    private Long status;

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
    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public Long getAccountId()
    {
        return accountId;
    }
    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getAccount()
    {
        return account;
    }
    public void setGameSn(String gameSn)
    {
        this.gameSn = gameSn;
    }

    public String getGameSn()
    {
        return gameSn;
    }
    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public String getGameName()
    {
        return gameName;
    }
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getMobile()
    {
        return mobile;
    }
    public void setWx(String wx)
    {
        this.wx = wx;
    }

    public String getWx()
    {
        return wx;
    }
    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }

    public String getIdNo()
    {
        return idNo;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPaymentAccount(String paymentAccount)
    {
        this.paymentAccount = paymentAccount;
    }

    public String getPaymentAccount()
    {
        return paymentAccount;
    }
    public void setAddTime(String addTime)
    {
        this.addTime = addTime;
    }

    public String getAddTime()
    {
        return addTime;
    }
    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }

    public String getChannelName()
    {
        return channelName;
    }
    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

    public String getProvinceName()
    {
        return provinceName;
    }
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCityName()
    {
        return cityName;
    }
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getAreaName()
    {
        return areaName;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createId", getCreateId())
            .append("updateId", getUpdateId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("accountId", getAccountId())
            .append("account", getAccount())
            .append("gameSn", getGameSn())
            .append("gameName", getGameName())
            .append("mobile", getMobile())
            .append("wx", getWx())
            .append("idNo", getIdNo())
            .append("name", getName())
            .append("paymentAccount", getPaymentAccount())
            .append("addTime", getAddTime())
            .append("channelName", getChannelName())
            .append("remark", getRemark())
            .append("provinceName", getProvinceName())
            .append("cityName", getCityName())
            .append("areaName", getAreaName())
            .append("status", getStatus())
            .toString();
    }
}
