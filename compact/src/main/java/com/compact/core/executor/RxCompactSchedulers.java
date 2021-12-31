package com.compact.core.executor;

import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleTransformer;

public class RxCompactSchedulers {

    private final Scheduler subscribeOn;
    private final Scheduler observeOn;

    public RxCompactSchedulers(WorkerThread subscribeOn, MainThread observeOn) {
        this(subscribeOn.getScheduler(), observeOn.getScheduler());
    }

    public RxCompactSchedulers(Scheduler subscribeOn, Scheduler observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public <T> ObservableTransformer<T, T> applyOnObservable() {
        return upstream -> upstream.subscribeOn(subscribeOn).observeOn(observeOn).unsubscribeOn(subscribeOn);
    }

    public <T> FlowableTransformer<T, T> applyOnFlowable() {
        return upstream -> upstream.subscribeOn(subscribeOn).observeOn(observeOn).unsubscribeOn(subscribeOn);
    }

    public <T> SingleTransformer<T, T> applyOnSingle() {
        return upstream -> upstream.subscribeOn(subscribeOn).observeOn(observeOn).unsubscribeOn(subscribeOn);
    }

    public CompletableTransformer applyOnCompletable() {
        return upstream -> upstream.subscribeOn(subscribeOn).observeOn(observeOn).unsubscribeOn(subscribeOn);
    }

    public Scheduler workerExecutor() {
        return subscribeOn;
    }

    public Scheduler mainExecutor() {
        return observeOn;
    }
}
