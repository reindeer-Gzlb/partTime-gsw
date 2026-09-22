package com.ruoyi.system.V2.inter.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.V2.inter.mapper.*;
import com.ruoyi.system.model.domain.ArtRec;
import com.ruoyi.system.model.domain.OutRec;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.V2.inter.service.IOutRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 推送记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-07
 */
@Service
public class OutRecServiceImpl implements IOutRecService
{
    @Autowired
    private OutRecMapper outRecMapper;
    @Autowired
    private InterRecMapper interRecMapper;
    @Autowired
    private SalePartnerMapper salePartnerMapper;
    @Autowired
    private ApiTaskPoolMapper apiTaskPoolMapper;
    @Autowired
    private ArtRecMapper artRecMapper;
    @Autowired
    private JymtaskMapper jymtaskMapper;

    public void delAllOut(String saleCode) {
//        DELETE FROM inter_rec WHERE sale_code = 'JYM_CLL';
//        DELETE FROM api_task_pool WHERE part_code = 'JYM_CLL';
//        DELETE FROM art_rec WHERE sale_code = 'JYM_CLL';
//        DELETE FROM out_rec WHERE salecode = 'JYM_CLL';
        interRecMapper.deleteInterRecBySaleCode(saleCode);
        apiTaskPoolMapper.deleteApiTaskPoolByPartCode(saleCode);
        artRecMapper.deleteArtRecBySaleCode(saleCode);
        outRecMapper.deleteOutRecBySaleCode(saleCode);
        if(saleCode.contains("JYM")){
           jymtaskMapper.deleteJymtaskBySaleCode(saleCode);
        }
    }


    /**
     * 查询推送记录
     *
     * @param id 推送记录主键
     * @return 推送记录
     */
    @Override
    public OutRec selectOutRecById(Long id)
    {
        return outRecMapper.selectOutRecById(id);
    }

    /**
     * 查询推送记录列表
     *
     * @param outRec 推送记录
     * @return 推送记录
     */
    @Override
    public List<OutRec> selectOutRecList(OutRec outRec)
    {
        if (Objects.equals(0,outRec.getSuccessCount())){
            outRec.setErrCount(1);
        }
        List<OutRec> outRecs = outRecMapper.selectOutRecList(outRec);
        return outRecs;
    }

    /**
     * 新增推送记录
     *
     * @param outRec 推送记录
     * @return 结果
     */
    @Override
    public int insertOutRec(OutRec outRec)
    {
        outRec.setCreateTime(DateUtils.getNowDate());
        return outRecMapper.insertOutRec(outRec);
    }

    /**
     * 修改推送记录
     *
     * @param outRec 推送记录
     * @return 结果
     */
    @Override
    public int updateOutRec(OutRec outRec)
    {
        return outRecMapper.updateOutRec(outRec);
    }

    /**
     * 批量删除推送记录
     *
     * @param saleIdS 需要删除的推送记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteOutRecByIds(String[] saleIdS)
    {

        /**
         * 删除推送记录
         * 删除接口记录
         */
        Long userId = SecurityUtils.getUserId();
        Arrays.asList(saleIdS).stream().forEach(sale ->{

            SalePartner salePartner = new SalePartner();
            salePartner.setName(sale);
            String saleCode = "";
            List<SalePartner> salePartnerList = salePartnerMapper.selectSalePartnerList(salePartner);
            if (!CollectionUtils.isEmpty(salePartnerList)){
                saleCode = salePartnerList.get(0).getSaleName();
            }else {
                saleCode = sale;
            }

            if (Objects.nonNull(sale)){
                interRecMapper.deleteInterRecBySleCode(saleCode,userId);
                outRecMapper.deleteOutRecBySleCode(sale,userId);
            }
        });
        return 1;
    }

    /**
     * 删除推送记录信息
     *
     * @param id 推送记录主键
     * @return 结果
     */
    @Override
    public int deleteOutRecById(Long id)
    {
        return outRecMapper.deleteOutRecById(id);
    }

    @Override
    public List<String> selectSaleId() {
        return outRecMapper.selectSaleId();
    }
}
