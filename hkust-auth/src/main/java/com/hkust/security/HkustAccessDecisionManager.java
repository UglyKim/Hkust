package com.hkust.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

@Slf4j
public class HkustAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        FilterInvocation fi = (FilterInvocation) object;
//        String url = fi.getRequestUrl();
//        String method = fi.getRequest().getMethod();
//        log.info("fi:{} url:{} method:{}", fi, url, method);
        if (configAttributes == null || configAttributes.isEmpty()) {
            return;
        }
        // 遍历权限并与用户角色匹配
        for (ConfigAttribute configAttribute : configAttributes) {
            String requiredRole = configAttribute.getAttribute();
            log.info("requiredRole:{}", requiredRole);
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (requiredRole.trim().equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("Access is denied");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}