package com.ruoyi.web.controller.V2.submitAccount;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfoang;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.Finance;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.OrderRec;
import com.ruoyi.system.pay.service.IPayRecordService;
import com.ruoyi.system.push.service.IPushService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 销售订单记录Controller
 *
 * @author ruoyi
 * @date 2022-10-28
 */
@RestController
@RequestMapping("/account/order/rec")
public class OrderRecController extends BaseController {
    @Autowired
    private IOrderRecService orderRecService;
    @Autowired
    private OrderRecMapper orderRecMapper;
    @Autowired
    private IPayRecordService payRecordService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private IPushService pushService;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    /**
     * 查询销售订单记录列表
     */
    @GetMapping("/list")
    public TableDataInfoang list(OrderRec orderRec) {
        startPage();
        List<OrderRec> list = orderRecService.selectOrderRecList(orderRec);
        return getDataTableRang(list, orderRec);
    }


    @GetMapping("/createList")
    public AjaxResult createList() {
        List<Map<String, Object>> list = orderRecService.createList();
        return AjaxResult.success(list);
    }

    private TableDataInfoang getDataTableRang(List<OrderRec> list, OrderRec orderRec) {

        TableDataInfoang rspData = new TableDataInfoang();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());

        Map<String, Object> result = orderRecService.sumPrice(orderRec);
        rspData.setParams(result);
        return rspData;
    }

    /**
     * 导出销售订单记录列表
     */
    @Log(title = "销售订单记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OrderRec orderRec) {
        List<OrderRec> list = orderRecService.selectOrderRecList(orderRec);
        ExcelUtil<OrderRec> util = new ExcelUtil<OrderRec>(OrderRec.class);
        return util.exportExcel(list, "销售订单记录数据");
    }

    /**
     * 获取销售订单记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(orderRecService.selectOrderRecById(id));
    }

    /**
     * 新增销售订单记录
     */
    @Log(title = "销售订单记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderRec orderRec) {
        return toAjax(orderRecService.insertOrderRec(orderRec));
    }

    /**
     * 新增销售订单记录
     */
    @Log(title = "销售订单记录", businessType = BusinessType.INSERT)
    @PostMapping(value = "/addTwo")
    public AjaxResult addTwo(@RequestBody OrderRec orderRec) {
        return toAjax(orderRecService.insertOrderRecTwo(orderRec));
    }

    /**
     * 转自售订单记录同步财务
     */
    @Log(title = "转自售订单记录同步财务", businessType = BusinessType.INSERT)
    @PostMapping(value = "/selfSale")
    public AjaxResult selfSale(@RequestBody Finance finance) {
        return toAjax(orderRecService.selfSale(finance));
    }

    /**
     * 转自售订单记录同步财务
     */
    @Log(title = "转自售订单记录同步财务", businessType = BusinessType.INSERT)
    @PostMapping(value = "/selfSaleFQ")
    public AjaxResult selfSaleFQ(@RequestBody Finance finance) {
        return toAjax(orderRecService.selfSaleFQ(finance));
    }

    /**
     * 修改销售订单记录
     */
    @Log(title = "销售订单记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderRec orderRec) {
        return toAjax(orderRecService.updateOrderRec(orderRec));
    }

    /**
     * 删除销售订单记录
     */
    @Log(title = "销售订单记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderRecService.deleteOrderRecByIds(ids));
    }

    @RepeatSubmit(interval = 2000)
    @PostMapping(value = "/finishOrder")
    public AjaxResult finishOrder(@RequestBody OrderRec orderRecReq) {
        OrderRec orderRec = orderRecService.selectOrderRecById(orderRecReq.getId());
        if (StringUtils.isNull(orderRec)) {
            throw new GlobalException("未找到此订单");
        }
        orderRecReq.setId(orderRec.getId());
        orderRecReq.setFinishTime(DateUtils.getNowDate());
        orderRecService.finishOrder(orderRecReq);
        push(orderRecReq);
        return toAjax(1);
    }

    @RepeatSubmit(interval = 2000)
    @PostMapping(value = "/tihao")
    public AjaxResult tihao(@RequestBody OrderRec orderRec) {
        return toAjax(orderRecService.tihao(orderRec));
    }

    @GetMapping(value = "/byGoodsId")
    public AjaxResult byGoodsId(Long goodsId) {
        OrderRec orderRec = new OrderRec();
        orderRec.setAccountid(String.valueOf(goodsId));
        orderRec.setOderstatus("3");
        List<OrderRec> orderRecs = orderRecMapper.selectOrderRecList(orderRec);
        OrderRec orderRecRes = new OrderRec();
        if (CollectionUtils.isNotEmpty(orderRecs)) {
            orderRecRes = orderRecs.get(0);
        }
        return AjaxResult.success(orderRecRes);
    }

    @RepeatSubmit(interval = 2000)
    @PostMapping(value = "/afterSale")
    public AjaxResult afterSale(@RequestBody OrderRec orderRecReq) {
        if (StringUtils.isNull(orderRecReq.getRefundType())) {
            throw new GlobalException("退款类型不能为空");
        }
        OrderRec orderRec = orderRecService.selectOrderRecById(orderRecReq.getId());
        if (StringUtils.isNull(orderRec)) {
            throw new GlobalException("未找到此订单");
        }

        orderRec.setRefundAmount(orderRecReq.getRefundAmount());
        orderRec.setAfterSaleId(orderRecReq.getAfterSaleId());
        orderRec.setAfterSaleValue(orderRecReq.getAfterSaleValue());
        orderRec.setAfterSaleRemark(orderRecReq.getAfterSaleRemark());
        orderRec.setRefundType(orderRecReq.getRefundType());
        return toAjax(orderRecService.afterSale(orderRec));
    }


    @RepeatSubmit
    @PostMapping("/queryOrder")
    public AjaxResult queryOrder(@RequestBody OrderRec orderRecReq) {
        OrderRec orderRec = orderRecService.selectOrderRecById(orderRecReq.getId());
        if (StringUtils.isNull(orderRec)) {
            throw new GlobalException("未找到此订单");
        }
        if (StringUtils.equals(orderRec.getOderstatus(), "3") || StringUtils.isNotEmpty(orderRecReq.getForce())) {
            payRecordService.queryOrderByOrderId(orderRec);
        }
        return AjaxResult.success(orderRec);
    }

    @Log
    @RepeatSubmit
    @PostMapping("/createOrder")
    public AjaxResult createOrder(@RequestBody OrderRec orderRec) {
        return AjaxResult.success(orderRecService.createOrder(orderRec));
    }

    /**
     * 获取订单状态
     *
     * @param id
     * @return
     */
    @GetMapping("/orderStatus")
    public AjaxResult orderStatus(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new GlobalException("id");
        }
        Map<String, Object> map = orderRecMapper.selectOrderStatus(Long.valueOf(id));
        return AjaxResult.success(map);
    }


    void push(OrderRec orderRec) {
        if (StringUtils.isNotEmpty(orderRec.getAccountid())) {
            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.valueOf(orderRec.getAccountid()));
            buyer(orderRec);
            supplyer(goodsInfo);
        }
    }

    /**
     * 订单交易完成,通知买家
     *
     * @param orderRec
     */
    void buyer(OrderRec orderRec) {
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
        if (StringUtils.isNotNull(sysUser)) {
            String[] getuiAlias = new String[]{sysUser.getUserName()};
            String title = "【订单消息】";
            String conten = "【您购买编号为" + orderRec.getAccountid() + "的商品交易成功，点击查看详情】";
            JSONObject payload = new JSONObject();
            payload.put("type", "1001");
            Integer pushType = 2;
            pushService.push(getuiAlias, title, conten, payload, pushType);
        }
    }

    /**
     * 订单交易完成,通知卖家
     *
     * @param goodsInfo
     */
    void supplyer(GoodsInfo goodsInfo) {
        SysUser sysUser = sysUserMapper.selectUserById(goodsInfo.getCreateId());
        if (StringUtils.isNotNull(sysUser)) {
            String[] getuiAlias = new String[]{sysUser.getUserName()};
            String title = "【订单消息】";
            String conten = "【您寄售编号为" + goodsInfo.getId() + "的商品已交易成功，点击查看详情】";
            JSONObject payload = new JSONObject();
            payload.put("type", "2003");
            Integer pushType = 2;
            pushService.push(getuiAlias, title, conten, payload, pushType);
        }
    }
}
