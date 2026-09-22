package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 消息历史记录对象 msg_history
 *
 * @author ruoyi
 * @date 2023-06-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgHistory extends BaseEntity {
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
     * 发送方
     */
    @Excel(name = "发送方")
    private String formUser;

    /**
     * 发送方用户id
     */
    @Excel(name = "发送方用户id")
    private Long formUserId;

    /**
     * 接收方用户id
     */
    @Excel(name = "接收方用户id")
    private Long toUserId;

    /**
     * 接收方用户
     */
    @Excel(name = "接收方用户")
    private String toUser;
    /**
     * 接收群id
     */
    @Excel(name = "接收方用户")
    private String toGroup;

    /**
     * text 文本消息 custom 自定义消息
     */
    @Excel(name = "text 文本消息 custom 自定义消息")
    private String type;

    /**
     * 自定义类型
     */
    @Excel(name = "自定义类型")
    private String customType;

    /**
     * 消息内容
     */
    @Excel(name = "消息内容")
    private String content;

    /**
     * 环信信息id
     */
    private String messageId;

    /**
     * 会话id
     */
    private String sessionId;

    private List<String> hxGroupIds;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setFormUser(String formUser) {
        this.formUser = formUser;
    }

    public String getFormUser() {
        return formUser;
    }

    public void setFormUserId(Long formUserId) {
        this.formUserId = formUserId;
    }

    public Long getFormUserId() {
        return formUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public String getCustomType() {
        return customType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createId", getCreateId())
                .append("updateId", getUpdateId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("formUser", getFormUser())
                .append("formUserId", getFormUserId())
                .append("toUserId", getToUserId())
                .append("toUser", getToUser())
                .append("type", getType())
                .append("customType", getCustomType())
                .append("content", getContent())
                .toString();
    }

    public String getToGroup() {
        return toGroup;
    }

    public void setToGroup(String toGroup) {
        this.toGroup = toGroup;
    }
}
