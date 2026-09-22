package com.ruoyi.system.outto.core;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/17 10:30
 */
public enum KJCoverGameType {
    //          {            "id": 107,            "title": "DNF"        },
    //          {            "id": 170,            "title": "火影忍者"        },
    //          {            "id": 206,            "title": "枪战王者"        },
    //          {            "id": 216,            "title": "英雄联盟手游"        },
    //          {            "id": 221,            "title": "金铲铲之战"        },
    //          {            "id": 53,            "title": "光遇"        },
    //          {            "id": 227,            "title": "天涯明月刀"        },
    //          {            "id": 211,            "title": "飞车手游"        },
    //          {            "id": 186,            "title": "飞车端游"        },
    //          {            "id": 136,            "title": "崩坏3"        },
    //          {            "id": 248,            "title": "300英雄"        },
    //          {            "id": 190,            "title": "哈利波特"        },
    //          {            "id": 175,            "title": "王牌竞速"        },
    //          {            "id": 106,            "title": "明日方舟"        },
    //          {            "id": 183,            "title": "决战平安京"        },
    //          {            "id": 179,            "title": "漫威对决"        },
    //          {            "id": 178,            "title": "超激斗梦境"        }    ]}

    wzry(1, "王者荣耀"),
    yxlmsy(216, "英雄联盟手游"),
    yxlm(10, "英雄联盟"),
    ys(50, "原神"),
    hpjy(42, "和平精英"),
    yjwj(47, "永劫无间"),
    cyhx(57, "穿越火线"),
    DNF(107, "DNF");

    private final Integer id;
    private final String type;

    KJCoverGameType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public static Integer getId(String type) {
        if (wzry.type.equals(type)){
            return wzry.id;
        }else if (yxlm.type.equals(type)){
            return yxlm.id;
        }else if (ys.type.equals(type)){
            return ys.id;
        }else if (cyhx.type.equals(type)){
            return cyhx.id;
        }else if (DNF.type.equals(type)) {
            return DNF.id;
        }else if (hpjy.type.equals(type)) {
            return hpjy.id;
        }else if (yjwj.type.equals(type)) {
            return yjwj.id;
        }else if (yxlmsy.type.equals(type)) {
            return yxlmsy.id;
        }
        return null;
    }

    public String getType() {
        return type;
    }



}
