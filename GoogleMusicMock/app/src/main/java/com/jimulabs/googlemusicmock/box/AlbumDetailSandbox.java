package com.jimulabs.googlemusicmock.box;

import android.content.Context;
import android.view.View;

import com.jimulabs.googlemusicmock.R;
import com.jimulabs.mirrorsandbox.MirrorAnimator;
import com.jimulabs.mirrorsandbox.MirrorAnimatorSandbox;
import com.jimulabs.mirrorsandbox.MirrorView;

/**
 * Created by lintonye on 14-12-16.
 */
public class AlbumDetailSandbox extends MirrorAnimatorSandbox {
    public AlbumDetailSandbox(View root) {
        super(root);
    }

    public MirrorAnimator showFab() {
        return $(R.id.fab).scale(0, 1);
    }

    public MirrorAnimator showTitleContainer() {
        return unfold($(R.id.title_container));
    }

    public MirrorAnimator showInfoContainer() {
        return unfold($(R.id.info_container));
    }

    public MirrorAnimator enter() {
        // return `[showTitleContainer() -> showInfoContainer(), 500 -> showFab()]`
        return tg(sq(showTitleContainer(), showInfoContainer()), showFab().startDelay(200));
    }

    public MirrorAnimator hideFab() {
        return $(R.id.fab).scale(1, 0);
    }

    public MirrorAnimator hideTitleContainer() {
        return fold($(R.id.title_container));
    }

    public MirrorAnimator hideInfoContainer() {
        return fold($(R.id.info_container));
    }

    public MirrorAnimator exit() {
        // return `hideFab() -> hideInfoContainer() -> hideTitleContainer()`
        return sq(hideFab(), hideInfoContainer(), hideTitleContainer());
    }

    @Override
    public void enterSandbox() {
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
