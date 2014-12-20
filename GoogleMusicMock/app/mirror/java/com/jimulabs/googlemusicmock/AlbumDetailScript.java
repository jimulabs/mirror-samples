package com.jimulabs.googlemusicmock;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.jimulabs.mirroranimator.MirrorAnimator;
import com.jimulabs.mirroranimator.MirrorAnimatorScript;
import com.jimulabs.mirroranimator.MirrorView;

/**
 * Created by lintonye on 14-12-16.
 */
public class AlbumDetailScript extends MirrorAnimatorScript {
    public AlbumDetailScript(View root) {
        super(root);
    }
    public AlbumDetailScript(Context context, View root) {
        super(context, root);
    }

    public MirrorAnimator showFab() {
        return $("fab").scale(0, 1);
    }

    public MirrorAnimator showTitleContainer() {
        MirrorView v = $("title_container");
        return fold(v);
    }

    public MirrorAnimator showInfoContainer() {
        MirrorView v = $("info_container");
        return fold(v);
    }

    public MirrorAnimator animateAll() {
        // return `[showTitleContainer() -> showInfoContainer(), 500 -> showFab()]`
        return tg(sq(showTitleContainer(), showInfoContainer()), showFab().startDelay(200));
    }

    @Override
    protected void editModeScript(View rootView) {
        setGlobalSpeed(0.2);
        animateAll().start();
    }

    private MirrorAnimator fold(MirrorView v) {
        return v.bottom(v.getTop(), v.getTop() + v.getHeight());
    }

}
