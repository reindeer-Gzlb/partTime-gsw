package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;

import com.ruoyi.system.model.domain.PriceRec;
import com.ruoyi.system.V2.inter.mapper.PriceRecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 特殊渠道推送价格
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/account/prcie")
public class PriceRecController extends BaseController
{
    @Autowired
    private PriceRecMapper priceRecMapper;

    /**
     */
    @GetMapping("/list")
    public AjaxResult list(PriceRec priceRec)
    {
        List<PriceRec> list = priceRecMapper.selectPriceRecList(priceRec);
        return AjaxResult.success(list);
    }
}
