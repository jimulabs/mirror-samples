package com.jimulabs.mirroranimator;

import android.animation.Animator;
import android.animation.AnimatorSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lintonye on 2014-12-19.
 */
public class MirrorAnimatorSet extends MirrorAnimator {
    private final List<MirrorAnimator> mAnimators;
    private final Ordering mOrdering;
    private final AnimatorSet mSet;

    public MirrorAnimatorSet(List<MirrorAnimator> animators, Ordering ordering) {
        mAnimators = Collections.unmodifiableList(animators);
        mOrdering = ordering;
        mSet = new AnimatorSet();
        List<Animator> anims = collectAnimators(animators);
        switch (ordering) {
            case Together:
                mSet.playTogether(anims);
                break;
            case Sequentially:
                mSet.playSequentially(anims);
        }
    }

    private List<Animator> collectAnimators(List<MirrorAnimator> animators) {
        List<Animator> result = new ArrayList<>(animators.size());
        for (MirrorAnimator a:animators) {
            result.add(a.getAnimator());
        }
        return result;
    }

    public List<MirrorAnimator> getChildAnimations() {
        return mAnimators;
    }

    public Ordering getOrdering() {
        return mOrdering;
    }

    @Override
    public Animator getAnimator() {
        return mSet;
    }

    @Override
    public MirrorAnimator duration(long duration) {
        //TODO maybe throw an exception?
        mSet.setDuration(duration);
        return this;
    }

    @Override
    public MirrorAnimator startDelay(long delay) {
        mSet.setStartDelay(delay);
        return this;
    }

    @Override
    public long getDuration() {
        return mSet.getDuration();
    }

    @Override
    public long getStartDelay() {
        return mSet.getStartDelay();
    }

    public enum Ordering {
        Together, Sequentially
    }
}
