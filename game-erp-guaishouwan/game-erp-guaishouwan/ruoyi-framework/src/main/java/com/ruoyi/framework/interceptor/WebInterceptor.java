package com.ruoyi.framework.interceptor;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.service.IUserKeyService;
import com.ruoyi.system.webReq.domain.WebReqHistory;
import com.ruoyi.system.webReq.service.IWebReqHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/26 15:32
 */
@Slf4j
@Component
public class WebInterceptor implements HandlerInterceptor {

    @Autowired
    private IWebReqHistoryService webReqHistoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String pagePath = ServletUtils.getRequest().getRequestURI();// 页面路径
        String resource = request.getHeader("resource");// 来源  web   h5   app
        //  统计用户页面数据
        if (StringUtils.isNotEmpty(pagePath)) {
            WebReqHistory history = new WebReqHistory();
            history.setPagePath(pagePath);
            history.setResource(resource);
            history.setCreateTime(new Date());
            webReqHistoryService.insertWebReqHistory(history);
        }
        // 保存请求记录
        return true;
    }
}
