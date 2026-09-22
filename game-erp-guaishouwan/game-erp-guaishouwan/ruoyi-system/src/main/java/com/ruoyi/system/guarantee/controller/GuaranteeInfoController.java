package com.ruoyi.system.guarantee.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.backorder.domain.BackOrder;
import com.ruoyi.system.backorder.service.IBackOrderService;
import com.ruoyi.system.guarantee.domain.GuaranteeInfo;
import com.ruoyi.system.guarantee.service.IGuaranteeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 包赔资料信息表(guarantee_info)表控制层
 *
 * @author zyy
 * @date 2023-12-05 18:41:00
 */
@RestController
@RequestMapping("/guaranteeInfo")
public class GuaranteeInfoController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IGuaranteeInfoService guaranteeInfoService;
    @Autowired
    private IBackOrderService backOrderService;

    /**
     * 分页查询所有数据
     *
     * @param guaranteeInfo 查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    public TableDataInfo list(GuaranteeInfo guaranteeInfo) {
        startPage();
        List<GuaranteeInfo> list = guaranteeInfoService.list();
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(guaranteeInfoService.getById(id));
    }

    /**
     * 通过回收订单id查看包赔信息
     *
     * @param guaranteeInfoReq
     * @return 单条数据
     */
    @GetMapping(value = "/getByBackOrderId")
    public AjaxResult getByBackOrderId(GuaranteeInfo guaranteeInfoReq) {
        LambdaQueryWrapper<GuaranteeInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GuaranteeInfo::getBackOrderId, guaranteeInfoReq.getBackOrderId());
        GuaranteeInfo guaranteeInfo = guaranteeInfoService.getOne(queryWrapper);
        return AjaxResult.success(guaranteeInfo);
    }

    /**
     * 新增数据
     *
     * @param guaranteeInfo 实体对象
     * @return 新增结果
     */
    @Log(title = "GuaranteeInfo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult insert(@RequestBody GuaranteeInfo guaranteeInfo) {
        BackOrder backOrder = backOrderService.getById(guaranteeInfo.getBackOrderId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此回收订单");
        }
        guaranteeInfo.setBacklistId(backOrder.getBacklistId());
        guaranteeInfo.setGuaranteeId(backOrder.getGuaranteeId());
        guaranteeInfo.setGuaranteeName(backOrder.getGuaranteeName());
        guaranteeInfo.setIp(backOrder.getIp());
        guaranteeInfo.setIpAddr(backOrder.getIpAddr());
        return toAjax(guaranteeInfoService.save(guaranteeInfo));
    }

    /**
     * 修改数据
     *
     * @param guaranteeInfo 实体对象
     * @return 修改结果
     */
    @Log(title = "GuaranteeInfo", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@RequestBody GuaranteeInfo guaranteeInfo) {
        return toAjax(guaranteeInfoService.updateById(guaranteeInfo));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @Log(title = "GuaranteeInfo", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(guaranteeInfoService.removeByIds(Arrays.asList(ids)));
    }
}

