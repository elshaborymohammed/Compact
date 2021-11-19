package com.compact.core.executor;

import io.reactivex.rxjava3.core.Scheduler;

public interface MainThread {
    Scheduler getScheduler();
}