package com.jimulabs.googlemusicmock;

import android.app.Activity;
import android.graphics.Point;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.jimulabs.lens.Fold;
import com.jimulabs.lens.LensBase;
import com.jimulabs.lens.MirrorAnimator;
import com.jimulabs.lens.MirrorPropertyAnimator;
import com.jimulabs.lens.RevealTransition;

/**
 * Created by lintonye on 14-12-03.
 */
public class Lens extends LensBase {
    public Lens(Activity host, int layoutResId) {
        super(host, layoutResId);
    }

    @Override
    protected void initWindow() {
        switch (mLayoutResId) {
            case R.layout.activity_album_list:
                disableEnterTransition();
                disableExitTransition();
                break;
            case R.layout.activity_album_detail:
                Point epicenter = mHostActivity.getIntent().getParcelableExtra(EXTRA_LENS_EPICENTER);
                int smallRadius = 0;
                DisplayMetrics displayMetrics = mHostActivity.getResources().getDisplayMetrics();
                int bigRadius = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
                long duration = 500;

                RevealTransition reveal = new RevealTransition(epicenter, smallRadius, bigRadius, duration);
                TransitionSet transition = new TransitionSet();
//                transition.addTransition(reveal);
                transition.addTransition(new Explode());
                Fold fold = new Fold();
                fold.addTarget(R.id.title_container);
                transition.addTransition(fold);

                setEnterTransition(transition);
                setExitTransition(transition);
                mHostActivity.getWindow().getSharedElementEnterTransition()
                        .setDuration(duration);
                mHostActivity.getWindow().getSharedElementExitTransition()
                        .setDuration(duration);


                break;
        }
    }

    @Override
    protected void runTranslatedScript() {
//        MirrorAnimator titleContainerFadeIn = MirrorPropertyAnimator.fromJSON();
//        export(titleContainerFadeIn, "titleContainerFadeIn");
    }
}
