package com.ruoyi.system.outto.dto.yx7881.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class YsInfo {
    //"id":"324240",
    //            "keyname":"账号类型",
    //            "keyid":"accountType",
    //            "type":"select",
    //            "value":"手机账号,QQ邮箱账号,网易邮箱账号,其他邮箱账号",
    //            "must":true,
    String accountType;
    //"id":"324101",
    //            "keyname":"游戏账号",
    //            "keyid":"gameaccount",
    //            "type":"text",
    //            "value":null,
    //            "must":true,
    String gameaccount;
    // "id":"324102",
    //            "keyname":"游戏密码",
    //            "keyid":"gamepassword",
    //            "type":"password",
    //            "value":null,
    //            "must":true,
    String gamepassword;
    //"id":"324103",
    //            "keyname":"确认游戏密码",
    //            "keyid":"gamepasswordqr",
    //            "type":"password",
    //            "value":null,
    //            "must":true,
    String gamepasswordqr;
    //"id":"330498",
    //            "keyname":"UID",
    //            "keyid":"gameuid",
    //            "type":"text",
    //            "value":null,
    //            "must":true,
    String gameuid;
    //"id":"324284",
    //            "keyname":"绑定的邮箱",
    //            "keyid":"emailadress",
    //            "type":"text",
    //            "value":null,
    //            "must":false,
    String emailadress;
    //"id":"324285",
    //            "keyname":"邮箱密码",
    //            "keyid":"emailpwd",
    //            "type":"text",
    //            "value":null,
    //            "must":false,
    String emailpwd;
    //"id":"324279",
    //            "keyname":"是否出售邮箱",
    //            "keyid":"csemail",
    //            "type":"select",
    //            "value":"未绑定邮箱,带邮箱出售,不出售邮箱",
    //            "must":true,
    String csemail;
    //"id":"333096",
    //            "keyname":"绑定账号类型",
    //            "keyid":"bindAccountType",
    //            "type":"select",
    //            "value":"绑定网易邮箱（已实名）,绑定网易邮箱（未实名）,绑定QQ邮箱（已实名）,绑定QQ邮箱（未实名）,绑定其他邮箱",
    //            "must":false,
    String bindAccountType;
    // "id":"332796",
    //            "keyname":"是否绑定Tap",
    //            "keyid":"Tap",
    //            "type":"select",
    //            "value":"未绑定Tap,已绑定Tap",
    //            "must":true,
    @JSONField(name = "Tap")
    String Tap;
    //"id":"332797",
    //            "keyname":"是否绑定Psn",
    //            "keyid":"Psn",
    //            "type":"select",
    //            "value":"未绑定Psn,已绑定Psn",
    //            "must":true,
    @JSONField(name = "Psn")
    String Psn;
    //"id":"324241",
    //            "keyname":"五星角色",
    //            "keyid":"fiverole",
    //            "type":"checkbox",
    //            "value":"无,夜兰,刻晴,迪卢克,七七,莫娜,琴,温迪,可莉,阿贝多,钟离,达达利亚,魈,胡桃,甘雨,优菈,枫原万叶,神里绫华,宵宫,雷电将军,珊瑚宫心海,荒泷一斗,申鹤,八重神子,
    //            神里绫人,提纳里,赛诺,妮露,纳西妲,流浪者,艾尔海森,迪希雅",
    //            "must":true,
    //有子垂直属性
    String fiverole;
    // "id":"324291",
    //            "keyname":"五星武器",
    //            "keyid":"fivewp",
    //            "type":"checkbox",
    //            "value":"无,若水,斫峰之刃,尘世之锁,无工之剑,贯虹之槊,风鹰剑,四风原典,和璞鸢,狼的末路,阿莫斯之弓,天空之刃,天空之傲,天空之脊,天空之卷,天空之翼,磐岩结绿,护摩之杖,
    //            终末嗟叹之诗,松籁响起之时,苍古自由之誓,雾切之回光,飞雷之弦振,薙草之稻光,不灭月华,息灾,神乐之真意,波乱月白经津,冬极白星,赤角石溃杵,猎人之径,赤沙之杖,圣显之钥,千夜浮梦,
    //            图莱杜拉的回忆,裁叶萃光,苇海信标",
    //            "must":true,
    //有子垂直属性
    String fivewp;
    //"id":"324309",
    //            "keyname":"等级",
    //            "keyid":"level",
    //            "type":"text",
    //            "value":null,
    //            "must":true,
    String level;
    // "id":"326296",
    //            "keyname":"金卡数量",
    //            "keyid":"goldCardNum",
    //            "type":"readonly",
    //            "value":null,
    //            "must":true,
    String goldCardNum;

}
