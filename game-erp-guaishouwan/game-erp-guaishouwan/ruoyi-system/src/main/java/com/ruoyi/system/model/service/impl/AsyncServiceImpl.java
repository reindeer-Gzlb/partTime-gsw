package com.ruoyi.system.model.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.inter.mapper.*;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.mapper.*;
import com.ruoyi.system.model.service.AsyncService;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.outto.service.IOutApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private IOutApi outApi;

    @Resource
    private SalePartnerMapper salePartnerMapper;
    @Resource
    private ApiTaskPoolMapper apiTaskPoolMapper;

    @Resource
    private ArtRecMapper artRecMapper;


    @Override
    public void excuTaskPool(Long id, Long configId, String type, Long createId, Long userId) {
        /**
         * 1、获取配置详情
         * 2、拼装入库
         */
        if (!Objects.isNull(configId)){
            SalePartner salePartner = salePartnerMapper.selectSalePartnerById(configId);

            ArtRec artRec = new ArtRec();
            artRec.setConfig(salePartner.getId());
            artRec.setAccountId(id);
            List<ArtRec> artRecs = artRecMapper.selectArtRecList(artRec);
            if (!CollectionUtils.isEmpty(artRecs)){
                ApiTaskPool apiTaskPool = new ApiTaskPool();
                apiTaskPool.setCreateId(userId);
                apiTaskPool.setCreateTime(new Date());
                apiTaskPool.setSuperId(createId);
                apiTaskPool.setConfig(String.valueOf(configId));
                apiTaskPool.setType(type);
                apiTaskPool.setStatus(3l);
                apiTaskPool.setPartnerName(salePartner.getName());
                apiTaskPool.setAccountId(id);
                apiTaskPool.setPartCode(salePartner.getSaleName());
                apiTaskPoolMapper.insertApiTaskPool(apiTaskPool);
            }
        }else {

            SalePartner salePartner = new SalePartner();
            salePartner.setStatus("1");
            List<SalePartner> salePartners = salePartnerMapper.selectSalePartnerList(salePartner);

            for (SalePartner partner : salePartners) {
                ArtRec artRec = new ArtRec();
                artRec.setConfig(partner.getId());
                artRec.setAccountId(id);
                List<ArtRec> artRecs = artRecMapper.selectArtRecList(artRec);
                if (!CollectionUtils.isEmpty(artRecs)){
                    log.info(partner.getSaleName());
                    ApiTaskPool apiTaskPool = new ApiTaskPool();
                    apiTaskPool.setCreateId(userId);
                    apiTaskPool.setCreateTime(new Date());
                    apiTaskPool.setSuperId(createId);
                    apiTaskPool.setConfig(String.valueOf(partner.getId()));
                    apiTaskPool.setType(type);
                    apiTaskPool.setStatus(3l);
                    apiTaskPool.setPartnerName(partner.getName());
                    apiTaskPool.setAccountId(id);
                    apiTaskPool.setPartCode(partner.getSaleName());
                    apiTaskPoolMapper.insertApiTaskPool(apiTaskPool);
                }
            }
        }
    }

    @Override
    public void excuAddTask(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto, Long configId) {

        outApi.addAndAttr(goodsInfoAndAttrValueDto,configId);


    }
}
