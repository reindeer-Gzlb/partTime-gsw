package com.ruoyi.system.V2.inter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.V2.inter.mapper.SupplyInfoMapper;
import com.ruoyi.system.V2.inter.service.ISupplyInfoService;
import com.ruoyi.system.model.domain.SupplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyInfoServiceImpl extends ServiceImpl<SupplyInfoMapper, SupplyInfo> implements ISupplyInfoService {

    @Autowired
    private SupplyInfoMapper supplyInfoMapper;

    @Override
    public List<SupplyInfo> getListForUserId(Long userId) {
        return supplyInfoMapper.selectList(new LambdaQueryWrapper<SupplyInfo>().eq(SupplyInfo::getUserId, userId));
    }
}
