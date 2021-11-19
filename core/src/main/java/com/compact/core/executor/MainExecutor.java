package com.compact.core.executor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Inject
    public MainExecutor() {
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        handler.post(runnable);
    }
}