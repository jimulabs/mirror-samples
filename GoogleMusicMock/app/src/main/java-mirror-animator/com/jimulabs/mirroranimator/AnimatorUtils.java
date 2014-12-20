package com.jimulabs.mirroranimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by lintonye on 14-12-16.
 */
public class AnimatorUtils {
    private static double sSpeed = 1;
    public static void setGlobalSpeed(double speed) {
        sSpeed = speed;
    }

    static long computeDuration(long duration1x) {
        return (long) (duration1x * 1/sSpeed);
    }

    public static MirrorAnimator together(MirrorAnimator... animators) {
        return together(Arrays.asList(animators));
    }

    public static MirrorAnimator sequence(MirrorAnimator... animators) {
        return sequence(Arrays.asList(animators));
    }

    public static MirrorAnimator together(List<MirrorAnimator> animators) {
        MirrorAnimatorSet set = new MirrorAnimatorSet(animators,
                MirrorAnimatorSet.Ordering.Together);
        return set;
    }

    public static MirrorAnimator sequence(List<MirrorAnimator> animators) {
        MirrorAnimatorSet set = new MirrorAnimatorSet(animators,
                MirrorAnimatorSet.Ordering.Sequentially);
        return set;
    }

    public static MirrorAnimator animator(Object target, String property, int... values) {
        ObjectAnimator animator = ObjectAnimator.ofInt(target, property, values);
        Keyframe firstFrame = Keyframe.ofInt(0, values[0]);
        Keyframe lastFrame = Keyframe.ofInt(0, values[values.length-1]);
        return new MirrorObjectAnimator(animator, firstFrame, lastFrame);
    }

    public static MirrorAnimator animator(Object target, String property, float... values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, property, values);
        Keyframe firstFrame = Keyframe.ofFloat(0, values[0]);
        Keyframe lastFrame = Keyframe.ofFloat(0, values[values.length-1]);
        return new MirrorObjectAnimator(animator, firstFrame, lastFrame);
    }

}
