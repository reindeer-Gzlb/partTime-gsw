package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "supply_info")
public class SupplyInfo extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    /**
     * 供货商名称
     */
    private String name;
    /**
     * 应用唯一标识
     */
    private String appId;
    /**
     * 公钥
     */
    private String appKey;
    /**
     * 私钥
     */
    private String appSecret;
    /**
     * 是否可用 0正常 1禁用
     */
    private Integer isEnble;

    /**
     * 最大商品数量(只算上架状态)
     */
    private Integer goodsMaxnum;
}
