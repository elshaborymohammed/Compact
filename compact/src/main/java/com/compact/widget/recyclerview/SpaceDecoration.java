package com.compact.widget.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private Builder builder;

    private SpaceDecoration(Builder builder) {
        this.builder = builder;
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == RecyclerView.VERTICAL) {
                outRect.top = builder.getTop() * ((parent.getChildLayoutPosition(view) == 0) ? builder.getFirst() : 1);
                outRect.left = builder.getLeft();
            } else {
                outRect.top = builder.getTop();
                outRect.left = builder.getLeft() * ((parent.getChildLayoutPosition(view) == 0) ? builder.getFirst() : 1);
            }
        } else {
            outRect.top = builder.getTop();
            outRect.left = builder.getLeft();
        }
        outRect.right = builder.getRight();
        outRect.bottom = builder.getBottom();
    }

    public static class Builder extends SpaceDecorationBuilder {
        public Builder(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ItemDecoration build() {
            return new SpaceDecoration(this);
        }
    }
}
