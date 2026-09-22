package com.ruoyi.system.model.dto;

import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.vo.SelectRetail;
import lombok.Data;

import java.util.List;

@Data
public class GoodsInfoAndAttrValueReq extends GoodsInfo {
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

    private String priceStart;
    private String priceEnd;
    private String sort;
    private String sortColumn;
    private String keyWord;
    private List<String> keyWordArr;
    private String dataType;

    /**
     * pricedesc  价格降序
     * priceasc  价格升序
     * timeasc  时间升序
     * timedesc  时间降序
     */
    private String sortRank;
    private Long retailId;

    /**
     * 选择的分销
     */
    private List<SelectRetail> selectRetails;

    private Integer pageNum;
    private Integer pageSize;

    private List boxRadioValues;

    private String[] goodsTypes;

    private String branchRoadValueStart;
    private String branchRoadValueEnd;

}
