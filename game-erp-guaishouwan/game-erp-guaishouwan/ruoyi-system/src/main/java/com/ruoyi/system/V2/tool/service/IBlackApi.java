package com.ruoyi.system.V2.tool.service;

import com.ruoyi.system.model.domain.Black;
import com.ruoyi.system.model.domain.HBlack;

import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/25 8:09
 */
public interface IBlackApi {

    public List<Map> allGameType();

    public List<HBlack> allAcount(Map<String,Object> params);


    Black addBlack(Black black);
}
