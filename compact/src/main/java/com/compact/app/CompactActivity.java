package com.compact.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.compact.content.ContextWrapper;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by lshabory on 3/8/18.
 */

public abstract class CompactActivity<T extends ViewDataBinding> extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private final CompositeDisposable disposable = new CompositeDisposable();
    public T dataBinding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextWrapper.wrap(newBase));
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, layoutRes());
        dataBinding.setLifecycleOwner(this);

        onBindView(savedInstanceState);
        disposable.addAll(subscriptions());
    }

    @LayoutRes
    protected abstract int layoutRes();

    protected abstract void onBindView(@Nullable Bundle savedInstanceState);

    protected Disposable[] subscriptions() {
        return new Disposable[0];
    }

    public void subscribe(Disposable d) {
        disposable.add(d);
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        disposable.clear();
        super.onDestroy();
    }

    public void showSnackBar(@NonNull String message) {
        Snackbar.make(dataBinding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .show();
    }

    public void showToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show();
    }
}