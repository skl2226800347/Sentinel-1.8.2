
package com.alibaba.csp.sentinel.dashboard.config;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = { "com.alibaba.csp.sentinel.dashboard.biz.mapper"  }, sqlSessionFactoryRef = DataSourceConfig.SESSION_FACTORY_NAME)
public class DataSourceConfig {
	private static final Logger logger=LoggerFactory.getLogger(DataSourceConfig.class);
    public static final String DATASOURC_ENAME = "dbDataSource";
    public static final String JDBC_TEMPLATE_ENAME = "dbJdbcTemplate";
    public static final String TRANSACTION_MANAGER_NAME = "dbTransactionManager";
    public static final String SESSION_FACTORY_NAME = "dbSqlSessionFactory";
    @Resource
    private Environment env;

    @Bean(name = DATASOURC_ENAME)
    //@Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource initDataSource() {
    	logger.info("[initDataSource]****start************");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = TRANSACTION_MANAGER_NAME)
    public DataSourceTransactionManager transactionManager(@Qualifier("dbDataSource")DataSource prehospitaldbDataSource) {
        logger.info("[transactionManager]****start************");
        return new DataSourceTransactionManager(prehospitaldbDataSource);
    }

    @Bean(name = SESSION_FACTORY_NAME)
    //@Primary
    public SqlSessionFactory initSqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(initDataSource());
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver=new PathMatchingResourcePatternResolver();
       String mybatisMapperLocations=env.getProperty("mybatis.mapper-locations");
        sessionFactory.setMapperLocations(pathMatchingResourcePatternResolver.getResources(mybatisMapperLocations));
        return sessionFactory.getObject();
    }

    @Bean(name = JDBC_TEMPLATE_ENAME)
    public JdbcTemplate initJdbcTemplate() {
        return new JdbcTemplate(initDataSource());
    }
}
