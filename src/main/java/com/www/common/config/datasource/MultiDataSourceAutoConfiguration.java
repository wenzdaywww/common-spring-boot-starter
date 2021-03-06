package com.www.common.config.datasource;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.www.common.config.datasource.core.ReadWriteDataSourceProxy;
import com.www.common.config.datasource.core.ReadWriteInterceptor;
import com.www.common.config.datasource.sources.IReadDataSoure;
import com.www.common.config.datasource.sources.IWriteDataSoure;
import com.www.common.config.datasource.sources.impl.ReadOneDataSource;
import com.www.common.config.datasource.sources.impl.ReadTwoDataSource;
import com.www.common.config.datasource.sources.impl.WriteDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.aspectj.util.SoftHashMap;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * <p>@Description ??????????????????????????? </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/3/22 21:53 </p>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {MultiDataSourceProperties.class,MybatisPlusProperties.class})
@ConditionalOnProperty( prefix = "com.www.common.datasource", name = "enable", havingValue = "true")
public class MultiDataSourceAutoConfiguration extends MybatisPlusAutoConfiguration {
    /** ???????????????????????? **/
    public static final String WRITE_DATA_SOURCE_PREFIX = "writeDataSource_";
    /** ???????????????????????? **/
    public static final String READ_DATA_SOURCE_PREFIX = "readDataSource_";
    /** ???????????????????????? **/
    private static int writeNum = 0;
    /** ???????????????????????? **/
    private static int readNum = 0;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * <p>@Description ???????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 18:00 </p>
     * @param properties
     * @param interceptorsProvider
     * @param typeHandlersProvider
     * @param languageDriversProvider
     * @param resourceLoader
     * @param databaseIdProvider
     * @param configurationCustomizersProvider
     * @param mybatisPlusPropertiesCustomizerProvider
     * @param applicationContext
     */
    public MultiDataSourceAutoConfiguration(MybatisPlusProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ObjectProvider<TypeHandler[]> typeHandlersProvider, ObjectProvider<LanguageDriver[]> languageDriversProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider, ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider, ApplicationContext applicationContext) {
        super(properties, interceptorsProvider, typeHandlersProvider, languageDriversProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider, mybatisPlusPropertiesCustomizerProvider, applicationContext);
        log.info("??????????????????????????????????????????");
    }
    /**
     * <p>@Description ?????????????????????????????????????????????-AOP???????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 21:59 </p>
     * @return com.www.common.config.datasource.interceptor.ReadWriteInterceptor
     */
    @Bean
    public ReadWriteInterceptor ReadWriteInterceptor(){
        return new ReadWriteInterceptor();
    }
    /**
     * <p>@Description ??????????????????????????????????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 22:01 </p>
     * @return com.www.common.config.datasource.datasoure.WriteDataSource
     */
    @Bean
    @ConditionalOnProperty(prefix = "com.www.common.datasource.write",name = {"url"})
    public WriteDataSource writeDataSource(){
        return new WriteDataSource();
    }
    /**
     * <p>@Description ??????????????????????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 22:01 </p>
     * @return com.www.common.config.datasource.datasoure.WriteDataSource
     */
    @Bean
    @ConditionalOnProperty(prefix = "com.www.common.datasource.write",name = {"url"})
    @ConditionalOnMissingBean
    public DataSource dataSource(){
        return new WriteDataSource();
    }
    /**
     * <p>@Description ????????????????????????1??????????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 22:01 </p>
     * @return com.www.common.config.datasource.datasoure.WriteDataSource
     */
    @Bean
    @ConditionalOnProperty(prefix = "com.www.common.datasource.read-one",name = {"url"})
    public ReadOneDataSource readOneDataSource(){
        return new ReadOneDataSource();
    }
    /**
     * <p>@Description ????????????????????????1??????????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2022/3/22 22:01 </p>
     * @return com.www.common.config.datasource.datasoure.WriteDataSource
     */
    @Bean
    @ConditionalOnProperty(prefix = "com.www.common.datasource.read-two",name = {"url"})
    public ReadTwoDataSource readTwoDataSource(){
        return new ReadTwoDataSource();
    }
    /**
     * <p>@Description ????????????????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:47 </p>
     * @return org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
     */
    @Bean
    public AbstractRoutingDataSource routingDataSource(){
        ReadWriteDataSourceProxy proxy = new ReadWriteDataSourceProxy();
        SoftHashMap targetDataSource = new SoftHashMap<>();
        //???????????????????????????
        Map<String, IWriteDataSoure> writeMap = applicationContext.getBeansOfType(IWriteDataSoure.class); //????????????????????????
        IWriteDataSoure DefaultDataSource = null;
        if(MapUtils.isNotEmpty(writeMap)){
            for (String key : writeMap.keySet()){
                targetDataSource.put(WRITE_DATA_SOURCE_PREFIX + writeNum, writeMap.get(key));
                writeNum ++;
                if(DefaultDataSource == null){
                    DefaultDataSource = writeMap.get(key);
                }
            }
            log.info("???????????????????????????????????????????????????{}???????????????????????????",writeNum);
        }
        //???????????????????????????
        Map<String, IReadDataSoure> readMap = applicationContext.getBeansOfType(IReadDataSoure.class);//????????????????????????
        if(MapUtils.isNotEmpty(readMap)){
            for (String key : readMap.keySet()){
                targetDataSource.put(READ_DATA_SOURCE_PREFIX + readNum, readMap.get(key));
                readNum ++;
            }
            log.info("???????????????????????????????????????????????????{}???????????????????????????",readNum);
        }
        //???????????????
        proxy.setDefaultTargetDataSource(DefaultDataSource);
        //??????????????????
        proxy.setTargetDataSources(targetDataSource);
        return proxy;
    }
    /**
     * <p>@Description ??????????????????mybatis???SqlSessionFactory??? </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 20:47 </p>
     * @param dataSource
     * @return org.apache.ibatis.session.SqlSessionFactory
     */
    @Bean
    @Override
    public SqlSessionFactory sqlSessionFactory(@Qualifier("routingDataSource") DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource);
    }
    /**
     * <p>@Description ?????????????????????????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/30 22:26 </p>
     * @return int
     */
    public static int getWriteNum() {
        return writeNum;
    }
    /**
     * <p>@Description ?????????????????????????????? </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/30 22:26 </p>
     * @return int
     */
    public static int getReadNum() {
        return readNum;
    }
}
