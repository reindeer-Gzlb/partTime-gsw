package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * gameinfo对象 game_info
 *
 * @author ruoyi
 * @date 2023-04-08
 */
@Data
public class GameInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 游戏名称
     */
    @Excel(name = "游戏名称")
    private String name;

    /**
     * 游戏类型（0手游 1端游）
     */
    @Excel(name = "游戏类型", readConverterExp = "0=手游,1=端游")
    private Integer type;

    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Integer sort;

    /**
     * 游戏图标
     */
    @Excel(name = "游戏图标")
    private String icon;

    /**
     * 状态 0、正常  1、停用
     */
    @Excel(name = "状态 0、正常  1、停用")
    private Integer state;

    /**
     * 删除标志 0、未删除  1、已删除
     */
    @Excel(name = "删除标志 0、未删除  1、已删除")
    private Integer del;

    /**
     * 是否为系统内置（0是 1否）
     */
    @Excel(name = "是否为系统内置", readConverterExp = "0=是,1=否")
    private Integer isSys;

    @TableField(exist = false)
    private Integer count;


    /**
     * 运营商类型  game_carrier表的id  1 腾讯 2网易  3米哈游 4其他
     */
    @Excel(name = "运营商类型")
    private Integer carrierType;

}
