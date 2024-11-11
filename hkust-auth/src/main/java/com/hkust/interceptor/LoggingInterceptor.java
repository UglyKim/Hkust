package com.hkust.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        StringBuilder params = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.append(paramName).append("=").append(paramValue).append("&");
        }
        // 删除最后一个 "&" 符号
        if (params.length() > 0) {
            params.setLength(params.length() - 1);
        }

        log.info("Received request: {} {} with query params: [{}]",
                request.getMethod(),
                request.getRequestURI(),
                params);
        return true;
    }
}
