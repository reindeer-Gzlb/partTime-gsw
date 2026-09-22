package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 游戏自定义属性对应模板对象 game_attr_template
 *
 * @author ruoyi
 * @date 2023-04-04
 */
@Data
public class GameAttrTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private Long createId;

    /**
     * 修改人id
     */
    @Excel(name = "修改人id")
    private Long updateId;

    /**
     * 游戏id
     */
    @Excel(name = "游戏id")
    private Long gameId;

    /**
     * 游戏名称
     */
    @Excel(name = "游戏名称")
    private String gameName;

    /**
     * 定义好的字段名称
     */
    @Excel(name = "定义好的字段名称")
    private String aavName;

    /**
     * 字段key
     */
    @Excel(name = "字段key")
    private String fieldKey;

    /**
     * 字段类型
     */
    @Excel(name = "字段类型")
    private String fieldType;

    /**
     * 字段名称
     */
    @Excel(name = "字段名称")
    private String fieldName;


    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Integer sort;

    /**
     * 是否必填  true必填  false非必填
     */
    @Excel(name = "是否必填")
    private String must;

    /**
     * 是否启用  0启用 1不启用
     */
    @Excel(name = "是否启用")
    private String isEnable;

    /**
     * 取值范围
     */
    @Excel(name = "取值范围")
    private String range;

    /**
     * attribute_id
     */
    @Excel(name = "attribute_id")
    private Long attributeId;
    /**
     * 是否搜索条件 1是 2否
     */
    private String isSearch;

    /**
     * 回收页面是否显示 1显示 2不显示
     */
    private String hsisShow;
    
    private List<GameAttribute> childAttributes;

}
