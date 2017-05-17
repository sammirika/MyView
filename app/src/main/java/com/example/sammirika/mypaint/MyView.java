package com.example.sammirika.mypaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Sammirika on 2017/5/17.
 */

public class MyView extends View {
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int mLastX;
    private int mLastY;

    public MyView(Context context){
        super(context);
        init();
    }
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x= (int) event.getX();
        int y = (int) event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX,mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x-mLastX);
                int dy = Math.abs(y-mLastY);
                if(dx>3 || dy>3)
                    mPath.lineTo(x,y);
                mLastX =x;
                mLastY =y;
                break;
        }
        invalidate();
        return  true;
    }

    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合处为圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 设置转弯处为圆角
        mPaint.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPath();
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int higth = getMeasuredHeight();
        mBitmap = Bitmap.createBitmap(width, higth, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }
    private void drawPath(){
        mCanvas.drawPath(mPath,mPaint);
    }

}
