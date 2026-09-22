package com.ruoyi.system.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoodReq {
    private Long[] ids;
    private String auditDes;
    private List<String> gameNames;
    private List<String> createIds;
    private Integer pageSize ;
    private String saleCode ;
}
