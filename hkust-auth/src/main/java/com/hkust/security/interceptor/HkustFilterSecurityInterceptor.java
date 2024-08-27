package com.hkust.security.interceptor;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class HkustFilterSecurityInterceptor extends FilterSecurityInterceptor {

    public HkustFilterSecurityInterceptor(FilterInvocationSecurityMetadataSource securityMetadataSource,
                                          AccessDecisionManager accessDecisionManager) {
        setSecurityMetadataSource(securityMetadataSource);
        setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public InterceptorStatusToken beforeInvocation(Object object) {
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequestUrl();

        // 如果是 Swagger 的请求，跳过安全拦截
        if (requestUrl.startsWith("/swagger-ui.html") ||
                requestUrl.startsWith("/swagger-ui/index.html") ||
                requestUrl.startsWith("/swagger-resources") ||
                requestUrl.startsWith("/v2/api-docs") ||
                requestUrl.startsWith("/favicon.ico") ||
                requestUrl.startsWith("/webjars")) {
            return null;
        }
        // 对其他请求执行正常的安全拦截
        return super.beforeInvocation(object);
    }
}