package com.jimulabs.mirroranimator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * Created by lintonye on 14-12-16.
 */
public class MirrorView {
    private final View mView;

    public MirrorView(View view) {
        mView = view;
    }

    public MirrorAnimator scale(float... values) {
        return AnimatorUtils.together(scaleX(values), scaleY(values));
    }

    public MirrorAnimator scaleY(float... values) {
        return animator("scaleY", values);
    }

    public MirrorAnimator scaleX(float... values) {
        return animator("scaleX", values);
    }

    public MirrorAnimator bottom(int... values) {
        return animator("bottom", values);
    }

    public int getHeight() {
        return mView.getMeasuredHeight();
    }


    public MirrorAnimator animator(String property, int... values) {
        return AnimatorUtils.animator(mView, property, values);
    }


    public MirrorAnimator animator(String property, float... values) {
        return AnimatorUtils.animator(mView, property, values);
    }

    public int getTop() {
        return mView.getTop();
    }

    public View getView() {
        return mView;
    }
}
