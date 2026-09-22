package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 【请填写功能名称】对象 api_task_pool
 *
 * @author ruoyi
 * @date 2022-09-05
 */
@Data
public class ApiTaskPool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;
    private Long createNoId;
    private List<Long> createIds;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 供货人id */
    @Excel(name = "供货人id")
    private Long superId;

    private String superName;

    /** 选择的游戏 */
    @Excel(name = "选择的游戏")
    private String games;

    /** 选择的接口 */
    @Excel(name = "选择的接口")
    private String config;

    //add edit del
    private String type;

    /**
     * 执行次数
     */
    private int countt;

    private int maxCount;


    //0、已运行 1、待运行
    private Long status;

    private String sttatusName;

    private Long page;
    private Long size;
    private String partnerName;
    private Long accountId;
    private String req;
    private String res;
    private String errmsg;
    private String partCode;
    private String notPartCode;

}
