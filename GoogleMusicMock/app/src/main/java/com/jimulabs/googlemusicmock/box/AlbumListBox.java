package com.jimulabs.googlemusicmock.box;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jimulabs.googlemusicmock.AlbumListActivity;
import com.jimulabs.googlemusicmock.R;
import com.jimulabs.mirrorsandbox.MirrorSandboxBase;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lintonye on 15-03-03.
 */
public class AlbumListBox extends MirrorSandboxBase {

    @InjectView(R.id.album_list)
    RecyclerView albumList;
    public AlbumListBox(View rootView) {
        super(rootView);
        ButterKnife.inject(this, rootView);
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        albumList.setLayoutManager(lm);
    }

    @Override
    public void $onLayoutDone(View view) {
    }
}
