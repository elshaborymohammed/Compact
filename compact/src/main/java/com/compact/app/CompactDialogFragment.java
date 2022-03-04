package com.compact.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import com.compact.content.ContextWrapper;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class CompactDialogFragment<T extends ViewDataBinding> extends DialogFragment {

    private final CompositeDisposable disposable = new CompositeDisposable();
    protected T dataBinding;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(ContextWrapper.wrap(context));
        setCancelable(cancelable());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        disposable.clear();
        super.onDestroyView();
    }

    protected boolean cancelable() {
        return false;
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
        onViewBound();
        disposable.addAll(subscriptions());
    }

    @Override
    public void onResume() {
        super.onResume();
//        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        getDialog().getWindow().setAttributes(params);
    }

    @LayoutRes
    protected abstract int layoutRes();

    protected abstract void onViewBound();

    protected Disposable[] subscriptions() {
        return new Disposable[0];
    }

    public void subscribe(Disposable d) {
        disposable.add(d);
    }
}