package com.jimulabs.googlemusicmock;

import android.content.Context;
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
        return unfold($("title_container"));
    }

    public MirrorAnimator showInfoContainer() {
        return unfold($("info_container"));
    }

    public MirrorAnimator enter() {
        // return `[showTitleContainer() -> showInfoContainer(), 500 -> showFab()]`
        return tg(sq(showTitleContainer(), showInfoContainer()), showFab().startDelay(200));
    }

    public MirrorAnimator hideFab() {
        return $("fab").scale(1, 0);
    }

    public MirrorAnimator hideTitleContainer() {
        return fold($("title_container"));
    }

    public MirrorAnimator hideInfoContainer() {
        return fold($("info_container"));
    }

    public MirrorAnimator exit() {
        // return `hideFab() -> hideInfoContainer() -> hideTitleContainer()`
        return sq(hideFab(), hideInfoContainer(), hideTitleContainer());
    }

    @Override
    protected void editModeScript(View rootView) {
//        setGlobalSpeed(0.5);
        enter().start();
//        showTitleContainer().start();
    }

    private MirrorAnimator unfold(MirrorView v) {
        return v.bottom(v.getTop(), v.getTop() + v.getHeight());
    }

    private MirrorAnimator fold(MirrorView v) {
        return v.bottom(v.getTop() + v.getHeight(), v.getTop());
    }

}
