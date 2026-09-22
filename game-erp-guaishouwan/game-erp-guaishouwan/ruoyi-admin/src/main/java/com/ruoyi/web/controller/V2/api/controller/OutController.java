package com.ruoyi.web.controller.V2.api.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.mapper.AccountInfoMapper;
import com.ruoyi.system.outto.core.LCConstant;
import com.ruoyi.system.outto.service.IOutApi;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/8 10:58
 */
@RestController
@RequestMapping("/out")
public class OutController {

    @Autowired
    private IGoodsInfoService goodsInfoService;

    @Autowired
    private IOutApi outApi;

    /**
     * 新增
     */
    @GetMapping(value = "/outAdd")
    public AjaxResult outAdd(@RequestParam Map<String,Object> params) {
        Long id = MapUtils.getLong(params, "id");
        Long configId = MapUtils.getLong(params, "configId");
        String interType = MapUtils.getString(params, "interType");
        if (Objects.equals(LCConstant.interEdit,interType)){
            if (Objects.isNull(id)){
                throw new GlobalException("id异常");
            }
            outApi.edit(id,configId,null);
        }
        if (Objects.equals(LCConstant.interAdd,interType)){
            outApi.add(id,configId);
        }
        if (Objects.equals(LCConstant.interDel,interType)){
            outApi.del(id,configId,1,null);
        }

        return AjaxResult.success();

    }
    /**
     * 删除
     */
    @GetMapping(value = "/outDel/{ids}/{config}")
    public AjaxResult outDel(@PathVariable Long id,@PathVariable Long config)
    {
        outApi.del(id,config,1, null);
        return AjaxResult.success();
    }

}
