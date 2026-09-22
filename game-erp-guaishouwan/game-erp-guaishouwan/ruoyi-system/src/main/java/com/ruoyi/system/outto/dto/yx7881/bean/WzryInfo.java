package com.ruoyi.system.outto.dto.yx7881.bean;

import lombok.Data;

@Data
public class WzryInfo {

    //安卓父垂直属性21个
    //实体22个

    //账号来源  "value":"我是号主,我买的号",
    //        "must":true,
    String accountSource;
    //        "id":"323114",
    //        "keyname":"成人号",
    //        "keyid":"antiaddiction",
    //        "type":"select",
    //        "value":"是,否",
    //        "must":true,
    String antiaddiction;
    //"id":"330296",
    //        "keyname":"能否二次",
    //        "keyid":"canSecond",
    //        "type":"select",
    //        "value":"可二次,不可二次",
    //        "must":true,
    String canSecond;
    //"id":"94223",
    //        "keyname":"段位",
    //        "keyid":"select",
    //        "type":"select",
    //        "value":"倔强青铜,秩序白银,荣耀黄金,尊贵铂金,永恒钻石,至尊星耀,最强王者,荣耀王者,无双王者",
    //        "must":true,
    String select;
    // "id":"297293",
    //        "keyname":"贵族等级",
    //        "keyid":"nobility",
    //        "type":"select",
    //        "value":"无,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10",
    //        "must":true,
    String nobility;
    //"id":"42280",
    //        "keyname":"游戏账号",
    //        "keyid":"gameaccount",
    //        "type":"text",
    //        "value":null,
    //        "must":true,
    String gameaccount;
    // "id":"42281",
    //        "keyname":"游戏密码",
    //        "keyid":"gamepassword",
    //        "type":"password",
    //        "value":null,
    //        "must":true,
    String gamepassword;
    //"id":"323017",
    //        "keyname":"荣耀典藏",
    //        "keyid":"honrDC",
    //        "type":"checkbox",
    //        "value":"无,赤影疾锋,九霄神辉,星域神启,鸣剑·曳影,倪克斯神谕,活力突击,无限飓风号,全息碎影,星空梦想,天鹅之梦,大秦宣太后,杀手不太冷,武则天,银.白咏叹调,幻阙歌",
    //        "must":true,
    String honrDC;
    //"id":"323122",
    //        "keyname":"星传说",
    //        "keyid":"xySkin",
    //        "type":"checkbox",
    //        "value":"无,星级治安官,时之思念,金色仲夏夜,幽冥火,幽冥之眼,幻海之心,未来机甲,零号·雷霆,时之愿境,时之羁绊,无限倾慕",
    //        "must":true,
    String xySkin;
    //"id":"323123",
    //        "keyname":"传说皮肤",
    //        "keyid":"csSkin",
    //        "type":"checkbox",
    //        "value":"无,枫霜尽,前尘镜,音你闪耀,深海之息,无尽星芒,无双飞将,挚爱花嫁,挚爱之约,启示之音,花朝如约,补天,异界灵契,绛天战甲,炽阳神光,启明星使,胖达荣荣,地狱之眼,地狱火,海洋之心,末日机甲,仲夏夜之梦,云霓雀翎,久胜战神,黄金狮子座,超时空战士,黄金射手座,黄金白羊座,猫影幻舞,时之恋人(男),一念神魔,云鹰飞将,冠军飞将,飞鸢探春,音你心动(男),音你心动(女),李逍遥,游龙清影,乞巧织情,淬星耀世,时之祈愿女",
    //        "must":true,
    String csSkin;
    //"id":"323018",
    //        "keyname":"稀有限定",
    //        "keyid":"xiySkin",
    //        "type":"checkbox",
    //        "value":"无,寅虎·瑞焰,寅虎·赤拳,寅虎·御盾,寅虎·展翼,寅虎·心曲,祈雪灵祝,时雨天司,奔雷神使,御风骁将,白晶晶,零号·赤焰,至尊宝,梅西,瑞麟志,白虎志,青龙志,玄武志,朱雀志,凤求凰,武陵仙君,霸王别姬(女),真爱至上,大圣娶亲,一生所爱,天魔缭乱,引擎之心,纯白花嫁,太华,岱宗,玄嵩,幽恒,飞衡,奇迹圣诞,蜜橘之夏,神奇女侠,武圣,虎魄,神威,烈魂,龙胆,青春决赛季,默契交锋(男),白鹤梁神女,乒乒小将,遇见胡旋,孙行者,猪悟能,女儿国国王,傲雪梅枪,山海·碧波行,山海·苍雷引,山海·玄木吟,山海·炽霜斩,山海·琳琅生,齐天大圣,冰霜神祇,无限倾心,诗剑行",
    //        "must":true,
    String xiySkin;
    //"id":"323019",
    //        "keyname":"优质皮肤",
    //        "keyid":"goodSkin",
    //        "type":"checkbox",
    //        "value":"无,天狼运算者,白龙吟,逐梦之光,逐梦之影,逐梦之音,逐梦之翼,逐梦之星,如梦令(女),如梦令(男),凤凰于飞,冰霜恋舞曲,千年之狐,永曜之星,辉光之辰,美猴王,云端筑梦师,冰锋战神,魔法小厨娘,银河之约,遇见神鹿,修罗,无限星赏官,猫狗日记(男),猫狗日记(女),天狼绘梦者,天狼征服者,天狼狩猎者,高山流水,逆光追影者",
    //        "must":true,
    String goodSkin;
//    //"id":"323016",
//    //        "keyname":"限定英雄",
//    //        "keyid":"limitHero",
//    //        "type":"checkbox",
//    //        "value":"无,艾琳,嬴政,韩信,不知火舞,娜可露露,橘右京",
//    //        "must":true,
//    String limitHero;
    // "id":"1632468",
    //        "keyname":"小国标英雄",
    //        "keyid":"smalltagHero",
    //        "type":"checkbox",
    //        "value":"无",
    //        "must":false,
    String smalltagHero;
    //"id":"42282",
    //        "keyname":"确认输入密码",
    //        "keyid":"gamepassword2",
    //        "type":"password",
    //        "value":null,
    //        "must":true,
    String gamepassword2;
    //"id":"1632467",
    //        "keyname":"大国标英雄",
    //        "keyid":"bigtagHero",
    //        "type":"checkbox",
    //        "value":"无",
    //        "must":false,
    String bigtagHero;
    //"id":"94225",
    //        "keyname":"皮肤数量",
    //        "keyid":"skinCount",
    //        "type":"text",
    //        "value":null,
    //        "must":true,
    String skinCount;
    // "id":"94224",
    //        "keyname":"英雄数量",
    //        "keyid":"heroCount",
    //        "type":"text",
    //        "value":null,
    //        "must":true,
    String heroCount;
    // "id":"323503",
    //        "keyname":"王者营地ID",
    //        "keyid":"wzCampId",
    //        "type":"text",
    //        "value":null,
    //        "must":true,
    String wzCampId;
    // "id":"1632661",
    //        "keyname":"常用微信号",
    //        "keyid":"weixin",
    //        "type":"text",
    //        "value":null,
    //        "must":false,
    String weixin;
    //"id":"333696",
    //            "keyname":"赛季皮肤",
    //            "keyid":"code2",
    //            "type":"checkbox",
    //            "value":"无,狮心王,蜃楼王,妖精王,炼金王,凯尔特女王,狰,饕餮,烛龙,重明,穷奇,御狮,御霄,御旌,御銮,朔风刀,混沌棋,疑决卦,醍醐杖,原初探秘者,原初追逐者,万华元夜,演武夺魁,
    //            驱傩正仪,千军破阵,黎明之约,沙漠行僧,金庭之子,大漠名商,云中旅人,冰封战神,游园惊梦,遇见飞天,梁祝,李小龙,五谷丰登,猎兽之王,白昼王子,朔望之晖,蓝屏警告,赤莲之焰,驯魔猎人,神迹守卫,
    //            狮舞东方,星空之诺,夺宝奇兵,迷踪丽影,西部大镖客,暗影游猎,单次大作战,狂想玩偶猫,潮玩骑士王,云间偶戏,胡桃意想国,星级陆战队,航海奇遇记,圣弓游侠,真爱魔法,幻夜卜梦,火炮绅士,夜都怪侠,
    //            吟游魔法,午后时光,电玩高手,落雪兰心",
    //            "must":true,
    String code2;
    //"id":"333597",
    //            "keyname":"内测",
    //            "keyid":"code1",
    //            "type":"checkbox",
    //            "value":"无,女武神,胡桃夹子,优雅恋人,蔷薇恋人,玩趣恶龙",
    //            "must":true,
    String code1;
}
