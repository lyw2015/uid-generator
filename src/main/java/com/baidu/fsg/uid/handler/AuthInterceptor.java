package com.baidu.fsg.uid.handler;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName uid-generator
 * @Author Laiyw
 * @CreateTime 2022/5/6 13:00
 * @Description TODO
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${uid-generator.token}")
    private String token;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        if (this.token.equals(token)) {
            return true;
        }
        response.setStatus(401);
        response.setContentType("Application/json;charset=utf-8");
        response.getWriter().write("{code: 401, msg: '无效Token'}");
        return false;
    }
}
