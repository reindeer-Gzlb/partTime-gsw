package com.ruoyi.system.outto.dto.yx7881.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class YX7881GoodsBean {
    String gameId; //是 游戏id
    String title;//否 商品标题
    String titleSupplement;//否 商品副标题
    String carrierId;//否 运营商id（手游必传）
    String carriername;//否 运营商名称（手游必传）
    String groupId;//否 区Id
    String serverId;//否 服Id
    String gtId;//否 物品类型（游戏币100001，账号100003,矛盾的结晶体100166）默认值100001
    BigDecimal perNum;//是 单份商品数量（账号类默认值为1）
    String phone;//否 卖家电话,担保商品时必填
    String price;//是 商品价格(元)
    Short deposit;//否 选,是否是无货赔付,如果为无货赔付且未开通赔付金服务则扣除相应的(5倍)保证金,可选值:0(不启用)、1(启用),默认:0
    String tradeCode;//否 交易安全码(可以为空?)
    Long stock;//是 库存数量,账号、装备类商品库存限制为1
    String qq;//否 担保商品时必填
    String sellerType;//否 卖家类型(‘C’,’B’)
    String userId;//是 卖家id
    Integer effective;//否 商品有效期(天数,默认0表示永久有效)
    String tradeTime;//否 可交易时间,默认:00:00-24:00
    String tradePlace;//否 交易方式1:邮寄2:拍卖 3:当面，默认值1
    Object propertyList;//是 商品父垂直属性
    Object childPropertyList;//否 商品子垂直属性
    String tradeType;//否 交易类型：db(担保)，js(寄售) 账号类型只支持js,其他类目只支持db
    String[] images;//否 商品截图(如果内容为base64则为生成7881图片链接地址，否则使用传入的图片地址)
    String description;//否 商品描述
    String thirdGoodsId;//否 第三方商品id

}
