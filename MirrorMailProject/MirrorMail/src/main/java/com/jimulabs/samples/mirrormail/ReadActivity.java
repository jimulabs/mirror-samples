package com.jimulabs.samples.mirrormail;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.android.debug.hv.ViewServer;

public class ReadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        setTitle(R.string.read_activity_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_menu, menu);
        return true;
    }
}
