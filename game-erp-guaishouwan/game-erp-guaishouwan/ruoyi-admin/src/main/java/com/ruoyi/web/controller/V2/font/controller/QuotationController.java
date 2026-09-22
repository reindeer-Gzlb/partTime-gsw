package com.ruoyi.web.controller.V2.font.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.V2.font.mapper.QuotationMapper;
import com.ruoyi.system.V2.font.service.IQuotationService;
import com.ruoyi.system.V2.recycle.service.IKrecycleService;
import com.ruoyi.system.V2.tool.mapper.BacklistMapper;
import com.ruoyi.system.V2.tool.service.IBacklistService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.Krecycle;
import com.ruoyi.system.model.domain.Quotation;
import com.ruoyi.system.outto.dto.HttpUtil;
import com.ruoyi.system.send.config.YunLuConfig;
import com.ruoyi.system.send.service.SendMsgService;
import com.ruoyi.system.sms.service.ISendSms;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 【供货商报价单】Controller
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Slf4j
@RestController
@RequestMapping("/account/quotation")
public class QuotationController extends BaseController {
    @Autowired
    private IQuotationService quotationService;
    @Autowired
    private QuotationMapper quotationMapper;
    @Autowired
    private IBacklistService backlistService;
    @Autowired
    private BacklistMapper backlistMapper;
    @Autowired
    private ISendSms sendSms;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IKrecycleService krecycleService;

    @Autowired
    YunLuConfig yunLuConfig;

    String lshttpUrl = "";
    String lsappKey = "";
    String lsappSecret = "";

    /**
     * 回收池报价
     */
    @GetMapping("/list")
    public TableDataInfo list(Quotation quotation) {
        startPage();
        List<Backlist> list = quotationService.selectQuotationList(quotation);
        return getDataTable(list);
    }

    /**
     * 回收池报价
     */
    @GetMapping("/kfList")
    public TableDataInfo kfList(Quotation quotation) {
        startPage();
        List<Quotation> list = quotationService.kefuList(quotation);
        return getDataTable(list);
    }

    /**
     * 我的回收报价
     */
    @GetMapping("/myList")
    public TableDataInfo myList(Quotation quotation) {
        startPage();
        List<Backlist> list = quotationService.selectMyQuotationList(quotation);
        return getDataTable(list);
    }

    /**
     * 我的回收报价
     */
    @GetMapping("/countOfferList")
    public AjaxResult countOfferList(@RequestParam Map<String, Object> params) {
        List<JSONObject> list = quotationService.countOfferList(params);
        return AjaxResult.success(list);
    }


    /**
     * 我的报价
     */
    @GetMapping("/mybprice")
    public AjaxResult mybprice(Quotation quotation) {

        Map<String, Object> result = new HashMap<>();
        quotation.setNoStatus("5");
        List<Quotation> list = quotationService.mybprice(quotation);
        result.put("rlist", list);
        Backlist backlist = backlistService.selectBacklistById(quotation.getBackId());
        result.put("back", backlist);
        return AjaxResult.success(result);
    }

    /**
     * 回收联盟  拒绝报价
     */
    @GetMapping("/refuse")
    public AjaxResult refuse(@RequestParam Map<String, Object> params) {

        return AjaxResult.success(quotationService.refuse(params));
    }

