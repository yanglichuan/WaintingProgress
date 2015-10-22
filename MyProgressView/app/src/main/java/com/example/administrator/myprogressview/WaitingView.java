package com.example.administrator.myprogressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class WaitingView extends View {
    private Context mContext = null;
    public WaitingView(Context context) {
        super(context);
        init(context, null);
    }
    public WaitingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public WaitingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Paint paint;
    public void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);

        for (int i = 0; i < itemNum; i++) {
            VItem item = new VItem();
            item.color = Color.RED;
            vItems.add(item);
        }
    }

    private void startAnim(final int maxH) {
        valueAnimator1 = ValueAnimator.ofFloat(20, maxH);
        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator1.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator1.setDuration(600);
        valueAnimator2 = valueAnimator1.clone();
        valueAnimator3 = valueAnimator1.clone();
        valueAnimator4 = valueAnimator1.clone();
        valueAnimator5 = valueAnimator1.clone();

        ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ff = (float) animation.getAnimatedValue();
                if (animation == valueAnimator1) {
                    vItems.get(0).height = (int) ff;
                } else if (animation == valueAnimator2) {
                    vItems.get(1).height = (int) ff;
                } else if (animation == valueAnimator3) {
                    vItems.get(2).height = (int) ff;
                } else if (animation == valueAnimator4) {
                    vItems.get(3).height = (int) ff;
                } else if (animation == valueAnimator5) {
                    vItems.get(4).height = (int) ff;
                }
                invalidate();
            }
        };
        valueAnimator1.addUpdateListener(updateListener);
        valueAnimator2.addUpdateListener(updateListener);
        valueAnimator3.addUpdateListener(updateListener);
        valueAnimator4.addUpdateListener(updateListener);
        valueAnimator5.addUpdateListener(updateListener);
        valueAnimator1.start();
        valueAnimator2.setStartDelay(100);
        valueAnimator2.start();
        valueAnimator3.setStartDelay(200);
        valueAnimator3.start();
        valueAnimator4.setStartDelay(300);
        valueAnimator4.start();
        valueAnimator5.setStartDelay(400);
        valueAnimator5.start();
    }


    private ValueAnimator valueAnimator1;
    private ValueAnimator valueAnimator2;
    private ValueAnimator valueAnimator3;
    private ValueAnimator valueAnimator4;
    private ValueAnimator valueAnimator5;


    private ArrayList<VItem> vItems = new ArrayList<>();


    private int getpx(int idp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * idp + 0.5f);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modew = MeasureSpec.getMode(widthMeasureSpec);
        int modeh = MeasureSpec.getMode(heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int rightW = 0;
        int rightH = 0;
        if (modew == MeasureSpec.EXACTLY) {
            rightW = w;
        } else {
            rightW = getPaddingLeft() + getPaddingRight() + getpx(30);
        }

        if (modeh == MeasureSpec.EXACTLY) {
            rightH = h;
        } else {
            rightH = getPaddingTop() + getPaddingBottom() + getpx(30);
        }
        setMeasuredDimension(rightW, rightH);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int w = getMeasuredWidth();
        final int h = getMeasuredHeight();
        int leftx = 0;
        int topy = 0;
        int rightx = w;
        int bottomy = h;

        //这是每一个的宽度
        int perW = w / (itemNum * 2 + (itemNum - 1) * 1);


        VItem v0 = vItems.get(0);
        v0.setHeight(20);
        v0.wight = perW * 2;
        v0.centerX = perW;
        v0.centerY = h / 2;
        v0.color = Color.CYAN;

        VItem v1 = vItems.get(1);
        v1.setHeight(20);
        v1.wight = perW * 2;
        v1.centerX = perW + 3 * perW;
        v1.centerY = h / 2;
        v1.color = Color.BLUE;

        VItem v2 = vItems.get(2);
        v2.setHeight(20);
        v2.wight = perW * 2;
        v2.centerX = perW + 6 * perW;
        v2.centerY = h / 2;
        v2.color = Color.DKGRAY;

        VItem v3 = vItems.get(3);
        v3.setHeight(20);
        v3.wight = perW * 2;
        v3.centerX = perW + 9 * perW;
        v3.centerY = h / 2;
        v3.color = Color.LTGRAY;

        VItem v4 = vItems.get(4);
        v4.setHeight(20);
        v4.wight = perW * 2;
        v4.centerX = perW + 12 * perW;
        v4.centerY = h / 2;
        v4.color = Color.MAGENTA;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim(h);
            }
        }, 300);
    }

    private Handler mHandler = new Handler();

    private int itemNum = 5;//一共5个条目

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < vItems.size(); i++) {
            VItem item = vItems.get(i);
            paint.setColor(item.color);
            canvas.drawRect(item.getRect(), paint);
        }
    }

    class VItem {
        int color = Color.RED;
        int centerX;
        int centerY;
        private int height;
        int wight;

        public void setHeight(int h) {
            this.height = h;
        }

        public int getHeight() {
            return height;
        }

        public int getLeftX() {
            return centerX - wight / 2;
        }

        public int getTopy() {
            return centerY - height / 2;
        }

        public int getRightX() {
            return centerX + wight / 2;
        }

        public int getBottomy() {
            return centerY + height / 2;
        }

        public Rect getRect() {
            return new Rect(getLeftX(), getTopy(), getRightX(), getBottomy());
        }
    }
}
