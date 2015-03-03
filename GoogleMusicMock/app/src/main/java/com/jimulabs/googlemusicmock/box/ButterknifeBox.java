package com.jimulabs.googlemusicmock.box;

import android.view.View;

import com.jimulabs.googlemusicmock.ButterknifeView;
import com.jimulabs.googlemusicmock.R;
import com.jimulabs.mirrorsandbox.MirrorAnimatorSandbox;

/**
 * Created by lintonye on 14-12-29.
 */
public class ButterknifeBox extends MirrorAnimatorSandbox {
    public ButterknifeBox(View root) {
        super(root);
    }

    @Override
    public void enterSandbox() {
        ButterknifeView v = (ButterknifeView) $(R.id.butterknife).getView();
        v.setText("hello", "World2");
    }
}
