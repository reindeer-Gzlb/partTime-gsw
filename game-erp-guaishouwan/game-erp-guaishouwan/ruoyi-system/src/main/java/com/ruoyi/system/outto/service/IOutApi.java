package com.ruoyi.system.outto.service;

import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/8 9:37
 */
public interface IOutApi {


    public void add(Long id, Long config);
    public void addAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto, Long config);
    //type 1 保存入库记录  0 不保存入库记录
    public void del(Long id, Long config, int type, Long taskId);
    public void edit(Long id, Long config,Long taskId);
}
