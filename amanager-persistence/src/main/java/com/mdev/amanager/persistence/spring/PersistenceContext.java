package com.mdev.amanager.persistence.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by gmilazzo on 01/10/2018.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.mdev.amanager.persistence.domain"})
public class PersistenceContext implements TransactionManagementConfigurer {

    public static final String MODEL_PACKAGE = "com.mdev.amanager.persistence.domain.model";
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    public static final String PERSISTENCE_UNIT_NAME = "amanager.persistence";

    private PlatformTransactionManager txManager;

    @Value("${jdbc.driver.class}")
    private String jdbcDriverClass;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.usr}")
    private String jdbcUsr;

    @Value("${jdbc.pwd}")
    private String jdbcPwd;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("#{T(java.lang.Boolean).parseBoolean('${hibernate.debug}')}")
    private Boolean hibernateDebug;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(MODEL_PACKAGE);
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalJpaProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(jdbcDriverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsr);
        dataSource.setPassword(jdbcPwd);
        return dataSource;
    }

    @Bean
    @Primary
    @Autowired
    public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);
        txManager = transactionManager;
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager;
    }

    Properties additionalJpaProperties() {

        Properties properties = new Properties();
        properties.setProperty(HIBERNATE_DIALECT, hibernateDialect);

        if (hibernateDebug != null && hibernateDebug) {

            properties.setProperty(HIBERNATE_SHOW_SQL, String.valueOf(true));
            properties.setProperty(HIBERNATE_FORMAT_SQL, String.valueOf(true));
        }

        return properties;
    }
}
