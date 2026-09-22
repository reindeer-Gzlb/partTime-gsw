package com.ruoyi.system.outto.game;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.SalePartner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/30 17:15
 */
public interface CommonApi {

    public SalePartner get(String code,Long userId);

    public List<String> getYDList();

    public String getSelectLable(String type,String  fildName,String labels);

    public BigDecimal addPrice(BigDecimal oldprice, Long discount);
    public BigDecimal addPrice(BigDecimal oldprice, String config);

    public void setPrice(BigDecimal oldprice,Long id, SalePartner salePartner);

}
