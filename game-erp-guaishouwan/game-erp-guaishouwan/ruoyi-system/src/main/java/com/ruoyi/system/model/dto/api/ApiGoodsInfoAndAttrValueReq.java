package com.ruoyi.system.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiGoodsInfoAndAttrValueReq {

//    private Long id;
    /**
     * 游戏id
     */
    @NotNull(message = "参数gameId不能为空")
    private Long gameId;
    /**
     * 游戏类型：0手游 1端游
     */
    @NotBlank(message = "参数gameType不能为空")
    private String gameType;
    /**
     * 操作系统id
     */
//    @NotNull(message = "参数systemId不能为空")
    private Long systemId;
    /**
     * 运营商id
     */
//    @NotNull(message = "参数carrierId不能为空")
    private Long carrierId;
    /**
     * 区id
     */
    @NotNull(message = "参数groupId不能为空")
    private Long groupId;
    /**
     * 服id
     */
    @NotNull(message = "参数serverId不能为空")
    private Long serverId;
    /**
     * 商品标题
     */
    @NotBlank(message = "参数title不能为空")
    @Size(min = 0, max = 200, message = "参数title长度不能超过200个字符")
    private String title;
    /**
     * 商品价格
     */
    @NotNull(message = "参数price不能为空")
    private BigDecimal price;
    /**
     * 商品描述
     */
    private String des;
    /**
     * 标题图片地址
     */
    @NotNull(message = "参数titleImg不能为空")
    private String titleImg;
    /**
     * 详情图片地址
     */
    @NotNull(message = "参数imgUrls不能为空")
    private String imgUrls;
    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * 视频名称
     */
    private String videoName;
    /**
     * 三方Id
     */
    @NotNull(message = "参数thirdId不能为空")
    private String thirdId;

    /**
     * 账号
     */
    @NotNull(message = "参数accountNumber不能为空")
    private String accountNumber;

    /**
     * 密码
     */
    private String accountPassword;

    /**
     * 密保手机
     */
    private String securityPhone;

    /**
     * 自定义属性
     */
    private String f1;
    private String f2;
    private String f3;
    private String f4;
    private String f5;
    private String f6;
    private String f7;
    private String f8;
    private String f9;
    private String f10;
    private String f11;
    private String f12;
    private String f13;
    private String f14;
    private String f15;
    private String f16;
    private String f17;
    private String f18;
    private String f19;
    private String f20;
}
