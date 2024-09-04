package com.hkust.security.interceptor;

import com.hkust.security.HkustAccessDecisionManager;
import com.hkust.security.HkustSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
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
    }
}