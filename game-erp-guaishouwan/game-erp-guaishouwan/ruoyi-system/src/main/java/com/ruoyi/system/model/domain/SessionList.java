package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 会话列表(session_list)表实体类
 *
 * @author zyy
 * @date 2023-08-24 15:42:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("session_list")
public class SessionList {

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
     * 属于方
     */
    private String belongHxIds;

    /**
     * 属于方
     */
    private String topHxIds;

    /**
     * 发送方
     */
    private String formUser;

    /**
     * 发送方用户id
     */
    private Long formUserId;

    /**
     * 接收方用户id
     */
    private Long toUserId;

    /**
     * 接收方用户
     */
    private String toUser;

    /**
     * text 文本消息 custom 自定义消息
     */
    private String type;

    /**
     * 自定义类型
     */
    private String customType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 类型 1 用户 2 群消息
     */
    private String msgType;

    /**
     * 会话id
     */
    private String sessionId;

    /**
     * 环信信息id
     */
    private String messageId;

    /**
     * 是否删除 1正常 2删除
     */
    private Integer del;
    /**
     * 1置顶 2取消置顶
     */
    @TableField(exist = false)
    private Integer topType;

    /**
     * 查询最近几天的数据
     */
    @TableField(exist = false)
    private Integer day;
}
