package com.jimulabs.mirroranimator;

import android.animation.Animator;
import android.animation.Keyframe;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.jimulabs.mirror.model.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lintonye on 14-12-16.
 */
public abstract class MirrorAnimator {

    public MirrorAnimator together(MirrorAnimator... mirrorAnimators) {
        List<MirrorAnimator> mas = mePlus(mirrorAnimators);
        return AnimatorUtils.together(mas);
    }

    public MirrorAnimator followedBy(MirrorAnimator mirrorAnimator) {
        List<MirrorAnimator> mas = mePlus(mirrorAnimator);
        return AnimatorUtils.sequence(mas);
    }

    public abstract Animator getAnimator();

    private List<MirrorAnimator> mePlus(MirrorAnimator... mirrorAnimators) {
        List<MirrorAnimator> result = new ArrayList<>(mirrorAnimators.length + 1);
        result.add(this);
        result.addAll(Arrays.asList(mirrorAnimators));
        return result;
    }

    public abstract MirrorAnimator duration(long duration);

    public abstract MirrorAnimator startDelay(long delay);

    public abstract long getDuration();

    public abstract long getStartDelay();

    public void start() {
        setupStage(this, new UseFirstFrameOnlyStageSetter());
        getAnimator().start();
    }

    private void setupStage(MirrorAnimator animator, StageSetter stageSetter) {
        List<Pair<MirrorObjectAnimator, Long>> animatorStartTimes = new ArrayList<>();
        collectStartTime(animator, animatorStartTimes, 0);
        Collections.sort(animatorStartTimes, new Comparator<Pair<MirrorObjectAnimator, Long>>() {
            @Override
            public int compare(Pair<MirrorObjectAnimator, Long> lhs, Pair<MirrorObjectAnimator, Long> rhs) {
                return (int) (lhs.second - rhs.second);
            }
        });

        stageSetter.setup(animatorStartTimes);
    }

    private void collectStartTime(MirrorAnimator animator, List<Pair<MirrorObjectAnimator, Long>> output, int startTime) {
        if (animator instanceof MirrorAnimatorSet) {
            MirrorAnimatorSet set = (MirrorAnimatorSet) animator;
            int accuTimeBefore = 0;
            for (MirrorAnimator c : set.getChildAnimations()) {
                if (set.getOrdering() == MirrorAnimatorSet.Ordering.Together) {
                    collectStartTime(c, output, startTime);
                } else {
                    collectStartTime(c, output, startTime + accuTimeBefore);
                }
                accuTimeBefore += c.getStartDelay() + c.getDuration();
            }
        } else if (animator instanceof MirrorObjectAnimator) {
            MirrorObjectAnimator o = (MirrorObjectAnimator) animator;
            output.add(new Pair<>(o, startTime + o.getStartDelay()));
        } else {
            throw new IllegalStateException("Unsupported animator type: " + animator);
        }
    }

    private static class UseFirstFrameOnlyStageSetter implements StageSetter {
        private static final String LOG_TAG = "FirstOccurrenceOnly";
        private static final List<String> PROPS_AFFECTED_BY_LAYOUT = Arrays.asList(new String[]{"left", "right", "top", "bottom"});
        private Map<Object, Set<String>> mRegistry = new HashMap<>();

        @Override
        public void setup(List<Pair<MirrorObjectAnimator, Long>> sortedAnimatorStartTimes) {
            for (Pair<MirrorObjectAnimator, Long> pair : sortedAnimatorStartTimes) {
                setupOne(pair.first);
            }
        }

        private void setupOne(MirrorObjectAnimator objectAnimator) {
            Object target = objectAnimator.getTarget();
            Set<String> props = mRegistry.get(target);
            if (props == null) {
                props = new HashSet<>();
                mRegistry.put(target, props);
            }
            String propertyName = objectAnimator.getPropertyName();
            if (!props.contains(propertyName)) {
                if ((target instanceof View) && willBeUpdatedDuringLayout(propertyName)) {
                    callSetterOnLayoutChange((View)target, propertyName, objectAnimator.getFirstFrame());
                } else {
                    callSetter(target, propertyName, objectAnimator.getFirstFrame());
                }
                props.add(propertyName);
            }
        }

        private void callSetterOnLayoutChange(final View target, final String propertyName, final Keyframe firstFrame) {
            target.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    callSetter(target, propertyName, firstFrame);
                    target.removeOnLayoutChangeListener(this);
                }
            });
        }

        private boolean willBeUpdatedDuringLayout(String propertyName) {
            return PROPS_AFFECTED_BY_LAYOUT.contains(propertyName);
        }

        private void callSetter(Object target, String propertyName, Keyframe firstFrame) {
            try {
                String setterName = "set" + StringUtils.capitalize(propertyName);
                Method setter = target.getClass().getMethod(setterName, firstFrame.getType());
                setter.invoke(target, firstFrame.getValue());
                Log.d(LOG_TAG, String.format("%s=%s", propertyName, firstFrame.getValue()));
            } catch (IllegalAccessException | NoSuchMethodException
                    | InvocationTargetException e) {
                Log.e(LOG_TAG, String.format("Failed to set value of \"%s\"", propertyName), e);
            }
        }
    }

    public interface StageSetter {
        void setup(List<Pair<MirrorObjectAnimator, Long>> sortedAnimatorStartTimes);
    }

}
