package com.jimulabs.googlemusicmock;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;

import com.jimulabs.mirroranimator.MirrorAnimator;
import com.jimulabs.mirroranimator.MirrorAnimatorScript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by lintonye on 2014-12-20.
 */
public class ChartScript extends MirrorAnimatorScript {
    public ChartScript(Context context, View root) {
        super(context, root);
    }

    public ChartScript(View root) {
        super(root);
    }

    @Override
    protected void enterSandbox() {
        ChartView chart = (ChartView) $("chart").getView();
//        chart.setSpanY(0.2f);
        List<Point> points = createRandomPoints(20, chart.getMeasuredWidth(), chart.getMeasuredHeight()/3);
        chart.setData(points.toArray(new Point[0]));

        sq(exit(), enter()).start();
//        sq(exit(), enter(), exit(), enter()).start();
//        enter().start();
//        exit().start();
    }

    public MirrorAnimator exit() {
        return sq(shrinkY(), shrinkX());
    }

    public MirrorAnimator enter() {
        return sq(expandX(), expandY());
    }

    public MirrorAnimator shrinkX() {
        return spanX(1f, 0f);
    }

    public MirrorAnimator shrinkY() {
        return spanY(1f, 0f);
    }

    public MirrorAnimator expandX() {
        return spanX(0f, 1f);
    }

    public MirrorAnimator expandY() {
        return spanY(0f, 1f);
    }

    private MirrorAnimator spanX(float startSpanX, float endSpanX) {
        return $("chart").animator("spanX", startSpanX, endSpanX).duration(1100);
    }

    private MirrorAnimator spanY(float startSpanY, float endSpanY) {
        return $("chart").animator("spanY", startSpanY, endSpanY).duration(1100);
    }

    private List<Point> createRandomPoints(int count, int maxX, int maxY) {
        List<Point> points = new ArrayList<>(count);
        Random random = new Random();
        int offsetY = maxY;
        int marginX = 5;
        for (int i = 0; i < count; i++) {
            int x = marginX + i * ((maxX + 2 * marginX) / (count - 1));
            int y = random.nextInt(maxY) + offsetY;
            points.add(new Point(x, y));
        }
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point lhs, Point rhs) {
                return lhs.x - rhs.x;
            }
        });
        return points;
    }
}
