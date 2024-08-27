package com.hkust.security;

import cn.hutool.json.JSONUtil;
import com.hkust.entity.Permission;
import com.hkust.entity.Role;
import com.hkust.entity.UrlPermissions;
import com.hkust.exception.CustomAccessDeniedHandler;
import com.hkust.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
public class HkustSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private RoleMapper roleMapper;

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public HkustSecurityMetadataSource() {
    }

    @PostConstruct
    private void loadResourceDefine() {

        if (resourceMap != null) {
            return;
        }
        resourceMap = new HashMap<>();
        List<Role> roles = roleMapper.selectAll();
        Collection<ConfigAttribute> configAttributes = new HashSet<>();
        for (Role role : roles) {
            ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
            log.info("roles:{}", JSONUtil.toJsonPrettyStr(roles));
            List<Permission> permissionList = role.getPermissionList();
            log.info("permissionList:{}", JSONUtil.toJsonPrettyStr(permissionList));
            for (Permission permission : permissionList) {
                List<UrlPermissions> urlPermissionList = permission.getUrlPermissionList();
                log.info("urlPermissionList:{}", JSONUtil.toJsonPrettyStr(urlPermissionList));
                for (UrlPermissions urlPermissions : urlPermissionList) {
                    configAttributes.add(configAttribute);
                    resourceMap.put(urlPermissions.getUrlPattern(), configAttributes);
                }
            }
        }
        log.info("size:{}", configAttributes.size());
        for (ConfigAttribute configAttribute : configAttributes) {
            log.info("role is:{}", configAttribute.getAttribute());
        }
        log.info("configAttributes:{}", JSONUtil.toJsonPrettyStr(configAttributes));
        log.info("permissions:{}", JSONUtil.toJsonPrettyStr(resourceMap));
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String uri = request.getRequestURI();
        log.info("access uri:{}", uri);
        // Implement your logic to retrieve security metadata based on the request
        if (uri.startsWith("/swagger-ui.html") ||
                uri.equals("/v1/auth/login") ||
                uri.contains("swagger") ||
                uri.startsWith("/v2/api-docs") ||
                uri.contains("/v3/api-docs") ||
                uri.startsWith("/favicon.ico") ||
                uri.startsWith("/webjars/**")) {
            return new HashSet<>();
        }
        Collection<ConfigAttribute> attributesForUrl = getAttributesForUrl(uri);
        return attributesForUrl;
    }

    private Collection<ConfigAttribute> getAttributesForUrl(String uri) {
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            if (entry.getKey().equals(uri)) {
                return entry.getValue();
            }
        }
        throw new AccessDeniedException("No matching ConfigAttribute found for " + uri);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return SecurityConfig.createList("admin", "student");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
}
