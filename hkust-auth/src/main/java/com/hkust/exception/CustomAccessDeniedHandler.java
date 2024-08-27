package com.hkust.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应内容类型为JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 返回401 Unauthorized状态码
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // 创建自定义的错误响应
        String errorResponse = "{ \"error\": \"Unauthorized\", \"message\": \"无权限访问资源\" }";
        response.getWriter().write(errorResponse);
    }
}
