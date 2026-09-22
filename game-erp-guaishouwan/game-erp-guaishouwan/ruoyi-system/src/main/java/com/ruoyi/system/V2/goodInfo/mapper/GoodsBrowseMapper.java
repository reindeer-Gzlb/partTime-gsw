package com.ruoyi.system.V2.goodInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.model.domain.GoodsBrowse;
import com.ruoyi.system.model.dto.GoodsBrowseRes;

import java.util.List;

public interface GoodsBrowseMapper extends BaseMapper<GoodsBrowse> {

    List<GoodsBrowseRes> myBrowse(Long userId);

}