    /**
     *
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(quotationService.selectQuotationById(id));
    }

    /**
     *
     */
    @PostMapping
    public AjaxResult add(@RequestBody Quotation quotation) {
        Backlist backlist = backlistMapper.selectBacklistById(quotation.getBackId());
        if (Objects.isNull(backlist)) {
            throw new GlobalException("未找到此回收编号！");
        }
        int cc = quotationMapper.getTodayCount(backlist);
        if (cc >= 10) {
            throw new GlobalException("当前回账号已超出当天报价次数");
        }
        int count = quotationService.insertQuotation(quotation);
        //从三方过来的
        if (backlist.getPartition() == 3 && StringUtils.isNotEmpty(backlist.getOtherUserId())) {
            SysUser sysUser = sysUserMapper.selectUserById(backlist.getCreateId());
            if (StringUtils.isNotNull(sysUser)) {
                //如果是从雷神过来的回收，报价成功后通知雷神
                if (StringUtils.equals(sysUser.getUserName(), "leishi123")) {
                    String profile = SpringUtils.getActiveProfile();
                    if (StringUtils.equals(profile, "ydds-prd")) {
                        // 只有配置文件等于ydds.prd 才认为是线上环境 才发送
                        leishenNotice(backlist, quotation);
                    }
                }
            }
        } else if (backlist.getPartition() == 2 || backlist.getPartition() == 1) { //从官网 或者 回收链接 过来的
            if (backlist.getTransfer() == 2) { //在商家联盟里报价的才发短信
                //发送报价短信
                //配置字典后才会发短信
                List<SysDictData> sysDictData = sysDictDataMapper.selectDictDataByType("quotationSuccess");
                if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(sysDictData)) {
                    SysDictData s = sysDictData.get(0);
                    if (Objects.nonNull(s) && StringUtils.equals(s.getDictValue(), "on")) {
                        try {
                            //lu
                            JSONObject params = new JSONObject();
                            params.put("code", backlist.getAccount());
                            params.put("price", quotation.getBprice());
                            params.put("link", yunLuConfig.getBaseUrl());
                            params.put("tel", backlist.getTel());
                            logger.info("发送手机号={}", backlist.getTel());
                            sendMsgService.sendLinkCode(params);
                        } catch (Exception e) {
                            log.info("短信发送失败" + e.getMessage());
                        }
                    }
                }
            }
        }
        return toAjax(count);
    }

    /**
     *
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Quotation quotation) {
        return toAjax(quotationService.updateQuotation(quotation));
    }

    /**
     *
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(quotationService.deleteQuotationByIds(ids));
    }

    @GetMapping("/notify/{id}")
    public AjaxResult NotifyToLS(@PathVariable("id") Long id) {
        Krecycle krecycle = krecycleService.selectKrecycleById(id);
        if (StringUtils.isNull(krecycle)) {
            throw new GlobalException("未找到此回收订单");
        }
        Backlist backlist = backlistService.selectBacklistById(krecycle.getBackId());
        if (StringUtils.isNull(backlist)) {
            throw new GlobalException("未找到此回收订单的回收详情");
        }
        SysUser sysUser = sysUserMapper.selectUserById(backlist.getCreateId());
        if (StringUtils.isNotNull(sysUser)) {
            //如果是从雷神过来的回收，调用雷神的提醒上线接口
            if (StringUtils.equals(sysUser.getUserName(), "leishi123")) {
                String profile = SpringUtils.getActiveProfile();
                if (StringUtils.equals(profile, "ydds-prd")) {
                    // 只有配置文件等于ydds.prd 才认为是线上环境 才发送
                    leishenNotice1(backlist);
                    log.info("调用雷神通知用户接口");
                }
            } else {
                throw new GlobalException("此回收订单不支持提醒用户上线");
            }
        } else {
            throw new GlobalException("未找到创建人");
        }
        return AjaxResult.success();
    }


    public static void main(String[] args) {
//        Backlist backlist = new Backlist();
//        backlist.setOtherUserId("18530912310");
//        Quotation quotation = new Quotation();
//        quotation.setBprice("999.1");
//        leishenNotice(backlist, quotation);


//        krecycle.setCreateTime(DateUtils.getNowDate());
//        krecycle.setCreateId(SecurityUtils.getUserId());
//        krecycle.setClientId(SecurityUtils.getUserId());
//        krecycle.setStatus("2");
//
//        //报价账号   状态修改 为  已接受
//
//
//        Long backId = krecycle.getBackId();
//        Backlist backlist = backlistMapper.selectBacklistById(backId);
//        if (Objects.isNull(backlist)) {
//            throw new GlobalException("未查询到该回收账号");
//        }
//        backlist.setStatus(2l);
//        backlist.setTransfer(1);
//        backlist.setHsPrice(krecycle.getBprice());
//        backlist.setBackState(2);
//        backlist.setSupplyUserId(krecycle.getSuplair());
//        // 从联盟进来的 就把content 存入info 信息中
//        if (StringUtils.isNotEmpty(backlist.getContent())) {
//            GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = JSONObject.parseObject(backlist.getContent(),
//            GoodsInfoAndAttrValueDto.class);
//            if (ObjectUtils.isNotEmpty(goodsInfoAndAttrValueDto)) {
//                goodsInfoAndAttrValueDto.setCreateId(SecurityUtils.getUserId());
//                goodsInfoAndAttrValueDto.setSaleid(SecurityUtils.getUserId());
//                goodsInfoAndAttrValueDto.setAccountType(1); //走发布的 都属于自营
//                goodsInfoAndAttrValueDto.setSaleState(88);  //新增默认为 未出售状态
//                goodsInfoService.insertGoodsInfoAndAttr(goodsInfoAndAttrValueDto);
//                if (goodsInfoAndAttrValueDto.getId() != null && goodsInfoAndAttrValueDto.getId() != 0) {
//                    backlist.setGid(goodsInfoAndAttrValueDto.getId());
//                } else {
//                    log.info("获取到的id 是" + goodsInfoAndAttrValueDto.getId());
//                }
//            }
//        }
//        backlistMapper.updateBacklist(backlist);
//        // 回收成功转我的回收 回收中状态
//        BacklistHistory history = new BacklistHistory();
//        history.setCreateId(SecurityUtils.getUserId());
//        history.setBackListId(backId);
//        history.setOperation(1);
//        backlistHistoryMapper.insertBacklistHistory(history);
//
//        krecycle.setTel(backlist.getTel());
//
//        //报价单  当前接受的  为已接受 其他情况为已拒绝
//        Quotation quotation = new Quotation();
//        quotation.setBackId(backId);
//        List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
//
//        if (CollectionUtils.isNotEmpty(quotations)) {
//            for (Quotation q : quotations) {
//                if (Objects.equals(q.getCreateId(), krecycle.getSuplair())) {
//                    q.setStatus("1");
//                } else {
//                    q.setStatus("2");
//                }
//            }
//            quotationMapper.bacthUpdate(quotations);
//        } else {
//            throw new GlobalException("未查询到该回收账号");
//        }
//        return krecycleMapper.insertKrecycle(krecycle);

    }

    void leishenNotice1(Backlist backlist) {
        if (leishenInitialize()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            Map<String, Object> header = new HashMap<>();
            header.put("reqChannel", 4);
            Map<String, Object> params = new HashMap<>();
            params.put("appKey", lsappKey);
            params.put("timestamp", timestamp);
            params.put("userId", backlist.getOtherUserId());
//            params.put("amount", quotation.getBprice());
            params.put("type", 4);
            String paramJson = String.valueOf(new JSONObject(params));
            params.put("paramJson", paramJson);
            String algSign = algSign(lsappKey, lsappSecret, timestamp, paramJson);
            params.put("sign", algSign);
            String response = HttpUtil.postRequest(lshttpUrl, params, ContentType.APPLICATION_JSON, null, header);
            log.info(response);
        }
    }

    void leishenNotice(Backlist backlist, Quotation quotation) {
        if (leishenInitialize()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            Map<String, Object> header = new HashMap<>();
            header.put("reqChannel", 4);
            Map<String, Object> params = new HashMap<>();
            params.put("appKey", lsappKey);
            params.put("timestamp", timestamp);
            params.put("userId", backlist.getOtherUserId());
            params.put("amount", quotation.getBprice());
            params.put("type", 1);
            String paramJson = String.valueOf(new JSONObject(params));
            params.put("paramJson", paramJson);
            String algSign = algSign(lsappKey, lsappSecret, timestamp, paramJson);
            params.put("sign", algSign);
            String response = HttpUtil.postRequest(lshttpUrl, params, ContentType.APPLICATION_JSON, null, header);
            log.info(response);
        }
    }

    public String algSign(String appKey, String appSecret, String timestamp, String paramJson) {
        StringBuilder sf = new StringBuilder();
        sf.append(appSecret);
        sf.append("appKey");
        sf.append(appKey);
        sf.append("paramJson");
        sf.append(paramJson);
        sf.append("timestamp");
        sf.append(timestamp);
        sf.append(appSecret);
        String algSign = md5(sf.toString());
        return algSign;
    }

    public String md5(String plainText) {
        String result = null;
        if (StringUtils.isBlank(plainText)) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] btInput = plainText.getBytes();
            md.update(btInput);
            byte[] btResult = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : btResult) {
                int bt = b & 0xff;
                if (bt < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
        }
        return result;
    }

    boolean leishenInitialize() {
        if (StringUtils.isEmpty(lsappKey) || StringUtils.isEmpty(lsappSecret) || StringUtils.isEmpty(lshttpUrl)) {
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("leishensend");
            for (SysDictData sysDictData : sysDictDataList) {
                if (sysDictData.getDictLabel().equals("lsappKey")) {
                    lsappKey = sysDictData.getDictValue();
                }
                if (sysDictData.getDictLabel().equals("lsappSecret")) {
                    lsappSecret = sysDictData.getDictValue();
                }
                if (sysDictData.getDictLabel().equals("lshttpUrl")) {
                    lshttpUrl = sysDictData.getDictValue();
                }
            }
        }
        if (StringUtils.isEmpty(lsappKey) || StringUtils.isEmpty(lsappSecret) || StringUtils.isEmpty(lshttpUrl)) {
            return false;
        }
        return true;
    }
}
