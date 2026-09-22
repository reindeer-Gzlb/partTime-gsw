package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品图片附对象 goods_info_img
 * 
 * @author ruoyi
 * @date 2023-04-10
 */
@Data
public class GoodsInfoImg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** 商品id */
    @Excel(name = "商品id")
    private Long goodsId;

    /** 1、图片 2、视频 */
    @Excel(name = "1、图片 2、视频")
    private String type;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imgUrl;

    /** 文件原名 */
    @Excel(name = "文件原名")
    private String fileName;

}
