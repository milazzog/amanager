package com.mdev.amanager.batch.domain.base;

import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by gmilazzo on 13/11/2018.
 */
public abstract class TaskletLauncher implements BatchConstants {

    @Autowired
    private JobLauncher jobLauncher;

    protected abstract Logger getLogger();

    protected void launch(Job job) {
        try {

            JobParametersBuilder paramBuilder = new JobParametersBuilder();

            paramBuilder.addDate(TIMESTAMP_KEY, new Date());

            jobLauncher.run(job, paramBuilder.toJobParameters());

        } catch (Exception e) {

            getLogger().error("error while executing job: ", e);
        }
    }
}