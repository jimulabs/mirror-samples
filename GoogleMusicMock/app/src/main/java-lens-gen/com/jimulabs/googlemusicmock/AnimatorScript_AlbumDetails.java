package com.jimulabs.googlemusicmock;

import com.jimulabs.lens.AnimatorScript;
import com.jimulabs.lens.LensContext;
import com.jimulabs.lens.MirrorAnimator;

/**
 * Created by lintonye on 14-12-01.
 */
public class AnimatorScript_AlbumDetails extends AnimatorScript {

    @Override
    protected void onScriptExecuted(LensContext context) {
        MirrorAnimator animator = null;
        context.export(animator, "textView1Anim");
    }

    public void startAlbumDetailsEnter() {
        start("albumDetailsEnter");
    }
}
