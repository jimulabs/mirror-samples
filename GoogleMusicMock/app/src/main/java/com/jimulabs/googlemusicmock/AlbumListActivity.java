package com.jimulabs.googlemusicmock;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimulabs.googlemusicmock.box.AlbumListBox;
import com.jimulabs.mirrorsandbox.mockdata.MockData;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AlbumListActivity extends ActionBarActivity {
    public static Object sHook;

    @InjectView(R.id.album_list)
    RecyclerView mAlbumList;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    private AlbumListBox mAlbumListBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        ButterKnife.inject(this);
        mToolbar.inflateMenu(R.menu.menu_album_list);
        mAlbumListBox = new AlbumListBox(getWindow().getDecorView());
        populate();
    }

    interface OnVHClickedListener {
        void onVHClicked(AlbumVH vh);
    }

    static class AlbumVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnVHClickedListener mListener;
        @InjectView(R.id.album_art)
        ImageView albumArt;
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.artist)
        TextView artist;

        public AlbumVH(View itemView, OnVHClickedListener listener) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onVHClicked(this);
        }
    }

    private void populate() {
        final int[] albumArts = {R.drawable.christina,
                R.drawable.ellie,
                R.drawable.foster,
                R.drawable.keane,
                R.drawable.kodaline,
                R.drawable.pinkrobots,};
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<AlbumVH>() {
            @Override
            public AlbumVH onCreateViewHolder(ViewGroup parent, int viewType) {
                View albumView = getLayoutInflater().inflate(R.layout.album_grid_item, parent, false);
                return new AlbumVH(albumView, new OnVHClickedListener() {
                    @Override
                    public void onVHClicked(AlbumVH vh) {
                        int albumArtResId = albumArts[vh.getPosition() % albumArts.length];
                        Intent intent = new Intent(AlbumListActivity.this, AlbumDetailActivity.class);
                        intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_ART_RESID, albumArtResId);

                        View sharedView = vh.albumArt;
                        ActivityOptions ops = ActivityOptions.makeSceneTransitionAnimation(AlbumListActivity.this, sharedView, "albumArt");
                        startActivity(intent, ops.toBundle());
                    }
                });
            }

            @Override
            public void onBindViewHolder(AlbumVH holder, int position) {
                holder.albumArt.setImageResource(albumArts[position % albumArts.length]);
                MockData md = new MockData();
                holder.name.setText(md.phrase());
                holder.artist.setText(md.personName());
            }

            @Override
            public int getItemCount() {
                return albumArts.length * 4;
            }

        };
        mAlbumList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_list, menu);
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
