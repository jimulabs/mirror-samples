package com.jimulabs.googlemusicmock;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

/**
 * Created by lintonye on 14-12-02.
 */
public class RevealTransition extends Visibility {
    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        float radius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 200, 200, 0, radius);
        animator.setDuration(300);
        return new WrapperAnimator(animator);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        float radius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 200, 200, radius, 0);
        animator.setDuration(300);
        return new WrapperAnimator(animator);
    }
}
