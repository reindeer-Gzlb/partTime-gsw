package com.ruoyi.system.outto.game.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.inter.mapper.PriceRecMapper;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.mapper.*;
import com.ruoyi.system.outto.core.LCConstant;
import com.ruoyi.system.outto.game.CommonApi;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/30 17:15
 */
@Service
public class CommonApiImpl implements CommonApi {

    @Resource
    private SalePartnerMapper salePartnerMapper;
    @Resource
    private SuprAuthMapper suprAuthMapper;
    @Resource
    private InterNameMapper interNameMapper;
    @Resource
    private FieldConfigMapper fieldConfigMapper;

    @Resource
    private PriceRecMapper priceRecMapper;


    @Autowired
    private ISysDictDataService dictDataService;

    @Override
    public SalePartner get(String code, Long userId) {
        SuprAuth suprAuth = new SuprAuth();
        suprAuth.setSuprId(userId);

        InterName interName = new InterName();
        interName.setCode(code);
        List<InterName> interNames = interNameMapper.selectInterNameList(interName);
        if (!CollectionUtils.isEmpty(interNames)) {
            suprAuth.setSaleId(String.valueOf(interNames.get(0).getId()));
            List<SuprAuth> suprAuths = suprAuthMapper.selectSuprAuthList(suprAuth);
            if (!CollectionUtils.isEmpty(suprAuths)) {
                SalePartner salePartner = salePartnerMapper.selectSalePartnerById(suprAuths.get(0).getConfigId());
                if (!Objects.isNull(salePartner)) {
                    return salePartner;
                }
            }
        }
        return null;
    }

