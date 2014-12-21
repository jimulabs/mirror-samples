package com.jimulabs.mirroranimator;

/**
 * Created by lintonye on 2014-12-21.
 */
public class MirrorWrap {
    private final Object mWrapped;

    public MirrorWrap(Object obj) {
        mWrapped = obj;
    }
    public MirrorAnimator animator(String property, int... values) {
        return AnimatorUtils.animator(mWrapped, property, values);
    }

    public MirrorAnimator animator(String property, float... values) {
        return AnimatorUtils.animator(mWrapped, property, values);
    }


}
