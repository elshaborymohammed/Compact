package com.compact.core.data;

import android.os.Looper;

import com.compact.core.executor.MainExecutor;
import com.compact.core.executor.MainThread;
import com.compact.core.executor.RxCompactSchedulers;
import com.compact.core.executor.WorkerExecutor;
import com.compact.core.executor.WorkerThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.migration.DisableInstallInCheck;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Module
@DisableInstallInCheck
public class SchedulerModule {

    @Provides
    @Singleton
    MainThread providesMainThread(MainExecutor executor) {
        return () -> AndroidSchedulers.from(Looper.myLooper(), true);
    }

    @Provides
    @Singleton
    WorkerThread providesNetworkScheduler(WorkerExecutor executor) {
        return () -> Schedulers.from(executor);
    }

    @Provides
    @Singleton
    RxCompactSchedulers providesRxCompactSchedulers(WorkerThread workerThread, MainThread mainThread) {
        return new RxCompactSchedulers(workerThread, mainThread);
    }
}
