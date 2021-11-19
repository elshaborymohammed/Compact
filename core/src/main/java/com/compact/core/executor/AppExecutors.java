package com.compact.core.executor;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppExecutors {
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    @Inject
    public AppExecutors(WorkerExecutor networkIO, MainExecutor mainThread) {
        this.diskIO = Executors.newSingleThreadExecutor();
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    @NonNull
    public final Executor diskIO() {
        return this.diskIO;
    }

    @NonNull
    public final Executor networkIO() {
        return this.networkIO;
    }

    @NonNull
    public final Executor mainThread() {
        return this.mainThread;
    }
}
