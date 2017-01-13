package com.ieuphiee.fractal;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by demouser on 1/13/17.
 */

public class FractalView extends View {

    private static final double X_MAX = 1;
    private static final double X_MIN = -2.5;
    private static final double Y_MAX = 1.75;
    private static final double Y_MIN = -1.75;

    private double mXMin = X_MIN;
    private double mXMax = X_MAX;
    private double mYMin = Y_MIN;
    private double mYMax = Y_MAX;


    private Paint mBackgroundPaint;
    private Paint mPaint;

    private int[] mColors;
    private int numPoints = 100;
    private int mMaxDepth;

    public FractalView(Context context) {
        super(context);
        init();
    }

    public FractalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FractalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FractalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(getResources().getColor(R.color.background_color));

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.rgb(25, 50, 75));
        mPaint.setStrokeWidth(12);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec == 0 || heightMeasureSpec == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        int size = Math.max(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(size, size);
    }

    /**
     * Define the colors to use, based on the depth.
     * This will mean less calculation during onDraw!
     */
    private void initializeColors() {
        mColors = new int[mMaxDepth + 1];
        for (int i = 0; i < mMaxDepth; i++) {
            int r = (int) (255 * (i * 1.0 / mMaxDepth));
            int g = (int) (255 * (1 - i * 1.0 / mMaxDepth));
            mColors[i] = Color.rgb(r, g, 255);
        }
        mColors[mMaxDepth] = Color.BLACK;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), (mBackgroundPaint));

        if (mColors == null || mColors.length == 0) {
            return;
        }

        int dotSize = getWidth() / numPoints;

        for (int i = 0; i < numPoints; i++) {
            double mX = (i * 1.0) / numPoints * (mXMax - mXMin) + mXMin;
            float x  = (i * 1.0f) / numPoints * getWidth();
            for (int j = 0; j < numPoints; j++) {
                double mY = (j * 1.0) / numPoints * (mYMax - mYMin) + mYMin;
                float y = (j * 1.0f) / numPoints * getHeight();

                mPaint.setColor(getMandelbrotColor(mX, mY));

                canvas.drawCircle(x, y, dotSize, mPaint);
            }
        }
    }

    private int getMandelbrotColor(double mX, double mY) {
        double x = 0;
        double y = 0;
        int iter = 0;
        while (x*x + y*y < 4 && iter < mMaxDepth) {
            double temp = x * x - y * y + mX;
            y = 2 * x * y + mY;
            x = temp;
            iter++;
        }

        return mColors[iter];
    }


    public void reset(int depth) {
        mMaxDepth = depth;
        mXMin = X_MIN;
        mXMax = X_MAX;
        mYMin = Y_MIN;
        mYMax = Y_MAX;
        initializeColors();
        postInvalidateOnAnimation();
    }




}
