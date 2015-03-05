package com.jimulabs.googlemusicmock;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import com.jimulabs.googlemusicmock.box.AlbumDetailBox;
import com.jimulabs.motionkit.MirrorAnimator;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AlbumDetailActivity extends Activity {

    public static final String EXTRA_ALBUM_ART_RESID = "EXTRA_ALBUM_ART_RESID";

    @InjectView(R.id.album_art)
    ImageView albumArtView;
    @InjectView(R.id.title_container)
    View cyanPanel;
    @InjectView(R.id.info_container)
    View whitePanel;
    @InjectView(R.id.fab)
    View fab;
    private AlbumDetailBox mBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        ButterKnife.inject(this);
        mBox = new AlbumDetailBox(getWindow().getDecorView());
        populate();
        initTransitions();
    }



    private void initTransitions() {
        final Window window = getWindow();
        window.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBox.enterAnimation().setupStage();
            }
        });

        window.getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                mBox.enterAnimation().start();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void populate() {
        int albumArtResId = getIntent().getIntExtra(EXTRA_ALBUM_ART_RESID, 0);
        albumArtView.setImageResource(albumArtResId);
    }

    @Override
    public void finishAfterTransition() {
        MirrorAnimator mirrorAnimator = mBox.exitAnimation();
        mirrorAnimator.getAnimator().addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                superFinishAfterTransition();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mirrorAnimator.start();
    }

    private void superFinishAfterTransition() {
        super.finishAfterTransition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
