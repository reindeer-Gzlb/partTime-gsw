package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 【请填写功能名称】对象 zhubo
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Data
public class Zhubo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String img;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String h5Img;
    /**
     * 名臣
     */
    @Excel(name = "名臣")
    private String name;
    private String type;
    /**
     * pc端跳转地址
     */
    private String pcJump;
    /**
     * 移动端跳转地址
     */
    private String mobileJump;

    /**
     * 内容
     */
    private String content;

}
