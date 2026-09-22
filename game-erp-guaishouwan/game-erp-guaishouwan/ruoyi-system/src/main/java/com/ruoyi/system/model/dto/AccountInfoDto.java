package com.ruoyi.system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lz
 * @version 1.0
 * @date 2022/8/6 12:23
 */
@Data
public class AccountInfoDto {


    private String type;
    private String regional;
    private String level;
    private String extractAccount;
    private Integer heroes;
    private Integer skins;
    private Integer suit;
    private Integer guns;
    private BigDecimal price;
    private String des;
    private String supplyName;
    private Long supplyId;
    private Long id;
    private String gameName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String imgList;
    private String orderNo;
    private String uuid;
    private String titleImg;

}
