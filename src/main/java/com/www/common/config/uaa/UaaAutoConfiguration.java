package com.www.common.config.uaa;

import com.www.common.config.uaa.handler.Oauth2LoginFailureHandler;
import com.www.common.config.uaa.handler.Oauth2LogoutSuccessHandler;
import com.www.common.config.uaa.handler.UserServiceHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>@Description 单点登录认证服务方自动配置类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/23 09:15 </p>
 */
@Slf4j
@Configuration
@MapperScan("com.www.common.config.uaa")//配置mapper扫描路径
@ComponentScan("com.www.common.config.uaa.controller")//配置controller扫描路径
@EnableConfigurationProperties(value = UaaProperties.class)
@ConditionalOnProperty( prefix = "com.www.common.uaa", name = "enable", havingValue = "true")
public class UaaAutoConfiguration implements WebMvcConfigurer {
    @Autowired
    private UaaProperties uaaProperties;

    /**
     * <p>@Description 设置视图控制器 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:00 </p>
     * @param registry 视图控制器
     * @return void
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("启动加载>>>单点登录认证服务方自动配置>>>加载视图控制器");
        registry.addViewController(uaaProperties.getLoginUrl()).setViewName(uaaProperties.getLoginPage());//自定义登录页面跳转
    }
    /**
     * <p>@Description 注册oauth2登录认证失败处理对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2023/4/10 21:50 </p>
     * @return
     */
    @Bean
    public Oauth2LoginFailureHandler oauth2LoginFailureHandler(){
        return new Oauth2LoginFailureHandler();
    }
    /**
     * <p>@Description 注册退出成功的处理对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2023/4/10 21:50 </p>
     * @return
     */
    @Bean
    public Oauth2LogoutSuccessHandler oauth2LogoutSuccessHandler(){
        return new Oauth2LogoutSuccessHandler();
    }
    /**
     * <p>@Description 注册oauth2用户详细信息服务类对象 </p>
     * <p>@Author www </p>
     * <p>@Date 2023/4/10 21:50 </p>
     * @return
     */
    @Bean
    public UserServiceHandler userServiceHandler(){
        return new UserServiceHandler();
    }
}
