package com.ruoyi.system.outto.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.outto.core.LCConstant;
import com.ruoyi.system.outto.game.CommonApi;
import com.ruoyi.system.outto.service.IOutApi;
import com.ruoyi.system.outto.thread.StartInter;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/8 9:38
 */
@Service
public class IGameApiImpl implements IOutApi {


    @Autowired
    private IGoodsInfoService goodsInfoService;

    @Autowired
    private StartInter startInter;
    @Resource
    private ISysUserService userService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Resource
    private SalePartnerMapper salePartnerMapper;

    /**
     * 1、获取所有销售伙伴
     * 2、循环销售伙伴
     * 3、调用销售伙伴对应接口
     * 4、返回结果进入记录
     *
     * @param
     */
    @Override
    public void add(Long id, Long configId) {

        Long userId = 0l;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.getMessage();
        }
        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = goodsInfoService.selectGoodsInfoAndAttrValueById(id);
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(id);
        bacthAdprice(goodsInfo);
        sensitive(goodsInfo);
        List<Long> saleNames = new ArrayList<>();

        BeanUtils.copyProperties(goodsInfo,goodsInfoAndAttrValueDto);
        if (Objects.nonNull(configId)) {
            saleNames.add(configId);
        }

        if (null != saleNames && !CollectionUtils.isEmpty(saleNames)) {
            startInter.start(goodsInfoAndAttrValueDto, "add", 1, null, saleNames);
        }
    }

    @Override
    public void addAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto, Long config) {
        Long userId = 0l;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.getMessage();
        }
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(goodsInfoAndAttrValueDto.getId());
        bacthAdprice(goodsInfo);
        sensitive(goodsInfo);
        List<Long> saleNames = new ArrayList<>();

        BeanUtils.copyProperties(goodsInfo,goodsInfoAndAttrValueDto);
        if (Objects.nonNull(config)) {
            saleNames.add(config);
        }

        if (null != saleNames && !CollectionUtils.isEmpty(saleNames)) {
            startInter.start(goodsInfoAndAttrValueDto, "add", 1, null, saleNames);
        }
    }

    @Override
    public void del(Long id, Long config, int type, Long taskId) {
        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = goodsInfoService.selectGoodsInfoAndAttrValueById(id);
        if (Objects.nonNull(goodsInfoAndAttrValueDto)) {
            List<Long> saleNames = new ArrayList<>();
            if (Objects.isNull(config) || 0l == config) {
            } else {
                saleNames.add(config);
            }
            if (null != saleNames && !CollectionUtils.isEmpty(saleNames)) {
                Long userId = 0l;
                try {
                    userId = SecurityUtils.getUserId();
                } catch (Exception e) {
                    userId = 1l;
                }
                startInter.start(goodsInfoAndAttrValueDto, "del", type, taskId, saleNames);
            }
        }
    }

    @Override
    public void edit(Long id, Long configId, Long taskId) {

        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = goodsInfoService.selectGoodsInfoAndAttrValueById(id);
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(id);

        bacthAdprice(goodsInfo);
        sensitive(goodsInfo);
        List<Long> saleNames = new ArrayList<>();
        if (Objects.isNull(configId)) {
        } else {
            saleNames.add(configId);
        }
        BeanUtils.copyProperties(goodsInfo,goodsInfoAndAttrValueDto);
        if (null != saleNames && !CollectionUtils.isEmpty(saleNames)) {
            Long userId = 0l;
            try {
                userId = SecurityUtils.getUserId();
            } catch (Exception e) {
                e.getMessage();
            }
            startInter.start(goodsInfoAndAttrValueDto, "edit", 1, taskId, saleNames);
        }
    }

    private void bacthAdprice(GoodsInfo goodsInfo) {
        boolean b = true;

        if (b) {
            List<GoodsInfo> list = new ArrayList<>();
            list.add(goodsInfo);
            if (!CollectionUtils.isEmpty(list)) {
                List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
                goodsInfoService.bacthAddPrice(list, supplyCustomer);
                GoodsInfo info = list.get(0);
                if (Objects.nonNull(info.getNewPrice())) {
                    info.setPrice(info.getNewPrice());
                }
                goodsInfo = info;
            }
        }
    }

    /**
     * 获取当前登录用户授权的销售伙伴
     *
     * @return
     */
    private List<Long> getSaleNames(Long userId) {
        SalePartner salePartner = new SalePartner();
        salePartner.setStatus("1");
        List<SalePartner> salePartners = salePartnerMapper.selectSalePartnerList(salePartner);
        List<Long> collect = salePartners.stream().map(c -> c.getId()).collect(Collectors.toList());
        return collect;
    }


    public static void main(String[] args) {
        String des = "f236sd12345678i";
        String str2 = "";
        if (des != null && !"".equals(des)) {
            for (int i = 0; i < des.length(); i++) {
                if (des.charAt(i) >= 48 && des.charAt(i) <= 57) {
                    str2 += des.charAt(i);
                } else {
                    System.out.println(str2);
                    if (str2.length() > 6) {
                        System.out.println(str2);
                        des = des.replace(str2, "");
                    }
                    str2 = "";
                }
            }
        }
        System.out.println(des);
    }

    /**
     * 过滤敏感词汇
     *
     * @param goodsInfo
     */
    private void sensitive(GoodsInfo goodsInfo) {
        List<String> sensitive = sensitive();
        for (String vocabulary : sensitive) {

            String gameName = goodsInfo.getTitle();
            if (Objects.nonNull(gameName)) {
                gameName = gameName.replace(vocabulary, "");
                goodsInfo.setTitle(gameName);
            }

            String des = goodsInfo.getDes();
            if (Objects.nonNull(des)) {
                des = des.replace(vocabulary, "");
                goodsInfo.setDes(des);
            }
        }
    }


    private List<String> sensitive() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(LCConstant.sensitive);
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        return sysDictDataList.stream().map(s -> s.getDictValue()).collect(Collectors.toList());
    }

}
