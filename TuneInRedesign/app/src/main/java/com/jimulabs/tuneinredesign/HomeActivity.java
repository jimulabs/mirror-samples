package com.jimulabs.tuneinredesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeActivity extends Activity {

    private ViewGroup mNowPlaying;
    private Scene mCurrentScene;
    private View mStopButton;
    private TextView mTitle;
    private TextView mSubTitle;
    private ImageView mThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_container);
        mTitle = (TextView)findViewById(R.id.title);
        mSubTitle = (TextView)findViewById(R.id.subtitle);
        mThumbnail = (ImageView)findViewById(R.id.thumbnail);
        mTitle.setText("Populated Title");
        mSubTitle.setText("Populated subtitle");
        mThumbnail.setImageResource(R.drawable.ic_launcher);


        mNowPlaying = (ViewGroup) findViewById(R.id.nowplaying_container);
        final Scene scene1 = Scene.getSceneForLayout(mNowPlaying, R.layout.nowplaying_mini, HomeActivity.this);
        final Scene scene2 = Scene.getSceneForLayout(mNowPlaying, R.layout.nowplaying_fullscreen, HomeActivity.this);
        final ChangeBounds t = new ChangeBounds();
//        scene1.enter();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                TransitionManager.go(scene2, t);
//            }
//        }, 500);
        mNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mCurrentScene = mCurrentScene != scene2 ? scene2 : scene1;
//                TransitionManager.go(mCurrentScene, t);
                TransitionManager.beginDelayedTransition(mNowPlaying);
                mStopButton.setVisibility(View.GONE);
            }
        });
        mStopButton = findViewById(R.id.stop);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NowPlayingActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
