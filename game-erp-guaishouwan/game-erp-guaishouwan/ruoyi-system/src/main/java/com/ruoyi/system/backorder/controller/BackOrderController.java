package com.ruoyi.system.backorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.tool.service.IBacklistService;
import com.ruoyi.system.backorder.domain.BackOrder;
import com.ruoyi.system.backorder.service.IBackOrderService;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.pay.domain.PayRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 回收订单表(back_order)表控制层
 *
 * @author zyy
 * @date 2023-12-02 13:17:27
 */
@RestController
@RequestMapping("/backOrder")
public class BackOrderController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBackOrderService backOrderService;
    @Autowired
    private IBacklistService backlistService;

    /**
     * 分页查询所有数据
     *
     * @param backOrder 查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    public TableDataInfo list(BackOrder backOrder) {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<BackOrder> queryWrapper = new LambdaQueryWrapper();
        if (StringUtils.equals("buy", backOrder.getDataType())) {
            queryWrapper.eq(BackOrder::getBuyerId, userId);
        } else if (StringUtils.equals("sell", backOrder.getDataType())) {
            queryWrapper.eq(BackOrder::getSellerId, userId);
        } else if (StringUtils.equals("guarant", backOrder.getDataType())) {
            queryWrapper.eq(BackOrder::getGuaranteeId, userId);
        } else if (StringUtils.equals("all", backOrder.getDataType())) {

        } else {
            throw new GlobalException("不支持的dataType查询");
        }
        queryWrapper.like(StringUtils.isNotEmpty(backOrder.getNo()), BackOrder::getNo, backOrder.getNo());
        queryWrapper.like(StringUtils.isNotEmpty(backOrder.getAccountNumber()), BackOrder::getAccountNumber,
                backOrder.getAccountNumber());
        queryWrapper.ge(StringUtils.isNotNull(backOrder.getCreateTimeStart()), BackOrder::getCreateTime,
                backOrder.getCreateTimeStart());
        queryWrapper.le(StringUtils.isNotNull(backOrder.getCreateTimeEnd()), BackOrder::getCreateTime,
                backOrder.getCreateTimeEnd());
        queryWrapper.eq(StringUtils.isNotNull(backOrder.getGameId()), BackOrder::getGameId, backOrder.getGameId());
        queryWrapper.eq(StringUtils.isNotNull(backOrder.getOrderStatus()), BackOrder::getOrderStatus,
                backOrder.getOrderStatus());
        queryWrapper.orderByDesc(BackOrder::getId);
        startPage();
        List<BackOrder> list = backOrderService.list(queryWrapper);
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
        return AjaxResult.success(backOrderService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param backlistReq 实体对象
     * @return 新增结果
     */
    @Log(title = "BackOrder", businessType = BusinessType.INSERT)
    @PostMapping(value = "/create")
    public AjaxResult create(@RequestBody Backlist backlistReq) {
        Backlist backlist = backlistService.selectBacklistById(backlistReq.getId());
        if (StringUtils.isNull(backlist)) {
            throw new GlobalException("未找到此回收记录");
        }
        backOrderService.createOrder(backlist);
        return toAjax(1);
    }


    /**
     * 修改数据
     *
     * @param backOrder 实体对象
     * @return 修改结果
     */
    @Log(title = "BackOrder", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@RequestBody BackOrder backOrder) {
        return toAjax(backOrderService.updateById(backOrder));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @Log(title = "BackOrder", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(backOrderService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 一键拉群
     *
     * @param backOrderReq
     * @return
     */
    @RepeatSubmit
    @PostMapping("/pullGroup")
    public AjaxResult pullGroup(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此订单");
        }
        backOrder.setCreateGroupUserId(backOrderReq.getCreateGroupUserId());
        return AjaxResult.success(backOrderService.pullGroup(backOrder));
    }

    /**
     * 取消群组
     *
     * @param backOrderReq
     * @return
     */
    @PostMapping("/cancelGroup")
    public AjaxResult cancelGroup(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此订单");
        }
        return AjaxResult.success(backOrderService.cancelGroup(backOrder));
    }

    /**
     * 恢复群组
     *
     * @param backOrderReq
     * @return
     */
    @PostMapping("/recoveryGroup")
    public AjaxResult recoveryGroup(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此订单");
        }
        return AjaxResult.success(backOrderService.recoveryGroup(backOrder));
    }

    /**
     * 获取订单的群组hxid
     *
     * @param backOrderReq
     * @return
     */
    @PostMapping("/getToGroup")
    public AjaxResult getToGroup(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此订单");
        }
        return AjaxResult.success(backOrderService.getToGroup(backOrder));
    }

    /**
     * 支付方法
     *
     * @param payRecordReq
     * @return
     */
    @RepeatSubmit
    @PostMapping("/payment")
    public AjaxResult payment(@RequestBody PayRecord payRecordReq) {
        BackOrder backOrder = backOrderService.getById(payRecordReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此回收记录");
        }
        if (StringUtils.isNull(payRecordReq.getPayWay())) {
            throw new GlobalException("参数payWay不能为空");
        }
        if (StringUtils.isNull(payRecordReq.getPayScene())) {
            throw new GlobalException("参数payScene不能为空");
        }
        return AjaxResult.success(backOrderService.payment(backOrder, payRecordReq));
    }

    /**
     * 查询订单
     *
     * @param backOrderReq
     * @return
     */
    @RepeatSubmit(interval = 1000)
    @PostMapping("/queryOrder")
    public AjaxResult queryOrder(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此回收订单");
        }
        if (backOrder.getOrderStatus() == 1) {
            if (StringUtils.isNotNull(backOrder.getBuyerPayRecordId())) {
                backOrder = backOrderService.queryOrderById(backOrder);
            }
        }
        return AjaxResult.success(backOrder);
    }

    /**
     * 修改售价
     *
     * @param backOrderReq
     * @return
     */
    @PostMapping(value = "/changeAmount")
    public AjaxResult changeAmount(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此回收订单");
        }
        backOrder.setChangeAmount(backOrderReq.getChangeAmount());
        backOrderService.changeAmount(backOrder);
        return AjaxResult.success();
    }

    /**
     * 获取订单买家支付状态
     *
     * @param backOrderId
     * @return
     */
    //    @RepeatSubmit(interval = 2000)
    @GetMapping("/buyerPayStatus")
    public AjaxResult buyerPayStatus(String backOrderId) {
        if (StringUtils.isEmpty(backOrderId)) {
            throw new GlobalException("backOrderId不能为空");
        }
        LambdaQueryWrapper<BackOrder> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.select(BackOrder::getBuyerPayStatus);
        queryWrapper.eq(BackOrder::getId, backOrderId);
        Map<String, Object> map = backOrderService.getMap(queryWrapper);
        return AjaxResult.success(map);
    }

    /**
     * 包赔资料审核通过
     *
     * @param backOrderReq
     * @return
     */
    @PostMapping(value = "/audit/sucess")
    public AjaxResult auditSucess(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此回收订单");
        }
        backOrder.setOrderStatus(3);
        backOrderService.updateById(backOrder);
        return AjaxResult.success();
    }

    /**
     * 完成订单
     *
     * @param backOrderReq
     * @return
     */
    @RepeatSubmit(interval = 2000)
    @PostMapping(value = "/finishOrder")
    public AjaxResult finishOrder(@RequestBody BackOrder backOrderReq) {
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此回收订单");
        }
        backOrder.setRemark(backOrderReq.getRemark());
        backOrderService.finishOrder(backOrder);
//        push(backOrder);
        return toAjax(1);
    }

    /**
     * 取消订单
     *
     * @param backOrderReq
     * @return
     */
    @RepeatSubmit(interval = 2000)
    @PostMapping(value = "/cancelOrder")
    public AjaxResult cancelOrder(@RequestBody BackOrder backOrderReq) {
        if (StringUtils.isNull(backOrderReq.getIsReturn())) {
            throw new GlobalException("是否退还押金必选");
        }
        BackOrder backOrder = backOrderService.getById(backOrderReq.getId());
        if (StringUtils.isNull(backOrder)) {
            throw new GlobalException("未找到此回收订单");
        }
        backOrder.setRemark(backOrderReq.getRemark());
        backOrder.setIsReturn(backOrderReq.getIsReturn());
        backOrderService.cancelOrder(backOrder);
//        push(backOrder);
        return toAjax(1);
    }

}

