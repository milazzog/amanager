package com.mdev.amanager.batch.domain.schedulers;

import com.mdev.amanager.batch.domain.base.TaskletLauncher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by gmilazzo on 13/11/2018.
 */
@Component
public class RefreshServiceScheduler extends TaskletLauncher {

    private static final Logger logger = LogManager.getLogger(RefreshServiceScheduler.class);

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Autowired
    @Qualifier("refreshServiceJob")
    private Job job;

    @Scheduled(cron = "${refresh.service.job.cron}")
    public void run() {

        launch(job);
    }
}
