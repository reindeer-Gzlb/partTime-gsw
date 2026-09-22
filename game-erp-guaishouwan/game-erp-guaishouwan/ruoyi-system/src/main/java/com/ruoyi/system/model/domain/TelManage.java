package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 手机管理对象 tel_manage
 * 
 * @author ruoyi
 * @date 2023-04-05
 */
@Data
public class TelManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 类型 */
    @Excel(name = "类型")
    private String type;
    private String custodian;

    /** 编号 */
    @Excel(name = "编号")
    private String num;

    /** 手机号 */
    @Excel(name = "手机号")
    private String tel;

    /** 绑定游戏 */
    @Excel(name = "绑定游戏")
    private Long game;
    private String gameStr;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** info_id */
    @Excel(name = "info_id")
    private Long infoId;


    /**
     * 腾讯
     */
    private Integer txCount = 0;
    private List<HashMap> txContent= new ArrayList<>();
    /**
     * 网易
     */
    private Integer wyCount =0;
    private List<HashMap> wyContent= new ArrayList<>();
    /**
     * 米哈游
     */
    private Integer mhyCount =0;
    private List<HashMap> mhyContent= new ArrayList<>();
    /**
     * 其他
     */
    private Integer qtCount = 0;
    private List<HashMap> qtContent = new ArrayList<>();
}
