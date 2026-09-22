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
 * @author zyy
 * @date 2023-06-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_message")
public class SysMessage {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 关联id
     */
    private Long relatedDataId;

    /**
     * 发送人id
     */
    private Long senderUserId;

    /**
     * 发送人姓名
     */
    private String senderUserName;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /**
     * 消息类型 1：商品审核通过 2：商品审核失败 3：商品交易成功(卖方) 4：商品交易成功(买方)
     */
    private Integer sendType;

    /**
     * 消息类型名称
     */
    private String sendTypeName;

    /**
     * 发送标题
     */
    private String sendTitle;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 接收人id
     */
    private Long receiveUserId;

    /**
     * 接收人姓名
     */
    private String receiveUserName;

    /**
     * 阅读状态 1：未读 2：已读
     */
    private Integer readState;

    /**
     * 阅读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date readTime;

}
