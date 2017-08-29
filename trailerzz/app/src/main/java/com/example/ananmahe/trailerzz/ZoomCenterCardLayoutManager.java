package com.example.ananmahe.trailerzz;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ananmahe on 8/29/17.
 */

public class ZoomCenterCardLayoutManager extends LinearLayoutManager {
    // Shrink the cards around the center up to 50%
    private final float mShrinkAmount = 0.13f;
    // The cards will be at 50% when they are 75% of the way between the
    // center and the edge.
    private final float mShrinkDistance = 0.5f;

    public ZoomCenterCardLayoutManager(Context context) {
        super(context);
    }

    @Override
    public int scrollVerticallyBy(int dx,
                                    RecyclerView.Recycler recycler, RecyclerView.State state) {

        int scrolled = super.scrollVerticallyBy(dx, recycler, state);
        float midpoint = getWidth() / 2.f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint;
        float s0 = 1.f;
        float s1 = 1.f - mShrinkAmount;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidpoint =
                    (getDecoratedTop(child) + getDecoratedBottom(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            child.setScaleX(scale);
            child.setScaleY(scale);
        }

        return scrolled;

    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scrollHorizontallyBy(0, recycler, state);
    }
}
