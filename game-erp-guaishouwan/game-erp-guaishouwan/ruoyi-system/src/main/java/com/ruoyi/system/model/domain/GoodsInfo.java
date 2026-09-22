package com.ruoyi.system.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * goods对象 goods_info
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Data
@ToString(includeFieldNames = false, onlyExplicitlyIncluded = true)
public class GoodsInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @ToString.Include
    private Long id;
    private List<Long> ids;

    /**
     * $column.columnComment
     */
    @ToString.Include
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;
    private String createName;
    private List<String> createIds;

    /**
     * $column.columnComment
     */
    @ToString.Include
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /**
     * 游戏id
     */
    @ToString.Include
    @Excel(name = "游戏id")
    private Long gameId;

    /**
     * 游戏名称
     */
    @ToString.Include
    @Excel(name = "游戏名称")
    private String gameName;
    private String salepartnerids;
    private String salepartnername;
    private String correspondjym;

    /**
     * 游戏类型：0手游 1端游
     */
    @ToString.Include
    @Excel(name = "游戏类型：手游 端游")
    private String gameType;

    /**
     * 操作系统id
     */
    @ToString.Include
    @Excel(name = "操作系统id")
    private Long systemId;

    /**
     * 操作系统名称 如	ios  安卓
     */
    @ToString.Include
    @Excel(name = "操作系统名称 如	ios  安卓")
    private String systemName;

    /**
     * 运营商id
     */
    @ToString.Include
    @Excel(name = "运营商id")
    private Long carrierId;

    /**
     * 例如QQ,微信等等
     */
    @ToString.Include
    @Excel(name = "例如QQ,微信等等")
    private String carrierName;

    /**
     * 服id
     */
    @ToString.Include
    @Excel(name = "服id")
    private Long groupId;

    /**
     * 服名称
     */
    @ToString.Include
    @Excel(name = "服名称")
    private String groupName;

    /**
     * 区id
     */
    @ToString.Include
    @Excel(name = "区id")
    private Long serverId;

    /**
     * 区名称
     */
    @ToString.Include
    @Excel(name = "区名称")
    private String serverName;

    /**
     * 商品标题
     */
    @ToString.Include
    @Excel(name = "商品标题")
    private String title;

    /**
     * 商品价格
     */
    @ToString.Include
    @Excel(name = "商品价格")
    private BigDecimal price;
    private BigDecimal newPrice;

    /**
     * 商品描述
     */
    @Excel(name = "商品描述")
    private String des;

    /**
     * 供货人
     */
    @ToString.Include
    @Excel(name = "供货人")
    private Long supplyId;

    /**
     * 销售状态
     */
    @Excel(name = "销售状态  0、待审核 1、未出售 2、出售中 3、已售 4、已下架 5、问题账号 6、找回账号 , 10 挂售,11 分期中 12 已封禁 13 已私下 ，14 赔付中  15 永久出售, 16 在手封禁,17 红锁赔付 99、审核失败")
    private Integer saleState;

    /**
     * 删除标志 0、未删除  1、已删除
     */
    @Excel(name = "删除标志 0、未删除  1、已删除")
    private Long del;

    /**
     * 1、自营商品 2、代售商品
     */
    @Excel(name = "1、自营商品 2、代售商品")
    private Integer accountType;

    /**
     * 1、显示 2、不显示
     */
    @Excel(name = "1、显示 2、不显示")
    private String isshow;

    /**
     * 标题图片
     */
    private String titleImg;

    /**
     * 问题备注
     */
    @ToString.Include
    private String problemDes;

    /**
     * 下架备注
     */
    @ToString.Include
    private String downDes;
    /**
     * 找回备注
     */
    @ToString.Include
    private String retrieveDes;

    /**
     * 三方id 用于判断接口是否重复传递
     */
    @ToString.Include
    private String thirdId;

    /**
     * 账号
     */
    @ToString.Include
    private String accountNumber;

    /**
     * 密码
     */
    @ToString.Include
    private String accountPassword;

    /**
     * 密保手机
     */
    @ToString.Include
    private String securityPhone;

    /**
     * 交易猫ID
     */
    private String jymtask;

    private String allField;

    @JsonIgnore
    @JsonProperty
    public String getAllField() {
        return allField;
    }

    //1  自首  2、代售
    private Integer ifself;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 重要信息备注
     */
    @ToString.Include
    private String importantRemark;

    @ToString.Include
    private String shiming;

    /**
     * 商品类型ids
     */
    @ToString.Include
    private String goodsTypeIds;

    /**
     * 商品类型值
     */
    @ToString.Include
    private String goodsTypeValues;


    private String selltype;
    private String note;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saletime;
    private Long saleid;
    private Long telid;
    private String telnote;

    private String salaName;
    private String supplyName;
    private String createTimeStart;
    private String createTimeEnd;

    private Integer browseNum; //浏览次数
    private String icon;  //游戏图标

    /**
     * 出租状态 1:不出租 2:未出租 3：出租中
     */
    private Integer hireState;
    /**
     * 时租金
     */
    private BigDecimal hireHour;
    /**
     * 夜租金
     */
    private BigDecimal hireNight;
    /**
     * 天租金
     */
    private BigDecimal hireDay;
    /**
     * 周租金
     */
    private BigDecimal hireWeek;

    /**
     * 图片展示方式 1：并排 2：下拉 默认为1
     */
    private Integer imgShow;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 审核备注
     */
    private String auditDes;

    /**
     * 是否是顶级账号 1:顶级 2:不是顶级
     */
    private Integer isTop;

    /**
     * 分路类型id
     */
    private Long branchRoadTypeId;

    /**
     * 分路类型名称
     */
    private String branchRoadTypeName;

    /**
     * 保障信息
     */
    private String assurance;

    /**
     * 分路类型的值
     */
    private Integer branchRoadValue;

    private Long orderRecId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    private String shimingImg;
    /**
     *微信昵称
     */
    private String vxNickName;

    /**
     *微信昵称
     */
    private String attributione;

    /**
     *绑定邮箱
     */
    private String email;
    /**
     *  业务编号
     */
    private String businessCode;    /**
     *  回收事见
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recoveryTime;
    /**
     * 一手回收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recoveryFirstTime;
    /**
     * 回收事见
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recoveryTimeStart;
    /**  回收事见
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recoveryTimeEnd;
    /**
     * 一手回收价
     */
    private BigDecimal fastPrice;

    private String toPlatform;

    private String gameThirdId;

    private String gsState;

    /**
     * 是否推送  1开   2关
     */
    private Long isPush;
    /**
     * 最低出售价
     */
    private BigDecimal downPrice;
    /**
     * 全球账户
     */
    private String quanqiuAccount;
    /**
     * 全球密码
     */
    private String quanqiuPassword;
    /**
     * 全球邮箱
     */
    private String quanqiuEmail;
    /**
     * 全球邮箱密码
     */
    private String quanqiuEmailPassword;
    /**
     * 全球备注
     */
    private String quanqiuRemark;

    /**
     * 浏览量
     */

    private Integer lookCount;


    /**
     * ARS手机号
     */
    private String arsPhone;

    /**
     * 绑定邮箱
     */
    private String bindEmail;

    /**
     * 是否特价
     *  0 默认  1 特价
     */
    private Integer isOnSpecialOffer;


    /**
     * 是否需要包赔
     *  0  默认 1 需要包赔  2 免费包赔
     */
    private Integer isGuarantee;
}
