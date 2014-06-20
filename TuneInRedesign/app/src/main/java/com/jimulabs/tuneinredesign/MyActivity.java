package com.jimulabs.tuneinredesign;

import android.app.Activity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MyActivity extends Activity {

    private ViewGroup mNowPlaying;
    private Scene mCurrentScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_container);
        mNowPlaying = (ViewGroup) findViewById(R.id.now_playing);
        final Scene scene1 = Scene.getSceneForLayout(mNowPlaying, R.layout.nowplaying_mini, MyActivity.this);
        final Scene scene2 = Scene.getSceneForLayout(mNowPlaying, R.layout.nowplaying_fullscreen, MyActivity.this);
        final ChangeBounds t = new ChangeBounds();
        scene1.enter();
        mNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentScene = mCurrentScene != scene2 ? scene2 : scene1;
                TransitionManager.go(mCurrentScene, t);
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
