package com.ruoyi.system.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class DeleteAllVo {

    private List<String> supplyIdAddr;
    private List<Long> configArr;
}
