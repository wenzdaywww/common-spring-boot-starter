package com.www.common.config.transaction;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * <p>@Description 全局事物管理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2023/3/12 21:20 </p>
 */
@Slf4j
@Aspect
@Configuration
@EnableConfigurationProperties(value = TransactionProperties.class)
@ConditionalOnProperty( prefix = "com.www.common.transaction", name = "enable", havingValue = "true")
public class TransactionAdviceConfiguation {
    @Autowired
    private TransactionManager transactionManager;
    @Autowired
    private TransactionProperties transactionProperties;

    /**
     * <p>@Description 设置事物传播级别 </p>
     * <p>@Author www </p>
     * <p>@Date 2023/3/12 21:21 </p>
     * @return
     */
    @Bean
    public TransactionInterceptor txAdvice() {
        log.info("启动加载>>>配置全局事物管理");
        DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_REQUIRED_READONLY.setReadOnly(true);
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*", txAttr_REQUIRED);
        source.addTransactionalMethod("save*", txAttr_REQUIRED);
        source.addTransactionalMethod("create*", txAttr_REQUIRED);
        source.addTransactionalMethod("delete*", txAttr_REQUIRED);
        source.addTransactionalMethod("update*", txAttr_REQUIRED);
        return new TransactionInterceptor(transactionManager, source);
    }
    /**
     * <p>@Description 添加事物管理器 </p>
     * <p>@Author www </p>
     * <p>@Date 2023/3/12 21:21 </p>
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(transactionProperties.getAopPointcut());
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}