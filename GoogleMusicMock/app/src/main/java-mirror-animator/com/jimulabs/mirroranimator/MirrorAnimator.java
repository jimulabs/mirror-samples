package com.jimulabs.mirroranimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jimulabs.mirror.model.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lintonye on 14-12-16.
 */
public class MirrorAnimator {
    private static final long DEFAULT_DURATION = 500;
    private static final TimeInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private final Animator mAnimator;

    public MirrorAnimator(Animator animator) {
        mAnimator = animator;
        setDefaults(animator);
    }

    private static void setDefaults(Animator animator) {
        animator.setDuration(AnimatorUtils.computeDuration(DEFAULT_DURATION));
        animator.setInterpolator(DEFAULT_INTERPOLATOR);
    }

    public MirrorAnimator together(MirrorAnimator... mirrorAnimators) {
        List<MirrorAnimator> mas = mePlus(mirrorAnimators);
        return AnimatorUtils.together(mas);
    }

    public MirrorAnimator followedBy(MirrorAnimator mirrorAnimator) {
        List<MirrorAnimator> mas = mePlus(mirrorAnimator);
        return AnimatorUtils.sequence(mas);
    }

    public Animator getAnimator() {
        return mAnimator;
    }

    private List<MirrorAnimator> mePlus(MirrorAnimator... mirrorAnimators) {
        List<MirrorAnimator> result = new ArrayList<>(mirrorAnimators.length + 1);
        result.add(this);
        result.addAll(Arrays.asList(mirrorAnimators));
        return result;
    }

    public MirrorAnimator duration(long duration) {
        mAnimator.setDuration(AnimatorUtils.computeDuration(duration));
        return this;
    }

    public MirrorAnimator startDelay(long delay) {
        mAnimator.setStartDelay(AnimatorUtils.computeDuration(delay));
        return this;
    }

    public void start() {
        initTargetProperties(mAnimator, new FirstOccurrenceOnlyTargetPropSetter());
        mAnimator.start();
    }

    private void initTargetProperties(Animator animator, TargetPropSetter targetPropSetter) {
        if (animator instanceof AnimatorSet) {
            AnimatorSet set = (AnimatorSet) animator;
            for (Animator c : set.getChildAnimations()) {
                initTargetProperties(c, targetPropSetter);
            }
        } else if (animator instanceof ObjectAnimator) {
            ObjectAnimator o = (ObjectAnimator) animator;
            targetPropSetter.set(o);
        } else {
            throw new IllegalStateException("Unsupported animator type: " + animator);
        }
    }

    private static class FirstOccurrenceOnlyTargetPropSetter implements TargetPropSetter {
        private static final String LOG_TAG = "FirstOccurrenceOnly";
        private Map<Object, Set<String>> mRegistry = new HashMap<>();

        @Override
        public void set(ObjectAnimator objectAnimator) {
            Object target = objectAnimator.getTarget();
            Set<String> props = mRegistry.get(target);
            if (props == null) {
                props = new HashSet<>();
                mRegistry.put(target, props);
            }
            String propertyName = objectAnimator.getPropertyName();
            if (!props.contains(propertyName)) {
                Object firstFrameValue = null;
                try {
                    firstFrameValue = getFirstFrameValue(objectAnimator);
                    callSetter(target, propertyName, firstFrameValue);
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException
                        | InvocationTargetException e) {
                    Log.e(LOG_TAG, String.format("Failed to set value of \"%s\"", propertyName), e);
                }
                props.add(propertyName);
            }
        }

        private Object getFirstFrameValue(ObjectAnimator objectAnimator) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            if (objectAnimator.getValues().length == 0) {
                throw new IllegalStateException("The animator does not have any values! " + objectAnimator);
            }
            PropertyValuesHolder firstFrameHolder = objectAnimator.getValues()[0];
            Field keyframesField = firstFrameHolder.getClass().getDeclaredField("mKeyframes");
            keyframesField.setAccessible(true);
            Object keyframes = keyframesField.get(firstFrameHolder);
            Method getValue = keyframes.getClass().getDeclaredMethod("getValue", float.class);
            Object value = getValue.invoke(keyframes, 0f);
            return value;
        }

        private void callSetter(Object target, String propertyName, Object firstFrameValue) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            String setterName = "get" + StringUtils.capitalize(propertyName);
            Method setter = target.getClass().getMethod(setterName, getType(firstFrameValue));
            setter.invoke(target, firstFrameValue);
        }

        private Class<?> getType(Object value) {
            if (value instanceof Integer) {
                return int.class;
            } else if (value instanceof Float) {
                return float.class;
            } else if (value instanceof Long) {
                return long.class;
            } else {
                return value.getClass();
            }
        }
    }

    public interface TargetPropSetter {
        void set(ObjectAnimator objectAnimator);
    }

}
