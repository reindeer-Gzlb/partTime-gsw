package com.ruoyi.system.outto.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lz
 * @version 1.0
 * @date 2022/7/26 10:23
 */
@Data
public class YDDto {

    /** 账号 */
    private String accountId;
    private Long id;
    /**
     * 营地ID
     */
    private String campId;

    /**
     * 标题图片
     */
    private String titleImg;

    /** 游戏类型 */
    private String type;


    /** 游戏大区 */
    private String regional;

    /** 段位 */
    private String level;

    /** 英雄数量 */
    private Integer heroes;

    /** 皮肤数量 */
    private Integer skins;

    /** 套装 */
    private Integer suit;

    /** 枪皮 */
    private Integer guns;

    /** 讲解 */
    private String des;

    /** 价格 */
    private BigDecimal price;

    private BigDecimal newPrice;


    /** 提取账号 */
    private String extractAccount;


    /** 自编号 */
    private String sinNum;

    /** 游戏名称 */
    private String gameName;

    private String orderNo;

    private String uuid;

    private String imgList;



}
