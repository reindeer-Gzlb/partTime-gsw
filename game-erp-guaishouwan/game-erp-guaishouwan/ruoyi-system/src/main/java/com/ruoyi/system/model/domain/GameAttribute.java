package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 游戏配置对象 game_attribute
 * 
 * @author ruoyi
 * @date 2023-04-08
 */
@Data
public class GameAttribute extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 游戏id */
    @Excel(name = "游戏id")
    private Long gameId;

    /** 游戏名称 */
    @Excel(name = "游戏名称")
    private String gameName;

    /** 系统内置key */
    @Excel(name = "系统内置key")
    private String systemKey;

    /** 字段名称 */
    @Excel(name = "字段名称")
    private String fieldName;

    /** 图标 */
    @Excel(name = "图标")
    private String imgUrl;
    private String sid;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 父id */
    @Excel(name = "父id")
    private Long parentId;

    /**
     * 回收页面是否显示 1显示 2不显示
     */
    private String hsisShow;

    String acode;

    List<GameAttribute> childGameAttributes;
}
