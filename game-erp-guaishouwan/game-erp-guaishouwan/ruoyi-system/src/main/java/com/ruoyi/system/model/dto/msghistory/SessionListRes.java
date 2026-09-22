package com.ruoyi.system.model.dto.msghistory;

import com.ruoyi.system.model.domain.SessionList;
import lombok.Data;

/**
 * @author zyy
 * @date 2023-08-24
 */
@Data
public class SessionListRes extends SessionList {

    /**
     * 发送人昵称
     */
    private String fromNickName;
    /**
     * 发送人头像
     */
    private String fromAvatar;

    /**
     * 接收人昵称
     */
    private String toNickName;

    /**
     * 接收人头像
     */
    private String toAvatar;

    /**
     * 群组名称
     */
    private String groupName;

    /**
     * 是否置顶
     */
    private String isTop;
}
