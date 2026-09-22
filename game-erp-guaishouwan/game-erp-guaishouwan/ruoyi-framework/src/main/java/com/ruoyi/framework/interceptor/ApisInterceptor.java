package com.ruoyi.framework.interceptor;

import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DESUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.UserKey;
import com.ruoyi.system.model.service.IUserKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/26 15:32
 */
@Slf4j
@Component
public class ApisInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserKeyService userKeyService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String author = request.getHeader(Constant.author);
        log.info("authorId = {}",author);
        UserKey userKey = userKeyService.getAuthor(author);
        if (StringUtils.isNotEmpty(author) && Objects.nonNull(userKey)) {
            log.info("authorId = {}",userKey.getUserId());
            return true;
        } else {
           throw new ServiceException("token校验失败",403);
        }

    }
}
