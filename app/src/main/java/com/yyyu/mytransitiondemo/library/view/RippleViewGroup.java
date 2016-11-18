package com.yyyu.mytransitiondemo.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.yyyu.mytransitiondemo.library.bean.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：水波纹效果
 *
 *
 * Created by yyyu on 2016/11/18.
 */
public class RippleViewGroup extends FrameLayout{

    private static final String TAG = "ClickViewWrapper";
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

    private static final int LONG_PRESS = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LONG_PRESS){
                if (mOnLocLongClickListener != null){
                    mOnLocLongClickListener.onLongClick(msg.arg1 , msg.arg2);
                }
            }
        }
    };


    public RippleViewGroup(Context context) {
        super(context , null);
    }

    public RippleViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public RippleViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(diffusesColor);
        setOnLocClickListener(new OnLocClickListener() {
            @Override
            public void onClick(float x, float y) {
                circleList.add(new Circle(x ,y , maxRadius));
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Message msg = new Message();
                msg.what = LONG_PRESS;
                msg.arg1 = (int) event.getX();
                msg.arg2 = (int) event.getY();
                mHandler.removeMessages(LONG_PRESS);
                mHandler.sendMessageDelayed(msg, 1000);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isUpInView(event)){
                    mHandler.removeMessages(LONG_PRESS);
                }
                break;
            case MotionEvent.ACTION_UP:
                mHandler.removeMessages(LONG_PRESS);
                if(isUpInView(event)){
                    if (mOnLocClickListener != null){
                        mOnLocClickListener.onClick(event.getX(),event.getY());
                    }
                }
                break;
        }
        return true;
    }
    private boolean isUpInView(MotionEvent event){
        boolean isInView;
        float upRawX = event.getRawX();
        float upRawY = event.getRawY();
        int[] location = new int[2];
        getLocationOnScreen(location);
        if(upRawX<location[0] || upRawX>location[0]+getMeasuredWidth()
                || upRawY<location[1] || upRawY>location[1]+getMeasuredHeight()){
            isInView = false;
        }else{
            isInView = true;
        }
        return isInView;
    }

    private OnLocLongClickListener mOnLocLongClickListener;

    public void setOnLocLongClickListener(OnLocLongClickListener onLocLongClickListener){
        this.mOnLocLongClickListener = onLocLongClickListener;
    }

    public interface  OnLocLongClickListener{
        void onLongClick(float x , float y);
    }

    private OnLocClickListener mOnLocClickListener;

    public void setOnLocClickListener(OnLocClickListener onLocClickListener){
        this.mOnLocClickListener = onLocClickListener;
    }

    public interface OnLocClickListener{
        void onClick(float x , float y);
    }

}
