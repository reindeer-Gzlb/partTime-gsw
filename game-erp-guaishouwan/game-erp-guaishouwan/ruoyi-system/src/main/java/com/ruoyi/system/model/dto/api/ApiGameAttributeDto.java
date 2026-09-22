package com.ruoyi.system.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiGameAttributeDto {
    Long id; //id
    String fieldName; //游戏名称
    Long parentId;//上级id
    String remark;
    List<ApiGameAttributeDto> Child;
}
