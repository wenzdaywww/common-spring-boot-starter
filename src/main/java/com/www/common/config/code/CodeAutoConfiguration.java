package com.www.common.config.code;

import com.www.common.config.code.core.CodeDataTask;
import com.www.common.config.code.core.CodeDictRunnerImpl;
import com.www.common.config.code.core.CodeRedisHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>@Description 数据字典自动配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/22 20:42 </p>
 */
@Configuration
@EnableConfigurationProperties(value = CodeProperties.class)
@ConditionalOnProperty( prefix = "com.www.common.code", name = "enable", havingValue = "true")
public class CodeAutoConfiguration {
    /**
     * <p>@Description 注册数据字典redis操作类对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 20:45 </p>
     * @return com.www.common.config.code.CodeRedisHandler
     */
    @Bean
    public CodeRedisHandler codeRedisHandler(){
        return new CodeRedisHandler();
    }
    /**
     * <p>@Description 注册数据字典加载对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 20:51 </p>
     * @param codeRedisHandler 据字典redis操作类对象
     * @return com.www.common.config.code.init.CodeDictRunner
     */
    @Bean
    public CodeDictRunnerImpl codeDictRunner(@Qualifier("codeRedisHandler") CodeRedisHandler codeRedisHandler){
        return new CodeDictRunnerImpl(codeRedisHandler);
    }
    /**
     * <p>@Description 注册定时加载字典对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 20:51 </p>
     * @param codeDictRunner 数据字典加载对象
     * @return com.www.common.config.code.task.CodeDataTask
     */
    @Bean
    public CodeDataTask codeDataTask(@Qualifier("codeDictRunner") CodeDictRunnerImpl codeDictRunner){
        return new CodeDataTask(codeDictRunner);
    }

}