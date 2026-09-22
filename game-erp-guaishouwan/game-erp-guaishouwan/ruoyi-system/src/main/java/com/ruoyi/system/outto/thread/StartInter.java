package com.ruoyi.system.outto.thread;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.inter.mapper.*;
import com.ruoyi.system.V2.inter.service.IInterRecService;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.outto.core.LCConstant;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/18 9:04
 */
@Component
public class StartInter {

    @Resource
    private OutRecMapper outRecMapper;

    @Resource
    private ArtRecMapper artRecMapper;
    @Resource
    private ApiTaskPoolMapper apiTaskPoolMapper;

    @Resource
    private ApiReqCopyMapper apiReqCopyMapper;

    @Resource
    private SalePartnerMapper salePartnerMapper;
    @Autowired
    private IInterRecService interRecService;


    String operType = "";
    static String addDoType = "add";
    static String delDoType = "del";
    static String modDoType = "mod";

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";
    public static final String OUTDI = "outId";

    @Transactional
    public void start(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto, String type, int delType, Long taskId,
                      List<Long> saleNames) {

        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put(CODE_TAG, 200);
        ajaxResult.put(MSG_TAG, "操作成功");
        ajaxResult.put(OUTDI, null);

        if (type.equals(Constant.add)) {
            for (Long saleId : saleNames) {
                this.operType = "2";
                SalePartner salePartner = salePartnerMapper.selectSalePartnerById(saleId);
                try {

                    getOutRecAndInterRec(ajaxResult, salePartner.getSaleName(), salePartner, goodsInfoAndAttrValueDto, taskId);
                } catch (Exception e) {
                    e.printStackTrace();
                    ajaxResult.put(CODE_TAG, 500);
                    ajaxResult.put(MSG_TAG, e.getMessage());
                    getOutRecAndInterRec(ajaxResult, salePartner.getSaleName(), salePartner, goodsInfoAndAttrValueDto, taskId);
                }
            }
        } else if (type.equals(Constant.edit)) {
            this.operType = "1";
            if (true) {
                for (Long saleId : saleNames) {
                    SalePartner salePartner = salePartnerMapper.selectSalePartnerById(saleId);
                    if (Objects.isNull(salePartner)) {
                        throw new GlobalException("salePartner为空");
                    }
                }
            }
        } else if (type.equals(Constant.del)) {
            this.operType = "1";
            for (Long saleId : saleNames) {
                SalePartner salePartner = salePartnerMapper.selectSalePartnerById(saleId);
                try {
                    Thread.sleep(1000);
                    delInterRec(goodsInfoAndAttrValueDto.getId(), salePartner.getSaleName());

                    insertOutRec(ajaxResult, goodsInfoAndAttrValueDto, LCConstant.interDel, salePartner, taskId);

                } catch (Exception e) {
                    e.printStackTrace();
                    ajaxResult.put(CODE_TAG, 500);
                    ajaxResult.put(MSG_TAG, e.getMessage());
                    insertOutRec(ajaxResult, goodsInfoAndAttrValueDto, LCConstant.interDel, salePartner, taskId);
                }
            }
        }
    }


    private void delInterRec(Long id, String saleName) {
        InterRec interRec = new InterRec();
        interRec.setAccountId(id);
        interRec.setSaleCode(saleName);
        int i = interRecService.deleteInterRec(id, saleName);
    }

    @Transactional
    public void getOutRecAndInterRec(AjaxResult ajaxResult, String saleNameLCC, SalePartner salePartner,
                                     GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto, Long taskId) {
        insertOutRec(ajaxResult, goodsInfoAndAttrValueDto, LCConstant.interAdd, salePartner, taskId);
        interRecService.save(saleNameLCC, goodsInfoAndAttrValueDto.getId(), goodsInfoAndAttrValueDto.getCreateId());
    }


