package com.compact.widget.recyclerview;

import android.content.Context;
import android.util.TypedValue;

import androidx.recyclerview.widget.RecyclerView;

public abstract class SpaceDecorationBuilder {

    private final Context context;
    private int first;
    private int top;
    private int left;
    private int right;
    private int bottom;

    public SpaceDecorationBuilder(Context context) {
        this.context = context;
        first(1).space(8);
    }

    public SpaceDecorationBuilder space(int space) {
        return top(space).left(space).right(space).bottom(0);
    }

    public SpaceDecorationBuilder first(int factor) {
        this.first = factor;
        return this;
    }

    public SpaceDecorationBuilder bottom(int space) {
        this.bottom = toDIP(context, space);
        return this;
    }

    public SpaceDecorationBuilder right(int space) {
        this.right = toDIP(context, space);
        return this;
    }

    public SpaceDecorationBuilder left(int space) {
        this.left = toDIP(context, space);
        return this;
    }

    public SpaceDecorationBuilder top(int space) {
        this.top = toDIP(context, space);
        return this;
    }

    protected int toDIP(Context context, int pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, context.getResources().getDisplayMetrics());
    }

    public SpaceDecorationBuilder horizontal(int space) {
        return left(space).right(space);
    }

    public SpaceDecorationBuilder vertical(int space) {
        return top(space).bottom(space);
    }

    public int getFirst() {
        return Math.max(first, 1);
    }

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public abstract RecyclerView.ItemDecoration build();
}
