package com.ruoyi.system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.model.domain.GoodsInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author zyy
 * @date 2023-06-13
 */
@Data
public class GoodsBrowseRes extends GoodsInfo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date browseTime;
}
