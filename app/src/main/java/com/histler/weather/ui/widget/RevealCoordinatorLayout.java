package com.histler.weather.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import io.codetail.animation.RevealViewGroup;
import io.codetail.animation.ViewRevealManager;

/**
 * Created by Badr
 * on 14.08.2016 1:58.
 */
public class RevealCoordinatorLayout extends CoordinatorLayout implements RevealViewGroup {
    private ViewRevealManager manager;

    public RevealCoordinatorLayout(Context context) {
        this(context, null);
    }

    public RevealCoordinatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealCoordinatorLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        manager = new ViewRevealManager();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        try {
            canvas.save();

            manager.transform(canvas, child);
            return super.drawChild(canvas, child, drawingTime);
        } finally {
            canvas.restore();
        }
    }

    @Override
    public ViewRevealManager getViewRevealManager() {
        return manager;
    }
}