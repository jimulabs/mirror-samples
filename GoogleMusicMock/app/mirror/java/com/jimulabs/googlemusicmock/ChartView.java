package com.jimulabs.googlemusicmock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lintonye on 2014-12-20.
 */
public class ChartView extends View {
    private final Paint mLinePaint;
    private final Paint mCurvePaint;
    private Point[] mPoints;
    private float mSpanY = 1;
    private float mSpanX = 1;
    private Path mPath;
    private int[] mHighlightIndices;
    private List<HighlightDot> mHighlightDots = Collections.emptyList();

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLinePaint = new Paint() {
            {
                setColor(context.getResources().getColor(R.color.line_stroke));
                setStrokeWidth(dp2px(1));
                setStyle(Paint.Style.STROKE);
            }
        };
        mCurvePaint = new Paint(mLinePaint) {
            {
                setColor(context.getResources().getColor(R.color.curve_stroke));
                setStrokeWidth(dp2px(5));
                setStrokeCap(Paint.Cap.ROUND);
                setAntiAlias(true);
            }
        };
    }

    public void setData(Point[] points, int[] highlightIndices) {
        mPoints = points;
        mHighlightIndices = highlightIndices;
        mHighlightDots = createHighlightDots(points, highlightIndices);
        updatePath();
    }

    private List<HighlightDot> createHighlightDots(Point[] points, int[] highlightIndices) {
        List<HighlightDot> dots = new ArrayList<>(highlightIndices.length);
        for (int idx : highlightIndices) {
            Point p = points[idx];
            dots.add(new HighlightDot(this, p.x, p.y));
        }
        return dots;
    }

    public static class HighlightDot {
        public final int x;
        public final int y;
        private final Paint mPaint;
        private final View mOwner;
        private float mRadius = 8;

        public HighlightDot(View owner, int x, int y) {
            this.x = x;
            this.y = y;
            mOwner = owner;
            mPaint = new Paint() {
                {
                    setStyle(Style.STROKE);
                    setStrokeWidth(5);
                    setAntiAlias(true);
                }
            };
        }

        public void setRadius(float radius) {
            mRadius = radius;
            float strokeWidth = Math.max(1, Math.min(5, radius / 2));
            mPaint.setStrokeWidth(strokeWidth);
            mOwner.invalidate();
        }

        public void onDraw(Canvas canvas) {
            canvas.drawCircle(x, y, mRadius, mPaint);
        }
    }

    private Path points2Path(Point... points) {
        int midX = getMeasuredWidth() / 2;
        int midY = computeMidY(points);
        float spanY = getSpanY();
        float spanX = getSpanX();

        Path path = new Path();
        path.moveTo(applySpan(points[0].x, midX, spanX),
                applySpan(points[0].y, midY, spanY));

        for (int i = 1; i < points.length; i++) {
            int x = applySpan(points[i].x, midX, spanX);
            int y = applySpan(points[i].y, midY, spanY);
            path.lineTo(x, y);

//            Point p0 = points[i - 1];
//            Point p = points[i];
//            Point m = midpoint(p0, p);
//            path.quadTo(applySpan(m.x, midX, spanX), applySpan(m.y, midY, spanY),
//                    applySpan(p.x, midX, spanX), applySpan(p.y, midY, spanY));
        }
        return path;
    }

    private int computeMidY(Point[] points) {
        int minY = 10000, maxY = 0;
        for (Point p : points) {
            minY = Math.min(minY, p.y);
            maxY = Math.max(maxY, p.y);
        }
        return (minY + maxY) / 2;
    }

    private int applySpan(int v, int mid, float span) {
        return (int) ((v - mid) * span + mid);
    }

    private Point midpoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }


    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLines(canvas, 8);
        drawCurve(canvas);
        drawHighlightDots(canvas);
    }

    private void drawHighlightDots(Canvas canvas) {
        for (HighlightDot dot:mHighlightDots) {
            dot.onDraw(canvas);
        }
    }

    private void drawCurve(Canvas canvas) {
        if (mPath != null) {
            canvas.drawPath(mPath, mCurvePaint);
        }
    }

    private void drawLines(Canvas canvas, int lineCount) {
        int heightPerLine = getMeasuredHeight() / lineCount;
        int midX = getMeasuredWidth() / 2;
        float spanX = getSpanX();
        for (int i = 0; i < lineCount; i++) {
            float startX = applySpan(0, midX, spanX);
            float startY = i * heightPerLine;
            float stopX = applySpan(getMeasuredWidth(), midX, spanX);
            float stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
        }
    }

    public float getSpanY() {
        return mSpanY;
    }

    public void setSpanY(float spanY) {
        mSpanY = spanY;
        updatePath();
    }

    private void updatePath() {
        mPath = points2Path(mPoints);
        invalidate();
    }

    public float getSpanX() {
        return mSpanX;
    }

    public void setSpanX(float spanX) {
        mSpanX = spanX;
        updatePath();
    }

    public List<HighlightDot> getHighlightDots() {
        return mHighlightDots;
    }
}
