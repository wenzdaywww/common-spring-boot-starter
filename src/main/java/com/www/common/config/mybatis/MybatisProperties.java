package com.www.common.config.mybatis;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>@Description 自定义Mybatis配置属性 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/26 20:23 </p>
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "com.www.common.mybatis")
public class MybatisProperties {
    /** 是否开启全局结果集数量限制，默认开启true **/
    private Boolean limit = true;
    /** 数据库类型，默认mysql **/
    private String database = "mysql";
    /** 结果集限制数量，默认10000，使用@RowLimitInterceptor注解则限制数量失效 **/
    private int limitNum = 10000;
}
