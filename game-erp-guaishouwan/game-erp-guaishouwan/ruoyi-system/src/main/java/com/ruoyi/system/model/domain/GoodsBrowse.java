package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zyy
 * @date 2023-05-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "goods_browse")
public class GoodsBrowse {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 游戏id
     */
    private Long gameId;

    /**
     * 游戏名称
     */
    private String gameName;

    /**
     * 浏览人id
     */
    private Long browseUserId;

    /**
     * 求和字段
     */
    @TableField(value = "count(*)", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Integer count;
}
