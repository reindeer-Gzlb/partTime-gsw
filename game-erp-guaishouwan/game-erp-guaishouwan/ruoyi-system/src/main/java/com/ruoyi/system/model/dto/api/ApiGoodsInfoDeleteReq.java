package com.ruoyi.system.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiGoodsInfoDeleteReq {
    @NotNull(message = "参数goodsId不能为空")
    Long goodsId;
}
