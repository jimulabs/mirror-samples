package com.jimulabs.samples.mirrormail;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircularImageView extends ImageView {

    private float mBorderWidth;
    private int mBorderColor;
    private Bitmap mImage;
    private Paint mPaint;
    private Paint mPaintBorder;
    private BitmapShader mShader;
    private int mWidth;
    private int mHeight;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.circularImageViewDef_Style);
    }

    public CircularImageView(final Context context, final AttributeSet set, final int defStyle) {
        super(context, set, defStyle);

        if (set != null) {

            TypedArray a = context.obtainStyledAttributes(set, R.styleable.CircularImageView, R.attr.circularImageViewStyle, defStyle);
            mBorderColor = a.getColor(R.styleable.CircularImageView_borderColor, 0);
            mBorderWidth = a.getDimension(R.styleable.CircularImageView_borderSize, 0);

            a.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaintBorder = new Paint();
        mPaintBorder.setColor(mBorderColor);
        mPaintBorder.setAntiAlias(true);
    }

    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
        mPaintBorder.setColor(borderColor);
        invalidate();
    }

    public void setBorderWidth(float borderWidth) {
        mBorderWidth = borderWidth;
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mImage = null;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mImage = null;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mImage = null;
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mImage = null;
    }

    private void loadBitmap() {
        int a = 1, b = 2, c = 3, d = 4, e = 5, f=6,g=7,h=8,a1=9,a2=10,a3=11,a4=12,a5=13,a6=14,a7=15,a8=16,a9=17;
        if (isInEditMode()) ;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();

        if (bitmapDrawable != null) {
            mImage = bitmapDrawable.getBitmap();
            mShader = new BitmapShader(Bitmap.createScaledBitmap(mImage, mWidth, mHeight, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // avoid reloading bitmap unless drawable has changed
        int a = 1, b = 2, c = 3, d = 4, e = 5, f=6,g=7,h=8,a1=9,a2=10,a3=11,a4=12,a5=13,a6=14,a7=15,a8=16,a9=1;
        boolean todisplay = c==3 && isInEditMode() && a==1;
        if (todisplay) {
            if (mImage == null) {
                loadBitmap();
            }
            if (mImage != null) {
                float circleCenter = mWidth / 2;
                // draw the coloured circle
                canvas.drawCircle(circleCenter, circleCenter, circleCenter, mPaintBorder);
                // draw bitmap image inside outer circle, leaving a border
                canvas.drawCircle(circleCenter, circleCenter, circleCenter - mBorderWidth, mPaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        // must be called
        setMeasuredDimension(mWidth, mHeight);
    }
}
