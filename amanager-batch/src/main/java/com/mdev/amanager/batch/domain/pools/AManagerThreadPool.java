package com.mdev.amanager.batch.domain.pools;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * Created by gmilazzo on 13/11/2018.
 */
@Component
public class AManagerThreadPool {

    public static final String DEFAULT_SHUTDOWN_ERROR_MESSAGE = "thread pool did not stop within the timeout";
    public static final int MIN_CORE_THREADS = 10;
    private ExecutorService threadPool;
    private static final Logger logger = LogManager.getLogger(AManagerThreadPool.class);

    @PostConstruct
    protected void init() {
        threadPool = new ThreadPoolExecutor(MIN_CORE_THREADS, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new BasicThreadFactory.Builder().namingPattern("amanager-worker-%d").build());
    }

    @PreDestroy
    protected void destroy() {
        shutdown(threadPool);
    }

    public Future<?> submit(Runnable runnable) {
        return threadPool.submit(runnable);
    }

    public <V> Future<V> submit(Callable<V> callable) {
        return threadPool.submit(callable);
    }

    public static void shutdown(ExecutorService threadPool) {
        shutdown(threadPool, DEFAULT_SHUTDOWN_ERROR_MESSAGE);
    }

    public static void shutdown(ExecutorService threadPool, String errorMessage) {
        threadPool.shutdownNow();
        try {
            threadPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LogManager.getLogger(AManagerThreadPool.class).error(errorMessage, e);
        }
    }
}
