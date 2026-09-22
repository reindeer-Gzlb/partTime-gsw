package com.ruoyi.system.V2.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.model.domain.SessionList;

/**
 * 会话列表(session_list)表服务接口
 *
 * @author zyy
 * @date 2023-08-24 15:42:49
 */
public interface ISessionListService extends IService<SessionList> {
    boolean topOrCancel(SessionList sessionList);
}

