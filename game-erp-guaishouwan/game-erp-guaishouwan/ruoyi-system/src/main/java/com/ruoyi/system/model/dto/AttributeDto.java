package com.ruoyi.system.model.dto;

import com.ruoyi.system.model.domain.Attribute;
import lombok.Data;

import java.util.List;

@Data
public class AttributeDto {
    private Long id;
    private String type;
    private String fieldType;
    private String fieldName;
    private Long fieldValue;
    private Long sort;
    private Long parentId;
    List<AttributeDto> attributes;
}
