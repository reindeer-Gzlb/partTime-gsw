package com.ruoyi.system.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiGameInfoDto {
    private String name; //游戏名称
    private Integer type; //游戏类型 0=手游,1=端游
    private List systems;
    private List carriers;
    private List groups;
    private List servers;
}
