package com.ruoyi.system.outto.dto;

import lombok.Data;
import org.apache.commons.math3.stat.descriptive.summary.Product;

@Data
public class HhbDto {
    private String appid;
    private String act;
    private HhbProductDto product;
}
