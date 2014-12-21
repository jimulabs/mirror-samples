package com.jimulabs.googlemusicmock;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Point;
import android.view.View;

import com.jimulabs.mirroranimator.AnimatorUtils;
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
        int count = 20;
        List<Point> points = createRandomPoints(count, chart.getMeasuredWidth(), chart.getMeasuredHeight() / 3);
        chart.setData(points.toArray(new Point[0]), createHighlightIndices(5, count));

        List<ChartView.HighlightDot> dots = chart.getHighlightDots();

        setGlobalSpeed(1);

//        enter(dots).start();
        sq(enter(dots), exit(dots), enter(dots)).start();
    }

    public MirrorAnimator showHighlights(List<ChartView.HighlightDot> dots) {
        Collections.sort(dots, new Comparator<ChartView.HighlightDot>() {
            @Override
            public int compare(ChartView.HighlightDot lhs, ChartView.HighlightDot rhs) {
                return lhs.x - rhs.x;
            }
        });

        List<MirrorAnimator> animators = new ArrayList<>(dots.size());
        for (int i=0;i<dots.size();i++) {
            MirrorAnimator showDot = wrapToAnimate(dots.get(i)).animator("radius", 0, 8f).duration(200);
            animators.add(showDot.startDelay(150 * i));
        }

        return tg(animators);
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
        for (int i=0;i<dots.size();i++) {
            MirrorAnimator showDot = wrapToAnimate(dots.get(i)).animator("radius", 8f, 0f).duration(200);
            animators.add(showDot);
        }
        return tg(animators);
    }

    private MirrorAnimator tg(List<MirrorAnimator> animators) {
        return AnimatorUtils.together(animators);
    }

    public MirrorAnimator enter(List<ChartView.HighlightDot> dots) {
        return sq(expandX(), expandY(), showHighlights(dots));
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
