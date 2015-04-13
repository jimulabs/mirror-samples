package com.jimulabs.paintexamples.lensgen;

import com.jimulabs.lens.AnimatorScript;
import com.jimulabs.lens.LensContext;
import com.jimulabs.lens.MirrorAnimator;

/**
 * Created by lintonye on 14-12-01.
 */
public class AnimatorScript_PropertyAnimation extends AnimatorScript {

    @Override
    protected void onScriptExecuted(LensContext context) {
        MirrorAnimator animator = null;
        context.export(animator, "textView1Anim");
    }

    public void startTextView1Anim() {
        start("textView1Anim");
    }
}
