package com.ruoyi.web.controller.V2.web.conntroller.needlogin;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsBrowseMapper;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.service.ISysMessageService;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.OrderRec;
import com.ruoyi.system.model.dto.OrderRecRes;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * web 我的收藏 功能
 *
 * @author zyy
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/need")
public class OrderWebH5Controller extends BaseController {

    @Autowired
    private IGoodsInfoService goodsInfoService;
    @Autowired
    private IGameAttributeService gameAttributeService;
    @Autowired
    private GoodsBrowseMapper goodsBrowseMapper;
    @Autowired
    private ISysMessageService sysMessageService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private OrderRecMapper orderRecMapper;
    @Autowired
    private IOrderRecService orderRecService;


    @GetMapping("/myOrder")
    public TableDataInfo myOrder(OrderRec orderRec) {
        Long userId = SecurityUtils.getUserId();
        OrderRec orderRecReq = new OrderRec();
        orderRecReq.setSalechanelid(userId);
        orderRecReq.setSaleTimeStart(orderRec.getSaleTimeStart());
        orderRecReq.setSaleTimeEnd(orderRec.getSaleTimeEnd());
        orderRecReq.setOderstatus(orderRec.getOderstatus());
        startPage();
        return getDataTable(orderRecMapper.myOrderRecList(orderRecReq));
    }

    @GetMapping("/myOrder/{id}")
    public AjaxResult myOrderById(@PathVariable("id") Long id) {
        return AjaxResult.success(orderRecMapper.myOrderRecOne(id));
    }

    @GetMapping("/myOrder/byHxId")
    public AjaxResult byHxId(String HxId) {
        SysUser sysUser = sysUserMapper.selectUserByHxUserName(HxId);
        OrderRecRes orderRecRes = null;
        if (Objects.nonNull(sysUser)) {
            OrderRec orderRec = new OrderRec();
            orderRec.setSalechanelid(sysUser.getUserId());
            orderRecRes = orderRecMapper.orderRecLimitOne(orderRec);
        }
        return AjaxResult.success(orderRecRes);
    }

    @GetMapping("/myOrder/byCenterNo")
    public AjaxResult myOrderByCenterNo(OrderRec orderRecReq) {
        if (StringUtils.isEmpty(orderRecReq.getCenterNo())) {
            throw new GlobalException("该订单的支付单号不能为空");
        }
        OrderRec orderRec = new OrderRec();
        orderRec.setNo(orderRecReq.getNo());
        orderRec.setCenterNo(orderRecReq.getCenterNo());
        List<OrderRecRes> orderRecResList = orderRecMapper.myOrderRecList(orderRecReq);
        OrderRecRes orderRecRes = null;
        if (CollectionUtils.isNotEmpty(orderRecResList)) {
            orderRecRes = new OrderRecRes();
            orderRecRes = orderRecResList.get(0);
        }
        return AjaxResult.success(orderRecRes);
    }


    @PostMapping("/myOrder/cancel")
    public AjaxResult myOrderCancel(@RequestBody OrderRec orderRecReq) {
        OrderRec orderRec = orderRecMapper.selectOrderRecById(orderRecReq.getId());
        if (StringUtils.isNull(orderRec)) {
            throw new GlobalException("未找到此订单");
        }
        if (StringUtils.equals(orderRec.getOderstatus(), "3") || StringUtils.isNotEmpty(orderRecReq.getForce())) {
            if (StringUtils.isNotEmpty(orderRec.getRemake())) {
                orderRec.setRemake(orderRec.getRemake() + "--" + orderRecReq.getRemake());
            } else {
                orderRec.setRemake(orderRecReq.getRemake());
            }
            orderRec.setChangeGoodsSaleState(1);
            orderRec.setUpdateId(SecurityUtils.getUserId());
            orderRecService.myOrderCancel(orderRec);
        }
        return toAjax(1);
    }

    @PostMapping("/myOrder/close")
    @Transactional
    public AjaxResult closeOrder(@RequestBody OrderRec orderRecReq) {
        return AjaxResult.success();
    }


}
