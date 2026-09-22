package com.ruoyi.system.logo.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * logo对象 sys_shop_logo
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
@Data
public class SysShopLogo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createId;

    /** logo */
    @Excel(name = "logo")
    private String logoImg;

    /** bannerImg */
    @Excel(name = "bannerImg")
    private String bannerImg;

}
