package com.hkust.security;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.entity.Role;
import com.hkust.entity.RolePerm;
import com.hkust.mapper.RoleMapper;
import com.hkust.mapper.RolePermMapper;
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
import java.util.stream.Collectors;

@Slf4j
public class HkustSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private RoleMapper roleMapper;

    private RolePermMapper rolePermMapper;

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public HkustSecurityMetadataSource() {
    }

    @PostConstruct
    private void loadResourceDefine() {
        if (resourceMap != null) {
            return;
        }
        resourceMap = new HashMap<>();
        String channel = System.getProperty("channel");
        log.info("channel:{}", channel);
        // 查询权限明细
        QueryWrapper<RolePerm> wrapper = new QueryWrapper();
        wrapper.eq("channel", channel);
        List<RolePerm> rolePerms = rolePermMapper.selectList(wrapper);
        if (CollUtil.isEmpty(rolePerms)) {
            throw new NullPointerException("permission list is null");
        }
        // 查询角色列表
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        List<Role> roles = roleMapper.selectList(roleQueryWrapper);
        if (CollUtil.isEmpty(roles)) {
            throw new NullPointerException("role list is null");
        }
        Map<Integer, String> roleMap = roles.stream().collect(Collectors.toMap(Role::getRoleId, Role::getRoleName));
        Map<String, List<RolePerm>> groupByRolePermMap = rolePerms.stream().collect(Collectors.groupingBy(RolePerm::getPermUrl));
        for (Map.Entry<String, List<RolePerm>> entry : groupByRolePermMap.entrySet()) {
            String keyUrl = entry.getKey();
            List<RolePerm> rolePermList = entry.getValue();
            Collection<ConfigAttribute> configAttributes = new HashSet<>();
            for (RolePerm rolePerm : rolePermList) {
                ConfigAttribute configAttribute = new SecurityConfig(roleMap.get(rolePerm.getRoleId()));
                configAttributes.add(configAttribute);
            }
            resourceMap.put(keyUrl, configAttributes);
        }
        log.info("init role permissions......");
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            String url = entry.getKey();
            Collection<ConfigAttribute> roleList = entry.getValue();
            log.info("url:{}, role_list:{}", url, roleList);
        }
        log.info("completed role permissions......");
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
                uri.contains("/v3/api-docs") ||
                uri.startsWith("/favicon.ico") ||
                uri.startsWith("/webjars/**")) {
            return new HashSet<>();
        }
        Collection<ConfigAttribute> attributesForUrl = getAttributesForUrl(uri);
        return attributesForUrl;
    }

    private Collection<ConfigAttribute> getAttributesForUrl(String uri) {
        Set<Map.Entry<String, Collection<ConfigAttribute>>> entrySet = resourceMap.entrySet();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : entrySet) {
            if (entry.getKey().equals(uri)) {
                return entry.getValue();
            }
        }
        throw new AccessDeniedException("No matching ConfigAttribute found for " + uri);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        List<Role> roleList = roleMapper.selectList(roleQueryWrapper);
        List<String> roleNameList = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        String[] roles = roleNameList.stream().toArray(String[]::new);
        return SecurityConfig.createList(roles);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setRolePrmsMapper(RolePermMapper rolePermMapper) {
        this.rolePermMapper = rolePermMapper;
    }
}
