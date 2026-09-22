package com.ruoyi.system.model.domain;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 密钥对象 user_key
 *
 * @author ruoyi
 * @date 2022-05-26
 */
@Data
public class UserKey extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long createId;

    /** 供应商id */
    @Excel(name = "供应商id")
    private Long userId;

    /** 密钥 */
    @Excel(name = "密钥")
    private String key;

    /** 加密字符串 */
    @Excel(name = "加密字符串")
    private String encryption;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplyName;
    private String spell;

    private Integer rise;
}
