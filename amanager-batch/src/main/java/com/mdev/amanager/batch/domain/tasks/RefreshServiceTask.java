package com.mdev.amanager.batch.domain.tasks;

import com.mdev.amanager.batch.domain.pools.AManagerThreadPool;
import com.mdev.amanager.core.service.base.Refreshable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gmilazzo on 13/11/2018.
 */
@Component
public class RefreshServiceTask implements Tasklet {

    private static final Logger logger = LogManager.getLogger(RefreshServiceTask.class);

    @Autowired
    private AManagerThreadPool aManagerThreadPool;

    @Autowired
    private List<Refreshable> refreshables;

    @Nullable
    @Override
    @SuppressWarnings("unused")
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        logger.info("refreshing all services.");
        if (CollectionUtils.isNotEmpty(refreshables)) {
            refreshables.forEach(r -> aManagerThreadPool.submit(r::refresh));
        }

        return null;
    }
}
