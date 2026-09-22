package com.ruoyi.system.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】对象 backlist
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
public class Backlist extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;
    private List<Long> extIds;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;
    private Long createBojiaId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /**
     * 游戏类型
     */
    @Excel(name = "游戏类型")
    private String type;
    /**
     * 游戏id
     */
    @Excel(name = "游戏id")
    private Long gameId;
    private List<String> types;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String des;

    /**
     * 区服ids 固定格式好前端识别
     */
    @Excel(name = "区服")
    private String regionIds;

    /**
     * 区服
     */
    @Excel(name = "区服")
    private String region;

    /**
     * 实名
     */
    @Excel(name = "实名")
    private String relname;

    /**
     * 实名图片
     */
    @Excel(name = "图片")
    private String relnameImg;

    /**
     * 期望价格
     */
    @Excel(name = "期望价格")
    private String expprice;

    /**
     * 账号
     */
    @Excel(name = "账号")
    private String account;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String tel;

    /**
     * 状态
     */
    @Excel(name = "状态")
    /**
     *   报价列表
     0 、待报价
     4、已取消
     2、已接受
     3、已完成
     5、拒绝报价
     */
    private Long status;
    private Long noStatus;

//    0,未报价  2 已报价

    /**
     * 报价
     */
    private String bprice;

    /**
     * 0，已报价 1，已拒绝 2，已同意
     */
    private String bstatus;

    private String img;

    private String rmsg;

    /**
     * 报价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date btime;

    /**
     * 报价人数
     */
    private Integer bcountt;
    /**
     * 最高报价
     */
    private String maxPrice;
    /**
     * 商户userId
     */
    private Long supplyUserId;


    private String supplyUserName;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 分区  1 回收链接  2 官网  3三方
     * sql 添加字符 `partition`
     */
    private Integer partition;
    /**
     * 转出状态 1我的回收 2回收联盟
     */
    private Integer transfer;

    /**
     * 三方userId
     */
    private String otherUserId;

    /**
     * 是否上下架 1上架 2下架
     */
    private Integer state;


    /**
     * 绑定商品信息id
     */
    private Long gid;
    /**
     * 绑定包赔信息id
     */
    private Long bcId;

    /**
     * 回收状态 1带回收，2回收中，3已回收
     */
    private Integer backState;

    /**
     * backCompensate ID
     * 绑定包赔信息id
     */
    private Long cpsId;
    /**
     * 包赔商名称
     */
    private String cpsName;
    /**
     * 回收价格
     */
    private String hsPrice;
    /**
     * 回收状态是否上架
     */
    private Integer isState;
    /**
     * 回收回显
     */
    private Map accountContent;

    /**
     * 是否同意提供包赔信息 1同意 2不同意
     */
    private Integer isAgreeBp;

    /**
     * 官网链接 回收数据长文本
     */
    private String content;

    private Integer groupState;

    /**
     * 是否是黑号：1是
     */
    private Integer isBlack;


    /**
     * 押金
     */
    private BigDecimal depositAmount;

    private String ip;
    private String ipAddr;

    /**
     * 移动支付记录id
     */
    @Excel(name = "移动支付记录id")
    private Long payRecordId;

    /**
     * 支付状态 1未支付 2已支付 3已撤销 4已完成 5已退款
     */
    @Excel(name = "支付状态 1未支付 2已支付 3已撤销 4已完成 5已退款")
    private Integer payStatus;

    /**
     * 订单支付时间
     */
    @Excel(name = "订单支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

}
