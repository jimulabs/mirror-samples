package com.jimulabs.googlemusicmock.box;

import android.view.View;

import com.jimulabs.googlemusicmock.R;
import com.jimulabs.mirrorsandbox.MirrorSandboxBase;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lintonye on 15-03-03.
 */
public class AlbumListTwowayBox extends MirrorSandboxBase {

    @InjectView(R.id.album_list)
    TwoWayView albumList;

    public AlbumListTwowayBox(View rootView) {
        super(rootView);
        ButterKnife.inject(this, rootView);
        SpannableGridLayoutManager lm = new SpannableGridLayoutManager(TwoWayLayoutManager.Orientation.VERTICAL, 2, 3);
        albumList.setLayoutManager(lm);
    }

    @Override
    public void $onLayoutDone(View view) {

    }
}
