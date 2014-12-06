package com.jimulabs.lens;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Slide;
import android.transition.TransitionValues;
import android.view.ViewGroup;

/**
 * Created by lintonye on 14-12-05.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Fold extends Slide {
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
