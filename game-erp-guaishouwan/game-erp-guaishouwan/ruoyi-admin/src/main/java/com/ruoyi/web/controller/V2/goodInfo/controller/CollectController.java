package com.ruoyi.web.controller.V2.goodInfo.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.goodInfo.service.ICollectService;
import com.ruoyi.system.model.domain.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 收藏
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
@RestController
@RequestMapping("/account/collect")
public class CollectController extends BaseController
{
    @Autowired
    private ICollectService collectService;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Collect collect)
    {
        startPage();
        List<Collect> list = collectService.selectCollectList(collect);
        return getDataTable(list);
    }
    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/listAccount")
    public AjaxResult listAccount(@RequestParam Map<String,Object> params)
    {
//        List<Collect> list = collectService.selectCollectList(collect);
//        List<Long> collect1 = list.stream().map(c -> c.getAccountId()).collect(Collectors.toList());
//        GoodsInfo goodsInfo = new GoodsInfo();
//        if (CollectionUtils.isEmpty(collect1)){
//            collect1.add(0l);
//            goodsInfo.setIds(collect1);
//        }else {
//            goodsInfo.setIds(collect1);
//        }
//
//        List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectGoodsInfoList(goodsInfo);
//
////        ;
        return AjaxResult.success(collectService.selectCollectDtoList(params));
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @GetMapping("/export")
    public AjaxResult export(Collect collect)
    {
        List<Collect> list = collectService.selectCollectList(collect);
        ExcelUtil<Collect> util = new ExcelUtil<Collect>(Collect.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(collectService.selectCollectById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @Log(title = "新增收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Collect collect)
    {
        return toAjax(collectService.insertCollect(collect));
    }

    /**
     * 修改【请填写功能名称】
     */
    @Log(title = "编辑收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Collect collect)
    {
        return toAjax(collectService.updateCollect(collect));
    }

    /**
     * 删除【请填写功能名称】
     */
    @Log(title = "删除收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(collectService.deleteCollectByIds(ids));
    }

    @PostMapping(value = "/removeCollct")
    public AjaxResult removeCollct(@RequestBody Collect collect)
    {
        return toAjax(collectService.removeCollct(collect));
    }
}
