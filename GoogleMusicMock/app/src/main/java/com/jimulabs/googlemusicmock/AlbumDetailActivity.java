package com.jimulabs.googlemusicmock;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.PatternPathMotion;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.jimulabs.lens.Fold;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AlbumDetailActivity extends Activity {

    public static final String EXTRA_ALBUM_ART_RESID = "EXTRA_ALBUM_ART_RESID";

    @InjectView(R.id.album_art)
    ImageView albumArtView;
    @InjectView(R.id.title_container)
    View titleContainer;

    private Lens mLens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        mLens = new Lens(this, R.layout.activity_album_detail);
        mLens.init();
        ButterKnife.inject(this);
        populate();
    }

    private void populate() {
        int albumArtResId = getIntent().getIntExtra(EXTRA_ALBUM_ART_RESID, 0);
        albumArtView.setImageResource(albumArtResId);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        AnimatorScript_AlbumDetails.enter().start();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
