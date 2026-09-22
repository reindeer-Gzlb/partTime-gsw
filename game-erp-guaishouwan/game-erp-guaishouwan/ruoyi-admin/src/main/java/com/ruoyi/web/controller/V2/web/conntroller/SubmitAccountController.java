package com.ruoyi.web.controller.V2.web.conntroller;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.order.service.IAccountOrderService;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/submitAccount")
public class SubmitAccountController {

//    @Resource
//    private IAccountInfoService accountInfoService;
//    @Resource
//    private AccountInfoMapper accountInfoMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    @Resource
    private IGoodsInfoService goodsInfoService;

    @Resource
    private IAccountOrderService accountOrderService;
    @Resource
    private ISysUserService userService;


    /**
     * 账号详情
     */
    @ResponseBody
    @GetMapping("/accountInfo")
    public AjaxResult accountInfo(@RequestParam Map<String, Object> params) {
        Long id = MapUtils.getLong(params, "id");
        if (Objects.isNull(id)) {
            throw new GlobalException("ID为空");
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = goodsInfoMapper.selectGoodsInfoAndAttrValueById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未查到此账号，请核查编号");
        }
        goodsInfoService.sensitive(goodsInfo);
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        goodsInfoList.add(goodsInfo);
        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        goodsInfoService.bacthAddPrice(goodsInfoList, supplyCustomer);
        goodsInfoAndAttrValueDto.setPrice(goodsInfo.getPrice());
        goodsInfoAndAttrValueDto.setTitle(goodsInfo.getTitle());
        goodsInfoAndAttrValueDto.setDes(goodsInfo.getDes());
//        @Excel(name = "销售状态  0、待审核 1、未出售 2、出售中 3、已售 4、问题账号 5、找回账号 6、超时未售 7、自售账号")
//        String info = SalerStatus.getInfo(accountInfo.getSaleState());
//        accountInfo.setSaleStateName(info);
        return AjaxResult.success(goodsInfoAndAttrValueDto);
    }

    /**
     * 提交订单
     */
    @ResponseBody
    @PostMapping("/submitOrder")
    public AjaxResult submitOrder(@RequestBody JSONObject params) {

         accountOrderService.submitOrder(params);

        return AjaxResult.success();
    }



    /**
     * 供货提交页面
     */
    @ResponseBody
    @GetMapping("/superSubmit")
    public AjaxResult superSubmit(@RequestParam Map<String,Object> params) {

       JSONObject result =  accountOrderService.superSubmit(params);

        return AjaxResult.success(result);
    }

    /**
     * 供货提交页面
     */
    @ResponseBody
    @PostMapping("/superSubmitRemake")
    public AjaxResult superSubmitRemake(@RequestBody JSONObject params) {

        accountOrderService.superSubmitRemake(params);

        return AjaxResult.success();
    }

    /**
     * 供货提交页面
     */
    @ResponseBody
    @GetMapping("/cancelOrder")
    public AjaxResult cancelOrder(@RequestParam Map<String,Object> params) {

        accountOrderService.cancelOrder(params);

        return AjaxResult.success();
    }

    /**
     * 手机号
     * 终端编号
     * 备注
     * 账号
     * 密码
     * 资料补充
     * 截图
     * 其他截图
     * 订单编号
     */

    /**
     * 终端编号
     * 游戏类型
     * 实名状态
     * 订单编号
     */

    /**
     * accountID
     * img_url
     * 类型
     * 订单编号
     */



}
