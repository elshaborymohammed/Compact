package com.compact.core.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WorkerExecutor implements Executor {
    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 0;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.MILLISECONDS;
    // Creates a thread pool manager
    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject
    public WorkerExecutor() {
        /*
         * Gets the number of available cores
         * (not always the same as the maximum number of cores)
         */
        //Runtime.getRuntime().availableProcessors();
        int NUMBER_OF_CORES = 1;
        // Instantiates the queue of #Runnable as a LinkedBlockingQueue
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                blockingQueue
        );
    }

    @Override
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}