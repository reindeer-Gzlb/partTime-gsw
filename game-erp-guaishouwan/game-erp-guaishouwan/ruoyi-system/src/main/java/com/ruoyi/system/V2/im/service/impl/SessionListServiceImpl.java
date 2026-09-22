package com.ruoyi.system.V2.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.im.mapper.SessionListMapper;
import com.ruoyi.system.V2.im.service.ISessionListService;
import com.ruoyi.system.model.domain.SessionList;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 会话列表(session_list)表服务实现类
 *
 * @author zyy
 * @date 2023-08-24 15:42:49
 */
@Service("sessionListService")
public class SessionListServiceImpl extends ServiceImpl<SessionListMapper, SessionList> implements ISessionListService {

    @Override
    public boolean topOrCancel(SessionList sessionList) {
        SessionList sessionList1 = getOne(new LambdaQueryWrapper<SessionList>().eq(SessionList::getSessionId,
                sessionList.getSessionId()));
        if (StringUtils.isNull(sessionList1)) {
            throw new GlobalException("该会话Id:" + sessionList.getSessionId() + "未找到");
        }
        if (Objects.nonNull(sessionList.getTopType())) {
            String topHxIds = sessionList1.getTopHxIds();
            if (StringUtils.isNotEmpty(topHxIds)) {
                if (sessionList.getTopType() == 1) {
                    if (topHxIds.indexOf(sessionList.getFormUser()) < 0) {
                        if (StringUtils.isEmpty(topHxIds)) {
                            topHxIds = sessionList.getFormUser();
                        } else {
                            topHxIds += "," + sessionList.getFormUser();
                        }
                    }
                } else if (sessionList.getTopType() == 2) {
                    if (StringUtils.equals(topHxIds, sessionList.getFormUser())) {
                        topHxIds = "";
                    } else if (topHxIds.indexOf("," + sessionList.getFormUser()) >= 0) {
                        topHxIds = topHxIds.replace("," + sessionList.getFormUser(), "");
                    } else if (topHxIds.indexOf(sessionList.getFormUser() + ",") >= 0) {
                        topHxIds = topHxIds.replace(sessionList.getFormUser() + ",", "");
                    }
                }
            } else {
                topHxIds = sessionList.getFormUser();
            }
            sessionList1.setTopHxIds(topHxIds);
        }
        return updateById(sessionList1);
    }

    public static void main(String[] args) {
        String aa = ",fh1691120051713";
        System.out.println(aa.indexOf(","));
    }
}

