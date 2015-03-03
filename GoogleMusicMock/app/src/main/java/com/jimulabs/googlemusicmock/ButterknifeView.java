package com.jimulabs.googlemusicmock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lintonye on 14-12-29.
 */
public class ButterknifeView extends LinearLayout {

    @InjectView(R.id.text1)
    TextView textView1;

    @InjectView(R.id.text2)
    TextView textView2;

    public ButterknifeView(Context context) {
        this(context, null);
    }

    public ButterknifeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setText(String s1, String s2) {
        textView1.setText(s1);
        textView2.setText(s2);
    }

    public ButterknifeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        inflate(context, R.layout.butterknife_view, this);
        ButterKnife.inject(this);
    }
}
