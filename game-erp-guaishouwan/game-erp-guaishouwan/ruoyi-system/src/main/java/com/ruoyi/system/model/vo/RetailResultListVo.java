package com.ruoyi.system.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RetailResultListVo {

    private Long id;


    private String type;

    private String regional;

    private String level;

    private Integer heroes;

    private Integer skins;

    private Integer suit;

    private Integer guns;

    private BigDecimal price;

    private BigDecimal newPrice;

    private String des;

    private Date createTime;

    private Integer saleState;

    private Integer rise;
}
