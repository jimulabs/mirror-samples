package com.jimulabs.googlemusicmock.box;

import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.jimulabs.googlemusicmock.ChartView;
import com.jimulabs.googlemusicmock.R;
import com.jimulabs.mirrorsandbox.MirrorAnimator;
import com.jimulabs.mirrorsandbox.MirrorAnimatorSandbox;
import com.jimulabs.mirrorsandbox.MirrorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by lintonye on 14-12-24.
 */
public class SneakPeekBox extends MirrorAnimatorSandbox {
    public SneakPeekBox(View root) {
        super(root);
    }

    @Override
    public void enterSandbox() {
//        $(R.id.text1).scale(0, 3, 1)
//                .duration(1000)
//                .interpolator(new BounceInterpolator())
//                .followedBy($(R.id.text2).alpha(0, 1))
//                .start();
        fillViewsWithMockData();
        setGlobalSpeed(0.2);
        expandY().start();
//        animateChart();
//        happyNewYear();
    }

    private ChartView fillViewsWithMockData() {
        showHotswapping();
        ChartView chart = (ChartView) $(R.id.chart).getView();
        Point[] points = createSamplePoints();
        chart.setData(points, new int[]{1, 5, 7, 11, 15, 18});
        return chart;
    }

    public MirrorAnimator showHighlights(List<ChartView.HighlightDot> dots) {
        Collections.sort(dots, new Comparator<ChartView.HighlightDot>() {
            @Override
            public int compare(ChartView.HighlightDot lhs, ChartView.HighlightDot rhs) {
                return lhs.x - rhs.x;
            }
        });

        List<MirrorAnimator> animators = new ArrayList<>(dots.size());
        for (int i = 0; i < dots.size(); i++) {
            MirrorAnimator showDot = wrapToAnimate(dots.get(i)).animator("radius", 0, 5f).duration(200);
            animators.add(showDot.startDelay(150 * i));
        }

        return together(animators);
    }

    private int[] createHighlightIndices(int highlightCount, int totalCount) {
        int[] result = new int[highlightCount];
        Random r = new Random();
        for (int i = 0; i < highlightCount; i++) {
            result[i] = r.nextInt(totalCount);
        }
        return result;
    }

    public MirrorAnimator exit(List<ChartView.HighlightDot> dots) {
        return sq(hideDots(dots), shrinkY(), shrinkX()).startDelay(1000);
    }

    public MirrorAnimator hideDots(List<ChartView.HighlightDot> dots) {
        List<MirrorAnimator> animators = new ArrayList<>(dots.size());
        for (int i = 0; i < dots.size(); i++) {
            MirrorAnimator showDot = wrapToAnimate(dots.get(i)).animator("radius", 8f, 0f).duration(200);
            animators.add(showDot);
        }
        return together(animators);
    }

    public MirrorAnimator enter(List<ChartView.HighlightDot> dots) {
        return sq(expandX(), expandY(), showHighlights(dots));
    }

    private void showHotswapping() {
        $(R.id.text1).scale(1).start();
        $(R.id.text2).alpha(1).start();
    }

    private void showSubtitle() {
        $(R.id.subtitle).scale(1).start();
    }

    private void happyNewYear() {
        showHotswapping();
        together($(R.id.white_bg).alpha(1, 0), $(R.id.fireworks).alpha(0, 1)).duration(1000).start();
    }

    private void animateChart() {
        ChartView chart = fillViewsWithMockData();
        List<ChartView.HighlightDot> dots = chart.getHighlightDots();

        enter(dots).start();
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
        return $(R.id.chart).animator("spanX", startSpanX, endSpanX).duration(1100);
    }

    private MirrorAnimator spanY(float startSpanY, float endSpanY) {
        return $(R.id.chart).animator("spanY", startSpanY, endSpanY).duration(1100);
    }

    private Point[] createSamplePoints() {
        Point[] points = {new Point(5, 415),
                new Point(42, 352),
                new Point(79, 418),
                new Point(116, 389),
                new Point(153, 381),
                new Point(190, 256),
                new Point(227, 439),
                new Point(264, 311),
                new Point(301, 410),
                new Point(338, 312),
                new Point(375, 418),
                new Point(412, 317),
                new Point(449, 334),
                new Point(486, 278),
                new Point(523, 437),
                new Point(560, 405),
                new Point(597, 367),
                new Point(634, 272),
                new Point(671, 331),
                new Point(708, 294),
        };
        return points;
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
        for (Point p : points) {
            Log.d("sneakpeek", p.toString());
        }
        return points;
    }
}
