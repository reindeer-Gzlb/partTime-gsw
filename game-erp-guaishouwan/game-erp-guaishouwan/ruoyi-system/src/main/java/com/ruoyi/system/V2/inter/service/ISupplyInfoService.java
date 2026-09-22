package com.ruoyi.system.V2.inter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.model.domain.SupplyInfo;

import java.util.List;

public interface ISupplyInfoService extends IService<SupplyInfo> {

    /**
     * 根据userId获取供货商信息
     *
     * @return
     */
    List<SupplyInfo> getListForUserId(Long userId);

}