    /**
     * 封装入参记录（保存推送记录）
     *
     * @param ajaxResults
     * @param interType
     * @return
     */
    @Transactional
    public void insertOutRec(AjaxResult ajaxResults, GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto,
                             String interType, SalePartner salePartner, Long taskId) {
        ApiTaskPool apiTaskPool = new ApiTaskPool();
        if (Objects.nonNull(taskId)) {
            apiTaskPool = apiTaskPoolMapper.selectApiTaskPoolById(taskId);
            if (Objects.nonNull(apiTaskPool)) {
                ApiReqCopy apiReqCopy = new ApiReqCopy();
                BeanUtils.copyProperties(apiTaskPool, apiReqCopy);
                apiReqCopyMapper.insertApiReqCopy(apiReqCopy);
                apiTaskPoolMapper.deleteApiTaskPoolById(taskId);
            }
        }
        if (LCConstant.interDel.equals(interType)) {
            artRecMapper.deleteArtRecCode(goodsInfoAndAttrValueDto.getId(), salePartner.getId());
        }

        OutRec outRec = new OutRec();
        outRec.setAccountid(goodsInfoAndAttrValueDto.getId());
        outRec.setType(goodsInfoAndAttrValueDto.getGameName());
        outRec.setSupllyid(goodsInfoAndAttrValueDto.getCreateId());
        Long userId = goodsInfoAndAttrValueDto.getCreateId();
        if (Objects.isNull(userId) || 0l == userId) {
            if (Objects.nonNull(apiTaskPool)) {
                outRec.setCreateId(apiTaskPool.getCreateId());
            } else {
                outRec.setCreateId(1l);
            }
        } else {
            outRec.setCreateId(userId);
        }

        Long accountId = goodsInfoAndAttrValueDto.getId();
        if (!Objects.isNull(salePartner)) {
            outRec.setSaleId(salePartner.getName());
            outRec.setSalecode(salePartner.getSaleName());
            /**
             * 格式：
             * 成功条数：100
             * 失败条数：200
             * 失败原因：
             * id:errInfo
             */
            StringBuffer result = new StringBuffer();
            int success = 0;
            int error = 0;
            StringBuffer errInfo = new StringBuffer();
            Integer code = MapUtils.getInteger(ajaxResults, CODE_TAG);
            String msg = "";
            String req = "";
            String res = "";
            if (Objects.equals(HttpStatus.SUCCESS, code)) {
                Object data = MapUtils.getObject(ajaxResults, "data");
                String s = JSONObject.toJSONString(data);
                JSONObject jsonObject = JSONObject.parseObject(s);
                if (StringUtils.isNotEmpty(s) && StringUtils.isNotEmpty(jsonObject)) {
                    req = jsonObject.getString("req");
                    res = jsonObject.getString("res");
                    outRec.setReq(req);
                    outRec.setRes(res);
                    if (Objects.equals(500, jsonObject.getInteger("code"))) {
                        error++;
                        msg = MapUtils.getString(jsonObject, MSG_TAG);
                        errInfo.append(msg + "\n");
                    } else {
                        success++;
                        //成功
                        if (LCConstant.interAdd.equals(interType)) {
                            ArtRec artRec = new ArtRec();
                            artRec.setAccountId(accountId);
                            artRec.setCreateId(goodsInfoAndAttrValueDto.getCreateId());
                            artRec.setType(interType);
                            artRec.setConfig(salePartner.getId());
                            artRec.setSaleCode(salePartner.getSaleName());
                        }
                    }
                }
            } else {
                error++;
                msg = MapUtils.getString(ajaxResults, MSG_TAG);
                errInfo.append(accountId + ":" + msg + "\n");
                outRec.setRes(msg);
            }
            result.append("成功：" + success +
                    "条,失败：" + error + "条,失败原因：" + errInfo + ",id为" + accountId);
            outRec.setResult(result.toString());
            outRec.setSuccessCount(success);
            outRec.setErrCount(error);
            outRec.setInter(interType);
            outRec.setCreateTime(DateUtils.getNowDate());
            outRecMapper.insertOutRec(outRec);
        }
    }

}
