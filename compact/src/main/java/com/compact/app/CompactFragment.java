package com.compact.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.compact.content.ContextWrapper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by lshabory on 3/8/18.
 */

public abstract class CompactFragment<T extends ViewDataBinding> extends Fragment {

    private final CompositeDisposable disposables = new CompositeDisposable();
    protected T dataBinding;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(ContextWrapper.wrap(context));
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes(), container, false);
        dataBinding.setLifecycleOwner(this);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        onViewBound(savedInstanceState);
        disposables.addAll(subscriptions());
    }

    protected abstract void onViewBound(@Nullable Bundle savedInstanceState);

    protected Disposable[] subscriptions() {
        return new Disposable[0];
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
    }

    protected void addSubscribe(Disposable d) {
        disposables.add(d);
    }

    public void showSnackBar(@NonNull String message) {
        Snackbar.make(dataBinding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setBehavior(new BaseTransientBottomBar.Behavior())
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .show();
    }

    public void showSnackBar(@NonNull String message, @IdRes int anchorView) {
        Snackbar.make(dataBinding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setBehavior(new BaseTransientBottomBar.Behavior())
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .setAnchorView(anchorView)
                .show();
    }

    public void showToast(@NonNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG)
                .show();
    }
}