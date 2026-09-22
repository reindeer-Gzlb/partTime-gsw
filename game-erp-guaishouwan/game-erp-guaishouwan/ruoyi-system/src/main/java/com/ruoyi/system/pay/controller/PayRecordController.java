package com.ruoyi.system.pay.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.model.domain.OrderRec;
import com.ruoyi.system.pay.domain.PayRecord;
import com.ruoyi.system.pay.service.IPayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 移动支付记录表(pay_record)表控制层
 *
 * @author zyy
 * @date 2023-09-15 16:23:58
 */
@RestController
@RequestMapping("/pay/record")
public class PayRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IPayRecordService payRecordService;
    @Autowired
    private IOrderRecService orderRecService;

    /**
     * 分页查询所有数据
     *
     * @param payRecord 查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    public TableDataInfo list(PayRecord payRecord) {
        startPage();
        LambdaQueryWrapper<PayRecord> queryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotNull(payRecord.getPayWay())) {
            queryWrapper.eq(PayRecord::getPayWay, payRecord.getPayWay());
        }
        if (StringUtils.isNotNull(payRecord.getOrderState())) {
            queryWrapper.eq(PayRecord::getOrderState, payRecord.getOrderState());
        }
        if (StringUtils.isNotEmpty(payRecord.getNo())) {
            queryWrapper.eq(PayRecord::getNo, payRecord.getNo());
        }
        if (StringUtils.isNotEmpty(payRecord.getPayNo())) {
            queryWrapper.eq(PayRecord::getPayNo, payRecord.getPayNo());
        }
        if (StringUtils.isNotNull(payRecord.getGoodsId())) {
            queryWrapper.eq(PayRecord::getGoodsId, payRecord.getGoodsId());
        }
        if (StringUtils.isNotNull(payRecord.getPayEndTimeStart())) {
            queryWrapper.ge(PayRecord::getPayEndTime, payRecord.getPayEndTimeStart());
        }
        if (StringUtils.isNotNull(payRecord.getPayEndTimeEnd())) {
            queryWrapper.le(PayRecord::getPayEndTime, payRecord.getPayEndTimeEnd());
        }
        queryWrapper.orderByDesc(PayRecord::getId);
        List<PayRecord> list = payRecordService.list(queryWrapper);
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
        return AjaxResult.success(payRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param payRecord 实体对象
     * @return 新增结果
     */
//    @Log(title = "PayRecord", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping("/order")
    public AjaxResult insert(@Validated @RequestBody PayRecord payRecord) {
        return AjaxResult.success(payRecordService.savePayRecord(payRecord));
    }

    /**
     * 立即支付
     */
//    @Log(title = "PayRecord", businessType = BusinessType.INSERT)
//    @PostMapping("/payment")
//    public AjaxResult payment(@RequestBody PayRecord payRecordReq) {
//        PayRecord payRecord = payRecordService.getById(payRecordReq.getId());
//        if (StringUtils.isNull(payRecord)) {
//            throw new GlobalException("未找到此订单");
//        }
//        if (StringUtils.isNull(payRecordReq.getPayWay())) {
//            throw new GlobalException("参数payWay不能为空");
//        }
//        if (StringUtils.isNull(payRecordReq.getPayScene())) {
//            throw new GlobalException("参数payScene不能为空");
//        }
//        payRecord.setPayWay(payRecordReq.getPayWay());
//        payRecord.setPayScene(payRecordReq.getPayScene());
//        payRecord.setReturnUrl(payRecordReq.getReturnUrl());
//        payRecord.setAmountType(payRecordReq.getAmountType());
//        payRecord.setGoodsTitle(payRecordReq.getGoodsTitle());
//        return AjaxResult.success(payRecordService.payment(payRecord));
//    }

    /**
     * 立即支付（业务订单维度，支持定金 / 尾款分笔）
     * <p>
     * 请求体 PayRecord 中的字段说明：
     * <ul>
     *   <li>{@code id}            必填，业务订单主键，用于加载 {@link OrderRec}</li>
     *   <li>{@code payWay}        必填，支付方式（与支付中心约定）</li>
     *   <li>{@code payScene}      必填，支付场景</li>
     *   <li>{@code amountType}    可选，0=全额 / 1=定金 / 2=尾款，不传则按全额处理</li>
     *   <li>{@code returnUrl}     可选，支付完成后前端跳转地址</li>
     *   <li>{@code notifyUrl}     可选，支付中心异步回调地址，不传则取字典默认配置</li>
     *   <li>{@code goodsTitle}    可选，商品标题，不传则默认"购买商品"</li>
     * </ul>
     * 成功返回已落库的支付记录，含支付中心单号 {@code centerNo} 和唤起支付的表单串 {@code formStr}。
     * 接口通过 {@link RepeatSubmit#interval()} 做防重复提交（2 秒内同一请求会被拦截）。
     */
    @Log
    @RepeatSubmit(interval = 2000)
    @PostMapping("/paymentTwo")
    public AjaxResult paymentTwo(@RequestBody PayRecord payRecordReq) {
        // 1) 根据请求中的 id 加载业务订单，订单不存在直接抛错
        OrderRec orderRec = orderRecService.selectOrderRecById(payRecordReq.getId());
        if (StringUtils.isNull(orderRec)) {
            throw new GlobalException("未找到此订单");
        }
        // 2) 参数校验：支付方式、支付场景为必填项
        if (StringUtils.isNull(payRecordReq.getPayWay())) {
            throw new GlobalException("参数payWay不能为空");
        }
        if (StringUtils.isNull(payRecordReq.getPayScene())) {
            throw new GlobalException("参数payScene不能为空");
        }
        // 3) 仅把前端关心的字段透传给 service 层，避免污染 PayRecord 上的业务字段（订单状态、金额等由 service 计算）
        PayRecord payRecord = new PayRecord();
        payRecord.setPayWay(payRecordReq.getPayWay());
        payRecord.setPayScene(payRecordReq.getPayScene());
        payRecord.setReturnUrl(payRecordReq.getReturnUrl());
        payRecord.setAmountType(payRecordReq.getAmountType());
        payRecord.setGoodsTitle(payRecordReq.getGoodsTitle());
        // 4) 交给 service 层做：金额计算（定金/尾款）、去重、调支付中心统一下单、回填订单等
        return AjaxResult.success(payRecordService.paymentTwo(orderRec, payRecord));
    }

    /**
     * 修改数据
     *
     * @param payRecord 实体对象
     * @return 修改结果
     */
    @Log(title = "PayRecord", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@RequestBody PayRecord payRecord) {
        return toAjax(payRecordService.updateById(payRecord));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @Log(title = "PayRecord", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(payRecordService.removeByIds(Arrays.asList(ids)));
    }

    @PostMapping("/queryOrder")
    public AjaxResult queryOrder(@RequestBody PayRecord payRecordReq) {
//        PayRecord payRecord = payRecordService.getById(payRecordReq.getId());
//        if (StringUtils.isNull(payRecord)) {
//            throw new GlobalException("未找到此订单");
//        }
//        if (payRecord.getOrderState() == 1) {
//            payRecord = payRecordService.queryOrder(payRecord);
//        }
        return AjaxResult.success("此方法未被使用");
    }

}

