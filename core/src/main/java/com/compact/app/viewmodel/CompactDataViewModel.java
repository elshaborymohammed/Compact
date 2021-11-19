package com.compact.app.viewmodel;

import com.jakewharton.rxrelay3.BehaviorRelay;
import com.jakewharton.rxrelay3.PublishRelay;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class CompactDataViewModel<T> extends CompactViewModel {

    protected final CompositeDisposable disposable = new CompositeDisposable();
    protected final BehaviorRelay<T> data = BehaviorRelay.create();
    protected final PublishRelay<Throwable> error = PublishRelay.create();

    public Observable<T> data() {
        if (!data.hasValue()) {
            disposable.add(subscription());
        }
        return data.subscribeOn(subscribeOn()).observeOn(observeOn());
    }

    protected Scheduler subscribeOn() {
        return Schedulers.newThread();
    }

    protected abstract Disposable subscription();

    protected Scheduler observeOn() {
        return AndroidSchedulers.mainThread();
    }

    public Observable<Throwable> error() {
        return error.subscribeOn(subscribeOn()).observeOn(observeOn());
    }

    protected Consumer<T> onSuccess() {
        return it -> data.accept(it);
    }

    protected Consumer<Throwable> onError() {
        return it -> error.accept(it);
    }

    @Override
    public <T> ObservableTransformer<T, T> composeLoadingObservable() {
        return upstream -> upstream.doOnSubscribe(this::add).compose(super.composeLoadingObservable());
    }

    @Override
    public <T> SingleTransformer<T, T> composeLoadingSingle() {
        return upstream -> upstream.doOnSubscribe(this::add).compose(super.composeLoadingSingle());
    }

    @Override
    public CompletableTransformer composeLoadingCompletable() {
        return upstream -> upstream.doOnSubscribe(this::add).compose(super.composeLoadingCompletable());
    }

    protected void add(Disposable d) {
        disposable.add(d);
    }

    protected boolean removeDisposable(Disposable d) {
        return disposable.remove(d);
    }

    @Override
    protected void onCleared() {
        dispose();
        super.onCleared();
    }

    protected void dispose() {
        if (!disposable.isDisposed()) {
            disposable.clear();
        }
    }
}
