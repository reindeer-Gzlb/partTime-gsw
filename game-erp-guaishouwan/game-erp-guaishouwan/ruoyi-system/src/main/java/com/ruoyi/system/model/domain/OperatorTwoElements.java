package com.ruoyi.system.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zyy
 * @date 2023-06-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "operator_two_elements")
public class OperatorTwoElements {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 查询的手机号码
     */
    @NotNull(message = "参数inputMobile不能为空")
    private String inputMobile;
    /**
     * 查询的姓名
     */
    @NotNull(message = "参数inputName不能为空")
    private String inputName;

    /**
     * 归属地编号
     */
    @TableField(value = "bank_addrCode")
    @JSONField(name = "bank_addrCode")
    private String bankAddrCode;
    /**
     * 所属地区市
     */
    @JSONField(name = "bank_city")
    private String bankCity;
    /**
     * 身份证前6位编号
     */
    @TableField(value = "bank_idCode")
    @JSONField(name = "bank_idCode")
    private String bankIdCode;
    /**
     * 手机号码
     */
    @JSONField(name = "bank_mobile")
    private String bankMobile;
    /**
     * 运营商类型
     */
    @TableField(value = "bank_mobileType")
    @JSONField(name = "bank_mobileType")
    private String bankMobileType;
    /**
     * 消息说明
     */
    @JSONField(name = "bank_msg")
    private String bankMsg;
    /**
     * 开户名,即身份证上名字
     */
    @JSONField(name = "bank_name")
    private String bankName;
    /**
     * 运营商
     */
    @JSONField(name = "bank_provider")
    private String bankProvider;
    /**
     * 所属省份
     */
    @JSONField(name = "bank_province")
    private String bankProvince;
    /**
     * 01:验证通过 02:验证不通过 202:无法验证 203:异常情况 204:姓名输入错误 205:身份证号输入错误 206:银行卡号输入错误
     */
    @JSONField(name = "bank_status")
    private String bankStatus;
    /**
     * 手机号码所在地区区号
     */
    @TableField(value = "bank_telCode")
    @JSONField(name = "bank_telCode")
    private String bankTelCode;
    /**
     * 状态码，返回10000状态都会进行计费
     * 10000	返回成功
     * 10001	appid必须指定，可以我的应用里面查看
     * 10002	sign值必须指定，加密规则请前往帮助中心查看
     * 10003	sign值验证不通过，加密规则请前往帮助中心查看
     * 10004	时差不能超过10分钟，可以不传递这个参数，注意时间戳单位是秒
     * 10005	appid错误，请检查appid值,前往会员中心->我的应用查看或添加
     * 10006	当前IP地址未授权，请前往用户中心->我的应用添加ip{@info}
     * 10007	应用被禁用，请联系客服处理
     * 10008	应用内没有该接口，请到我的应用里面添加这个接口
     * 10009	api接口不存在
     * 10010	您没有添加该api接口
     * 10011	api已经到期
     * 10012	没有订购任何api,请前往购买后再操作
     * 10013	该接口已经暂停使用
     * 10014	未知的错误，可以联系客服处理
     * 10015	参数个数错误
     * 10019	{@info}
     * 10017	time必须是整型
     * 10018	次数不足
     * 10020	子接口不存在,可能已经被关闭
     * 10021	服务器发生错误
     * 10022	帐户余额不足，请充值！
     * 10023	订单提交成功，等待回调结果
     * 10024	调试模式数据
     * 10025	查无数据
     */
    private String codeid;
    /**
     * 请求状态说明
     */
    private String message;
    /**
     * 请求时传递的当前服务器时间戳
     */
    private String time;
    /**
     * 请求参数
     */
    @JsonIgnore
    private String req;
    /**
     * 返回数据
     */
    @JsonIgnore
    private String res;

}