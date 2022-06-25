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
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.compact.R;
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
    public T dataBinding;

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

    public void subscribe(Disposable d) {
        disposables.add(d);
    }

    @Deprecated
    public void addSubscribe(Disposable d) {
        disposables.add(d);
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
    }

    public Snackbar makeSnackBar(@NonNull String message) {
        return Snackbar.make(dataBinding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setBehavior(new BaseTransientBottomBar.Behavior())
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE);
    }

    public void showSnackBar(@NonNull String message) {
        makeSnackBar(message).show();
    }

    public void showSnackBar(@NonNull String message, @IdRes int anchorView) {
        makeSnackBar(message)
                .setAnchorView(anchorView)
                .show();
    }

    public void showSnackBarRetry(@NonNull String message, View.OnClickListener listener) {
        makeSnackBar(message)
                .setAction(R.string.retry, listener)
                .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.retry))
                .show();
    }

    public void showSnackBarRetry(@NonNull String message, @IdRes int anchorView, View.OnClickListener listener) {
        makeSnackBar(message)
                .setAction(R.string.retry, listener)
                .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.retry))
                .setAnchorView(anchorView)
                .show();
    }

    public void showToast(@NonNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}