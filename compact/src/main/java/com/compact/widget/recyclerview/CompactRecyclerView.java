package com.compact.widget.recyclerview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompactRecyclerView {

    public static abstract class Adapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
        private RecyclerView recyclerView;
        private List<T> data = new ArrayList<>();
        private Context context;

        public Adapter() {
        }

        public Adapter(@NonNull List<T> data) {
            this.data = data;
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            this.context = recyclerView.getContext();
            recyclerView.setLayoutManager(layoutManager());
            recyclerView.setItemAnimator(itemAnimator());
            if (null != itemDecorations())
                for (RecyclerView.ItemDecoration itemDecoration : itemDecorations()) {
                    recyclerView.addItemDecoration(itemDecoration);
                }
            super.onAttachedToRecyclerView(recyclerView);
            this.recyclerView = recyclerView;
        }

        protected RecyclerView.LayoutManager layoutManager() {
            return new LinearLayoutManager(context);
        }

        protected RecyclerView.ItemAnimator itemAnimator() {
            return new DefaultItemAnimator();
        }

        protected RecyclerView.ItemDecoration[] itemDecorations() {
            return new RecyclerView.ItemDecoration[]{
                    SpaceDecoration.builder(context).space(8).build()
            };
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public Context getContext() {
            return context;
        }

//        @LayoutRes
//        @NonNull
//        protected abstract int layoutRes();

//        @NonNull
//        @Override
//        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return (VH) new ViewHolder(DataBindingUtil.inflate(
//                    LayoutInflater.from(parent.getContext()),
//                    layoutRes(),
//                    parent,
//                    false
//            ));
//        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int i) {
            if (vh instanceof ViewHolder) {
                ViewHolder<T, ViewDataBinding> viewHolder = (ViewHolder<T, ViewDataBinding>) vh;
                viewHolder.bind(Objects.requireNonNull(DataBindingUtil.bind(vh.itemView)), i, get(i));
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return null == data ? 0 : data.size();
        }

        protected T get(int position) {
            return data.get(position);
        }

        public List<T> getData() {
            return data;
        }

        public void swap(@NonNull List<T> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        public void add(@NonNull T t) {
            this.data.add(t);
            notifyItemInserted(data.size() - 1);
        }

        public void addAll(@NonNull List<T> data) {
            this.data.addAll(data);
            notifyItemRangeInserted(this.data.size() - data.size(), data.size());
        }

        public void replace(int position, @NonNull T data) {
            this.data.set(position, data);
            notifyItemChanged(position);
        }

        public T remove(int position) {
            T remove = this.data.remove(position);
            //notifyItemRemoved(position);
            notifyDataSetChanged();
            return remove;
        }

        public boolean remove(T object) {
            boolean remove = this.data.remove(object);
            notifyDataSetChanged();
            return remove;
        }

        public void clear() {
            this.data.clear();
            notifyDataSetChanged();
        }
    }

    public static abstract class ViewHolder<T, B extends ViewDataBinding> extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull ViewDataBinding inflate) {
            super(inflate.getRoot());
        }

        public abstract void bind(@NonNull B binding, int position, @NonNull T t);
    }

    public static abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
            boolean endHasBeenReached = lastVisible >= totalItemCount && totalItemCount > 0;

            if (endHasBeenReached) {
                nextPage();
            }
        }

        protected abstract void nextPage();
    }
}