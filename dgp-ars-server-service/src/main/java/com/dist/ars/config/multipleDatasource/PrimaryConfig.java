package com.dist.ars.config.multipleDatasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-04-23 17:56
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：辅助审查系统-配置类：配置JPA事务管理对象、JdbcTemplate、实体管理工厂对象
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        // repository包名
        basePackages = "com.dist.ars.dao.ars",
        // 实体管理bean名称
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        // 事务管理bean名称
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryConfig {
    /**
     * 扫描spring.jpa.primary开头的配置信息
     *
     * @return jpa配置信息
     */
    @Primary
    @Bean(name = "primaryJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.primary")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 获取主库实体管理工厂对象
     *
     * @param primaryDataSource 注入名为primaryDataSource的数据源
     * @param jpaProperties     注入名为primaryJpaProperties的jpa配置信息
     * @param builder           注入EntityManagerFactoryBuilder
     * @return 实体管理工厂对象
     */
    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                                                       @Qualifier("primaryJpaProperties") JpaProperties jpaProperties,
                                                                       EntityManagerFactoryBuilder builder) {
        return builder
                // 设置数据源
                .dataSource(primaryDataSource)
                // 设置jpa配置
                .properties(jpaProperties.getProperties())
                // 设置实体包名
                .packages("com.dist.ars.model.entity.ars")
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 注入名为primaryEntityManagerFactory的bean
     * @return 实体管理对象
     */
    @Primary
    @Bean(name = "primaryEntityManager")
    public EntityManager entityManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取主库事务管理对象
     *
     * @param factory 注入名为primaryEntityManagerFactory的bean
     * @return 事务管理对象
     */
    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory factory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(factory);

        // 设置事务超时时间30*60秒
        jpaTransactionManager.setDefaultTimeout(1800);
        return jpaTransactionManager;
    }

    /**
     * 配置JdbcTemplate
     *
     * @param primaryDataSource
     * @return
     */
    @Primary
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return new JdbcTemplate(primaryDataSource);
    }
}