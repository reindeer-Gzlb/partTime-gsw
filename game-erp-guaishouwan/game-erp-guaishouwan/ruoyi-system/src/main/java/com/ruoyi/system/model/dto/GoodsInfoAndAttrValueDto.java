package com.ruoyi.system.model.dto;

import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.SalePartner;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GoodsInfoAndAttrValueDto extends GoodsInfo {
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
    private String remark;
    private List<Map<String, Object>> urlArr;
    private String videoUrl;
    private String videoName;
    private String thirdId;
    private Long createId;
    private Long updateId;
    private int isCollect; //是否被当前人收藏 1代表已收藏

    private String imgUrls;
    /**
     * 接口配置
     */
    private SalePartner salePartner;

    private String outId;
}
