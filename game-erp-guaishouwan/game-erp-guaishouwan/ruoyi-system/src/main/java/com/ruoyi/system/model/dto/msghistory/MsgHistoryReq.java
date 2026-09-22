package com.ruoyi.system.model.dto.msghistory;

import lombok.Data;

/**
 * @author zyy
 * @date 2023-08-10
 */
@Data
public class MsgHistoryReq {

    private Long id;
    /**
     * 类型 1查上  2查下  3查上下
     */
    private Integer type;

    /**
     * 会话id
     */
    private String sessionId;

    /**
     * 查多少条
     */
    private Integer limit = 10;
}
