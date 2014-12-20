package com.jimulabs.mirroranimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    public static MirrorAnimator together(Collection<MirrorAnimator> animators) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(collectAnimators(animators));
        return new MirrorAnimator(set);
    }

    public static MirrorAnimator sequence(List<MirrorAnimator> animators) {
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(collectAnimators(animators));
        return new MirrorAnimator(set);
    }

    static List<Animator> collectAnimators(Collection<MirrorAnimator> mirrorAnimators) {
        List<Animator> animators = new ArrayList<>(mirrorAnimators.size());
        for (MirrorAnimator ma : mirrorAnimators) {
            animators.add(ma.getAnimator());
        }
        return animators;
    }

    public static MirrorAnimator animator(Object target, String property, int... values) {
        Animator animator = ObjectAnimator.ofInt(target, property, values);
        return new MirrorAnimator(animator);
    }

    public static MirrorAnimator animator(Object target, String property, float... values) {
        Animator animator = ObjectAnimator.ofFloat(target, property, values);
        return new MirrorAnimator(animator);
    }

}
