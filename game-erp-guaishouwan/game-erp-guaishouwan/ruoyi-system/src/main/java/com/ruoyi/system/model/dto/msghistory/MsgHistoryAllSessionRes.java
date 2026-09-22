package com.ruoyi.system.model.dto.msghistory;

import lombok.Data;

/**
 * @author zyy
 * @date 2023-08-11
 */
@Data
public class MsgHistoryAllSessionRes {

    private String formUser;

    private String toUser;

    private String sessionId;

    private String toGroup;

    private String records;


}
