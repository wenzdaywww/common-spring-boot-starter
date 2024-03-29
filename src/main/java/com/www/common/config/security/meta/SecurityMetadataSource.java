package com.www.common.config.security.meta;

import com.www.common.config.security.dto.AuthorityDTO;
import com.www.common.config.security.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>@Description 安全元数据源配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/24 18:22 </p>
 */
@Slf4j
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    /** 路径匹配 **/
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * <p>@Description  </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 18:14 </p>
     * @return
     */
    public SecurityMetadataSource(){
        log.info("启动加载>>>Security认证自动配置>>>配置访问URL验证");
    }
    /**
     * <p>@Description 访问权限角色配置 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 21:38 </p>
     * @param o
     * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestURL = ((FilterInvocation)o).getRequestUrl();
        List<AuthorityDTO> roleMenuList = userDetailsService.findAllAuthority();
        if(CollectionUtils.isNotEmpty(roleMenuList)){
            List<String> roleList = new ArrayList<>();
            for (AuthorityDTO dto : roleMenuList){
                if(antPathMatcher.match(dto.getUrl(),requestURL) && StringUtils.isNotBlank(dto.getRole())){
                    roleList.add(dto.getRole());
                }
            }
            String[] roleArr = roleList.toArray(new String[roleList.size()]);
            log.info("3、访问URL验证通过,url={}",requestURL);
            return SecurityConfig.createList(roleArr);
        }
        log.info("3、访问URL验证不通过,url={}",requestURL);
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