    @Override
    public List<String> getYDList() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(LCConstant.saleNameYDD);
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        return sysDictDataList.stream().map(s -> s.getDictValue()).collect(Collectors.toList());
    }

    @Override
    public String getSelectLable(String type, String fildName, String labels) {

        /**
         * 1、获取游戏和字段的多选框
         * 2、循环选中标签
         */
        Set<String> resultList = new HashSet<>();
        FieldConfig fieldConfig = new FieldConfig();
        fieldConfig.setType(type);
        fieldConfig.setFieldName(fildName);
        fieldConfig.setCfgType("1");
        List<FieldConfig> fieldConfigs = fieldConfigMapper.selectFieldConfigList(fieldConfig);
        for (FieldConfig config : fieldConfigs) {
            String fieldValue = config.getFieldValue();
            if (StringUtils.isNotEmpty(fieldValue)) {
                String[] split = fieldValue.split(",");
                Arrays.asList(split).stream().forEach(s -> {
                    if (StringUtils.isNotEmpty(labels) && labels.contains(s)) {
                        resultList.add(s);
                    }
                    ;
                });
            }
        }
        return StringUtils.join(resultList.toArray(), ",");
    }

    @Override
    public BigDecimal addPrice(BigDecimal oldprice, Long discount) {
        if (Objects.nonNull(oldprice)) {
            if (Objects.isNull(discount)) {
                return oldprice;
            }
//            BigDecimal add = new BigDecimal(100).add(new BigDecimal(discount)).divide(new BigDecimal(100));
//            BigDecimal multiply = oldprice.multiply(add).setScale(0, BigDecimal.ROUND_UP);


            BigDecimal multiply = new BigDecimal(100).subtract(new BigDecimal(discount));

            BigDecimal divide = multiply.divide(new BigDecimal(100));

            BigDecimal divide1 = oldprice.divide(divide, 0, BigDecimal.ROUND_UP);

            return divide1;
        } else {
            return new BigDecimal(0);
        }
    }
    @Override
    public BigDecimal addPrice(BigDecimal price, String config) {
        BigDecimal bigDecimal = price;
        if (Objects.nonNull(price)) {
            if (Objects.isNull(config)) {
                return price;
            }
            if (StringUtils.isNotEmpty(config)) {
                List<JSONObject> addRetailConfig = JSONArray.parseArray(config, JSONObject.class);
                for (JSONObject addConfig : addRetailConfig) {
                    String down = addConfig.getString("down");
                    if (StringUtils.isNotEmpty(down)) {
                        if (price.compareTo(new BigDecimal(down)) > 0) {
                            String up = addConfig.getString("up");
                            if (StringUtils.isEmpty(up) || new BigDecimal(up).compareTo(price) >= 0) {
                                String type = addConfig.getString("type");
                                String raise = addConfig.getString("raise");
                                if (StringUtils.equals(type, "1")) {
                                    bigDecimal = bigDecimal.add(new BigDecimal(raise));
                                } else {
                                    BigDecimal dive = new BigDecimal(raise).add(new BigDecimal(100)).divide(new BigDecimal(100));
                                    bigDecimal = price.multiply(dive).setScale(0, BigDecimal.ROUND_DOWN);
                                }
                                break;
                            }
                        }
                    }
                }
            }
            return bigDecimal;
        } else {
            return new BigDecimal(0);
        }
    }


    public void addPrice11(){


    }
    public void setPrice(BigDecimal oldprice, Long id, SalePartner salePartner) {

        Long seleep = salePartner.getSeleep();
        if (seleep > 0) {
            PriceRec priceRec = new PriceRec();
            priceRec.setPrice(oldprice.toPlainString());
            priceRec.setSaleCode(salePartner.getSaleName());
            priceRec.setSaleName(salePartner.getName());
            priceRec.setConfig(salePartner.getId());
            priceRec.setInfoid(id);

            PriceRec select = new PriceRec();
            select.setInfoid(id);
            select.setConfig(salePartner.getId());
            List<PriceRec> priceRecs = priceRecMapper.selectPriceRecList(select);
            if (!CollectionUtils.isEmpty(priceRecs)) {
                PriceRec priceRec1 = priceRecs.get(0);
                priceRec1.setPrice(oldprice.toPlainString());
                priceRec1.setSaleCode(salePartner.getSaleName());
                priceRec1.setSaleName(salePartner.getName());
                priceRec1.setConfig(salePartner.getId());
                priceRec1.setInfoid(id);
                priceRecMapper.updatePriceRec(priceRec1);
            } else {
                priceRecMapper.insertPriceRec(priceRec);
            }
        }
    }

    public static void main(String[] args) {
        BigDecimal price = new BigDecimal(46);

        String config = "[{\"down\":\"44\",\"up\":\"55\",\"type\":\"2\",\"raise\":\"55\"},{\"down\":\"66\",\"up\":\"66\",\"type\":\"1\",\"raise\":\"66\"},{\"down\":\"88\",\"up\":\"88\",\"type\":\"2\",\"raise\":\"88\"},{\"down\":\"99\",\"up\":\"99\",\"type\":\"1\",\"raise\":\"99\"},{\"down\":\"22\",\"up\":\"22\",\"type\":\"2\",\"raise\":\"22\"},{\"down\":\"77\",\"up\":\"77\",\"type\":\"1\",\"raise\":\"77\"}]";
        BigDecimal bigDecimal = price;
        if (Objects.nonNull(price)) {
            if (StringUtils.isNotEmpty(config)) {
                List<JSONObject> addRetailConfig = JSONArray.parseArray(config, JSONObject.class);
                for (JSONObject addConfig : addRetailConfig) {
                    String down = addConfig.getString("down");
                    if (StringUtils.isNotEmpty(down)) {
                        if (price.compareTo(new BigDecimal(down)) > 0) {
                            String up = addConfig.getString("up");
                            if (StringUtils.isEmpty(up) || new BigDecimal(up).compareTo(price) >= 0) {
                                String type = addConfig.getString("type");
                                String raise = addConfig.getString("raise");
                                if (StringUtils.equals(type, "1")) {
                                    bigDecimal = bigDecimal.add(new BigDecimal(raise));
                                } else {
                                    BigDecimal dive = new BigDecimal(raise).add(new BigDecimal(100)).divide(new BigDecimal(100));
                                    bigDecimal = price.multiply(dive).setScale(0, BigDecimal.ROUND_DOWN);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } else {
        }
        System.out.println(bigDecimal.toPlainString());
    }

}
