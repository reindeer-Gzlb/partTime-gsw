package com.ruoyi.web.controller.V2.sms.controller;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.send.config.YunLuConfig;
import com.ruoyi.system.send.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收藏
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@RestController
@RequestMapping("/nd/login")
public class NeedLoginSmsController extends BaseController {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private YunLuConfig yunLuConfig;
    @Autowired
    private SendMsgService sendMsgService;

    /**
     * @param hxId    环信id
     * @param type    1 账号评估发卖家
     *                2 销售客服发买家
     *                3 销售客服发卖家-咨询
     *                4 销售客服发卖家-成交
     *                5上架客服发卖家-审核通过
     *                6上架客服发卖家-审核不通过
     * @param goodsId 商品编号
     * @return
     * @throws Exception
     */
    @RepeatSubmit(interval = 2000)
    @ResponseBody
    @GetMapping("/sendDX")
    public AjaxResult sendDX(String hxId, String type, Long goodsId) throws Exception {
        if (StringUtils.isEmpty(hxId) || StringUtils.isEmpty(type)) {
            throw new GlobalException("传入参数有误");
        }
        SysUser sysUser = sysUserMapper.selectUserByHxUserName(hxId);
        if (StringUtils.isNull(sysUser)) {
            throw new GlobalException("找不到此用户");
        }
        if (StringUtils.isEmpty(sysUser.getPhonenumber())) {
            throw new GlobalException("此用户手机号为空");
        }
        String code = "";
        String[] param = null;
        String tel = sysUser.getPhonenumber();
//        if (StringUtils.equals(type, "1")) {
//            code = yunLuConfig.getTempletePingGu();
//        } else if (StringUtils.equals(type, "2")) {
//            code = yunLuConfig.getTempleteZaiXian();
//        } else if (StringUtils.equals(type, "3")) {
//            code = yunLuConfig.getTempleteZiXun();
//        } else if (StringUtils.equals(type, "4")) {
//            code = yunLuConfig.getTempleteChengJiao();
//        } else if (StringUtils.equals(type, "7")) {
//            code = yunLuConfig.getTempleteTiXing();
//        } else {
//            throw new GlobalException("不支持的发送类型");
//        }
//        sendMsgService.sendByCode(code, param, tel);
        return AjaxResult.success();
    }

}
