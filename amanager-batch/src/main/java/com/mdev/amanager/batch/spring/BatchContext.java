package com.mdev.amanager.batch.spring;

import javafx.concurrent.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.lang.NonNullApi;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * Created by gmilazzo on 13/11/2018.
 */
@Configuration
@EnableScheduling
@EnableBatchProcessing
@ComponentScan(basePackages = "com.mdev.amanager.batch.domain")
public class BatchContext implements BatchConfigurer {

    private JobRepository jobRepository;
    private PlatformTransactionManager platformTransactionManager;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("#{T(java.lang.Integer).parseInt('${batch.available.cores:0}')}")
    private Integer availableCores;

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @PostConstruct
    protected void init() {

        try {

            ResourcelessTransactionManager transactionManager = new ResourcelessTransactionManager();
            MapJobRepositoryFactoryBean jobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
            JobRepository jobRepository = jobRepositoryFactoryBean.getObject();
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            SimpleJobLauncher launcher = (new SimpleJobLauncher());

            if (Objects.isNull(jobRepository)) {
                throw new Exception("unable to create 'jobRepository'");
            }

            threadPoolTaskExecutor.setCorePoolSize(ObjectUtils.defaultIfNull(availableCores, 1));
            threadPoolTaskExecutor.setThreadNamePrefix("amanager-batch-thread-");
            threadPoolTaskExecutor.afterPropertiesSet();
            launcher.setJobRepository(jobRepository);
            launcher.setTaskExecutor(threadPoolTaskExecutor);
            launcher.afterPropertiesSet();

            MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactoryBean);
            jobExplorerFactory.afterPropertiesSet();

            this.platformTransactionManager = transactionManager;
            this.jobRepository = jobRepository;
            this.jobLauncher = launcher;
            this.jobExplorer = jobExplorerFactory.getObject();
            this.threadPoolTaskExecutor = threadPoolTaskExecutor;

        } catch (Exception e) {
            throw new Error("error occurred while initializing context: ", e);
        }
    }

    @PreDestroy
    public void destroy() {
        threadPoolTaskExecutor.shutdown();
    }

    @Bean
    public Job refreshServiceJob(@Autowired @Qualifier("refreshServiceStep") Step refreshServiceStep) {
        return jobBuilders.get("refreshServiceJob").flow(refreshServiceStep).end().build();
    }

    @Bean
    public Step refreshServiceStep(@Autowired @Qualifier("refreshServiceTask") Tasklet refreshServiceTask) {
        return stepBuilders.get("refreshServiceStep").tasklet(refreshServiceTask).transactionManager(getTransactionManager()).build();
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return this.platformTransactionManager;
    }

    @Override
    public JobRepository getJobRepository() {
        return this.jobRepository;
    }

    @Override
    public JobLauncher getJobLauncher() {
        return this.jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() {
        return this.jobExplorer;
    }
}
