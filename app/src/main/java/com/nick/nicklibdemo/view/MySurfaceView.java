package com.nick.nicklibdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nick on 2022/9/30 14:56.
 */
public class MySurfaceView extends SurfaceView {

    private Paint mPaint;
    private List<Integer> mColors = Arrays.asList(Color.GRAY,Color.BLUE,Color.RED,Color.YELLOW,Color.WHITE);
    private List<Bubble> mBubbles = new ArrayList<>();
    private List<Bubble> mTempBubbles = new ArrayList<>();
    private boolean mStopFlag = false;

    public MySurfaceView(Context context) {
        this(context,null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        LogUtils.i("创建完画笔了");
        Observable.create(Void ->{
            while (true){
                if (mStopFlag){
                    break;
                }
                SurfaceHolder holder = getHolder();
                //如果holder已经准备好了再执行
                if (holder.getSurface().isValid()){
                    Canvas canvas = holder.lockCanvas();
                    canvas.drawColor(Color.WHITE);
                    mTempBubbles.clear();
                    mTempBubbles.addAll(mBubbles);
                    for (Bubble bubble : mTempBubbles) {
                        if (bubble.r>3000f){
                            continue;
                        }
                        float r = bubble.getR();
                        mPaint.setColor(bubble.getColor());
                        canvas.drawCircle(bubble.getX(),bubble.getY(),r,mPaint);
                        bubble.setR(r + 10f);
                    }
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int i = new Random().nextInt(5);
        Bubble bubble = new Bubble(event.getX(), event.getY(), 10f, mColors.get(i));
        if (mBubbles.size()>30){
            mBubbles.remove(0);
        }
        mBubbles.add(bubble);
        LogUtils.i("收到点击,当前泡泡长度是>>>>"+mBubbles.size());
        LogUtils.i("颜色是"+i);
        return super.onTouchEvent(event);
    }

    public void stop(){
        mStopFlag = true;
    }


    private static class Bubble{
        private float x;
        private float y;
        private float r;
        private int color;

        public Bubble(float x, float y, float r, int color) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.color = color;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getR() {
            return r;
        }

        public void setR(float r) {
            this.r = r;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
