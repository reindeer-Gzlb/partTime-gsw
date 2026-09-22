package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【请填写功能名称】对象 msg_group_info
 *
 * @author ruoyi
 * @date 2023-08-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgGroupInfo extends BaseEntity {
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
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /**
     * 群id
     */
    @Excel(name = "群id")
    private String toGroup;

    /**
     * 群名称
     */
    @Excel(name = "群名称")
    private String groupName;

    /**
     * 群详情
     */
    @Excel(name = "群详情")
    private String groupDesc;

    /**
     * 群成员id
     */
    @Excel(name = "群成员id")
    private String numbers;

    private String groupType;

    private String other;

    /**
     * 是否删除 1正常 2删除
     */
    private Integer del;

    /**
     * 群公告
     */
    private String announcement;
}
