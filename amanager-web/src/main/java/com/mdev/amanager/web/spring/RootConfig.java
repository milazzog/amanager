package com.mdev.amanager.web.spring;

import com.mdev.amanager.batch.spring.BatchContext;
import com.mdev.amanager.persistence.spring.PersistenceContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by gmilazzo on 28/09/2018.
 */
@Configuration
@ComponentScan(basePackages = "com.mdev")
@PropertySource({"classpath:db.properties", "classpath:batch.properties"})
@Import({SecurityConfig.class, PersistenceContext.class, BatchContext.class})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class RootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
