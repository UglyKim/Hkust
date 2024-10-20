package com.hkust.security.interceptor;

import com.hkust.security.HkustAccessDecisionManager;
import com.hkust.security.HkustSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Collection;

public class HkustFilterSecurityInterceptor extends FilterSecurityInterceptor {

    @Autowired
    private HkustSecurityMetadataSource hkustSecurityMetadataSource;

    @Autowired
    private HkustAccessDecisionManager hkustAccessDecisionManager;

    public HkustFilterSecurityInterceptor(FilterInvocationSecurityMetadataSource securityMetadataSource,
                                          AccessDecisionManager accessDecisionManager) {
        setSecurityMetadataSource(securityMetadataSource);
        setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {
        // 根据具体逻辑绕过权限检查
        if (shouldBypassSecurity(object)) {
            return null;  // 跳过权限检查
        }
        return super.beforeInvocation(object);  // 否则执行默认权限检查
    }

    private boolean shouldBypassSecurity(Object object) {
        // 自定义跳过条件，比如根据请求 URL、用户角色等
        return true;  // 在此处返回 true 表示跳过权限检查
    }

    /*@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        this.invoke(filterInvocation);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        // Retrieve security metadata source
        FilterInvocationSecurityMetadataSource metadataSource = this.hkustSecurityMetadataSource;
//        HkustSecurityMetadataSource metadataSource = new HkustSecurityMetadataSource();
        Collection<ConfigAttribute> attributes = metadataSource.getAttributes(filterInvocation);

        // AccessDecisionManager decision
        AccessDecisionManager accessDecisionManager = this.hkustAccessDecisionManager;
//        HkustAccessDecisionManager accessDecisionManager = new HkustAccessDecisionManager();
        accessDecisionManager.decide(SecurityContextHolder.getContext().getAuthentication(),
                filterInvocation.getRequest(),
                attributes);

        // Continue with the filter chain
        filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
    }*/
}