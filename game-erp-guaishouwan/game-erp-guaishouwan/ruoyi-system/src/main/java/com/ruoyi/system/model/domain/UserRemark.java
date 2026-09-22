package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户备注名字表(user_remark)表实体类
 *
 * @author zyy
 * @date 2023-08-23 17:32:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_remark")
public class UserRemark {

    @TableId(type = IdType.AUTO)
    private Long id;


    private Long createId;


    private Long updateId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注用户id
     */
    private Long fromUserId;

    /**
     * 备注环信用户id
     */
    private String fromHxId;

    /**
     * 被备注用户id
     */
    private String toUserId;

    /**
     * 被备注环信用户id
     */
    private String toHxId;
    /**
     * 备注类型 1用户 2群组
     */
    private Integer type;
    /**
     * 备注名称
     */
    private String remarkName;


}
