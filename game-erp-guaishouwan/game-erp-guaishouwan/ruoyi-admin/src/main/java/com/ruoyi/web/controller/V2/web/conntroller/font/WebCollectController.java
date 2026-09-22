package com.ruoyi.web.controller.V2.web.conntroller.font;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.goodInfo.service.ICollectService;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.service.IGoodsBrowseService;
import com.ruoyi.system.model.domain.Collect;
import com.ruoyi.system.model.domain.GoodsBrowse;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * web 我的收藏 功能
 *
 * @author zyy
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/web/goods/collect")
public class WebCollectController extends BaseController {
    @Autowired
    private ICollectService collectService;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private IGoodsInfoService goodsInfoService;

    @Resource
    private ISysUserService userService;

    @Autowired
    IGoodsBrowseService goodsBrowseService;


    /**
     * 查询 我的收藏 列表
     */
    @GetMapping("/list")
    public AjaxResult list(Collect collect) {
        startPage();
        List<Collect> list = collectService.selectCollectList(collect);
        List<Long> collect1 = list.stream().map(c -> c.getAccountId()).collect(Collectors.toList());
        GoodsInfo goodsInfo = new GoodsInfo();
        if (CollectionUtils.isEmpty(collect1)) {
            collect1.add(0l);
            goodsInfo.setIds(collect1);
        } else {
            goodsInfo.setIds(collect1);
        }

        List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectGoodsInfoList(goodsInfo);

        if(!CollectionUtils.isEmpty(goodsInfoList)){

            List<Long> goodsIds = goodsInfoList.stream().map(goods -> goods.getId()).collect(Collectors.toList());

            Map<Long, Integer> browsesMap = new HashMap<>();
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(goodsIds)) {
                List<GoodsBrowse> browses =
                        goodsBrowseService.list(new LambdaQueryWrapper<GoodsBrowse>().select(GoodsBrowse::getGoodsId,
                                GoodsBrowse::getCount).in(GoodsBrowse::getGoodsId, goodsIds).groupBy(GoodsBrowse::getGoodsId));
                browsesMap = browses.stream().collect(Collectors.toMap(GoodsBrowse::getGoodsId,
                        GoodsBrowse::getCount));
            }

            for (GoodsInfo info : goodsInfoList) {
                info.setBrowseNum(browsesMap.get(info.getId()));
            }

        }



        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        goodsInfoService.bacthAddPrice(goodsInfoList, supplyCustomer);
        return AjaxResult.success(goodsInfoList);
    }

//    /**
//     * 导出【请填写功能名称】列表
//     */
//    @GetMapping("/export")
//    public AjaxResult export(Collect collect) {
//        List<Collect> list = collectService.selectCollectList(collect);
//        ExcelUtil<Collect> util = new ExcelUtil<Collect>(Collect.class);
//        return util.exportExcel(list, "【请填写功能名称】数据");
//    }

//    /**
//     * 获取【请填写功能名称】详细信息
//     */
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(collectService.selectCollectById(id));
//    }

    /**
     * 新增商品到我的收藏
     */
    @Log(title = "新增收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Collect collect) {
        return toAjax(collectService.insertCollect(collect));
    }

//    /**
//     * 修改【请填写功能名称】
//     */
//    @Log(title = "编辑收藏", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody Collect collect) {
//        return toAjax(collectService.updateCollect(collect));
//    }


//    @Log(title = "删除收藏", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(collectService.deleteCollectByIds(ids));
//    }

    /**
     * 取消收藏商品
     */
    @PostMapping(value = "/remove")
    public AjaxResult removeCollct(@RequestBody Collect collect) {
        return toAjax(collectService.removeCollct(collect));
    }

    public static void main(String[] args) {

        String path = "http://back.cyxuanhaoba.com/api/account/undercarriage";


        List<String> list = new ArrayList<>();
        list.add("AJ13013");
        list.add("AJ13012");
        list.add("AJ13011");
        list.add("AJ13009");
        list.add("AJ5293");
        list.add("AJ5293");
        list.add("AJ5292");
        list.add("AJ5291");
        list.add("AJ5287");
        list.add("AJ5286");
        list.add("AJ5285");
        list.add("AJ5284");
        list.add("AJ5281");
        list.add("AJ5280");
        list.add("AJ5279");
        list.add("AJ5278");
        list.add("AJ5277");


        for (String s : list) {
            Map<String, String> params = new HashMap<>();
            params.put("orderNo", s);
            System.out.println(s);
            String s1 = HttpUtils.sendYDPost(path, JSONObject.toJSONString(params), "9BCE7D5947C738FA");
            System.out.println(s1);

            try {
                Thread.sleep(3000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
