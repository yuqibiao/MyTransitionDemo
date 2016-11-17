package com.yyyu.mytransitiondemo.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：扩散效果的View
 * <p/>
 * Created by yyyu on 2016/11/17.
 */
public class DiffusesView extends View {

    private static final String TAG = "DiffusesView";

    /**
     * 扩散波纹的颜色
     */
    private int diffusesColor = 0xFFFF4081;
    private Paint mPaint;
    private int maxRadius;
    /**
     * 圆形波浪中心点
     */
    private float cX;
    private float cY;
    private List<Circle> circleList = new ArrayList();

    public DiffusesView(Context context) {
        this(context, null);
    }

    public DiffusesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiffusesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "DiffusesView: ");
        mPaint = new Paint();
        mPaint.setColor(diffusesColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{

                break;
            }
            case MotionEvent.ACTION_UP:{
                Log.e(TAG, "onTouchEvent: event.getX()="+event.getX()+"event.getX()="+event.getRawX() );
                circleList.add(new Circle(event.getX() , event.getY() , maxRadius));
                break;
            }
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure: ");
        maxRadius = (int) Math.hypot(getMeasuredWidth(), getMeasuredHeight());
        cX = getMeasuredWidth() / 2;
        cY = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0;i <circleList.size() ; i++){
            Circle bean = circleList.get(i);
            if(bean.getDrawRadius()>=maxRadius){
                circleList.remove(bean);
            }
            mPaint.setAlpha(bean.getAlpha());
            canvas.drawCircle(bean.getCX() , bean.getCY() ,bean.getDrawRadius(),mPaint);
        }
        if(circleList.size()>=0){
            postInvalidateDelayed(10);
        }
    }

}
