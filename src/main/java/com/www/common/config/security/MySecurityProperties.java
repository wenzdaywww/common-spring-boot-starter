package com.www.common.config.security;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>@Description Security认证配置属性 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/23 09:16 </p>
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "com.www.common.securuty")
public class MySecurityProperties {
    /** 是否开启Security认证，默认关闭false **/
    private Boolean enable = false;
    /** jwt令牌签名，默认wenzday */
    private String secretKey = "wenzday";
    /** 使用redis保存用户的token的key前缀,不需冒号结尾，代码已添加 **/
    private String tokenPrefix;
    /**  用户token过期时间（单位小时）,默认48小时 */
    private int tokenExpireHour = 48;
    /** 使用redis保存用户的角色信息的key前缀,不需冒号结尾，代码已添加 **/
    private String userPrefix;
    /** 保存用户的角色信息的redis的key的过期时间（单位小时）,默认48小时 **/
    private long userExpireHour = 48L;
    /** 角色访问请求权限信息的redis的key，不为空则缓存到redis中 **/
    private String authRedisKey;
    /** 角色访问请求权限信息的redis的key的过期时间（单位小时）,默认48小时 **/
    private long authExpireHour = 48L;
    /** 登录的http请求地址，即表单form中action的地址，默认/login **/
    private String login = "/login";
    /** 登录的http请求的用户名key，即表单form中action的参数key，默认id **/
    private String name = "id";
    /** 登录的http请求的密码key，即表单form中action的参数key，默认pwd **/
    private String password = "pwd";
    /** 退出登录的http请求地址，默认/logout **/
    private String logout = "/logout";
}
