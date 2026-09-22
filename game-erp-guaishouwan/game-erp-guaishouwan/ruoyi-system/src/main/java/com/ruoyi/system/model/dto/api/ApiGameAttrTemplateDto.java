package com.ruoyi.system.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiGameAttrTemplateDto {
    private Long id;
    private String aavName;
    private String fieldType;
    private String fieldName;
    private List values;//取值范围
    private String must;
    private Long attributeId;


}
