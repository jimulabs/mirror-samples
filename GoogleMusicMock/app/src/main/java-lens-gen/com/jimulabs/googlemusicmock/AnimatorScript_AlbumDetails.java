package com.jimulabs.googlemusicmock;

import android.animation.Animator;

import com.jimulabs.lens.AnimatorScript;
import com.jimulabs.lens.LensContext;
import com.jimulabs.lens.MirrorAnimator;
import com.jimulabs.lens.MirrorPropertyAnimator;
import com.jimulabs.lens.ViewWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lintonye on 14-12-01.
 */
public class AnimatorScript_AlbumDetails extends AnimatorScript {

    private Animator titleContainer(boolean toExpand) {
        ViewWrapper v = findView("title_container");
        int bStart = toExpand ? 0 : v.getHeight();
        int bEnd = toExpand ? v.getHeight() : 0;
        Map<String, Object> spec = new HashMap<>();
        Map<String, Object> props = new HashMap<>();
        props.put("bottom", new float[] {bStart, bEnd});
        spec.put("properties", props);
        Animator anim = v.animator(spec);
        return anim;
    }

    public Animator titleContainerEnter() {
        return titleContainer(true);
    }

    public Animator fab() {
        //TODO
        return null;
    }

    public Animator enter() {
        return sequence(titleContainerEnter(), fab());
    }
}
