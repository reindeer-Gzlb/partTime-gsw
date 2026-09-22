package com.ruoyi.system.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiGoodsInfoEditReq {
    /**
     * 商品id
     */
    @NotNull(message = "参数goodsId不能为空")
    private Long goodsId;
    /**
     * 商品价格
     */
    @NotNull(message = "参数price不能为空")
    private BigDecimal price;
    /**
     * 商品描述
     */
    private String des;

}
