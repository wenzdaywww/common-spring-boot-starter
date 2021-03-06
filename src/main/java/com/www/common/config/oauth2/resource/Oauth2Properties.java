package com.www.common.config.oauth2.resource;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>@Description oauth2资源方认证配置属性 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/22 22:19 </p>
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "com.www.common.oauth2")
public class Oauth2Properties {
    /** 是否开启oauth2资源方认证配置 **/
    private Boolean enable = false;
    /** jwt令牌签名 **/
    private String signingKey = "wenzday";
    /** 资源服务id **/
    @Value("${spring.application.name}")
    private String resourceId;
    /** 图片资源路径 **/
    @Value("${com.www.common.file.img-url-path:unknown}")
    private String imgUrlPath;
}
