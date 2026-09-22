package com.ruoyi.system.V2.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.model.domain.SessionList;
import com.ruoyi.system.model.dto.msghistory.SessionListRes;

import java.util.List;

/**
 * 会话列表(session_list)表数据库访问层
 *
 * @author zyy
 * @date 2023-08-24 15:42:49
 */
public interface SessionListMapper extends BaseMapper<SessionList> {

    List<SessionListRes> getSessionList(SessionList sessionList);

    List<SessionListRes> getSessionListTop(SessionList sessionList);

}

