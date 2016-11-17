package com.yyyu.mytransitiondemo.library.view;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class Circle{

        private float cX;
        private float cY;
        private int alpha;
        private float drawRadius;
        private Interpolator radiusInterpolator;
        private Interpolator alphaInterpolator;

        public Circle(float cX ,float  cY , float maxRadius){
            this.cX = cX;
            this.cY = cY;
            if(radiusInterpolator == null){
                radiusInterpolator = new AccelerateDecelerateInterpolator();
            }
            if(alphaInterpolator == null){
                alphaInterpolator = new LinearInterpolator();
            }
            ValueAnimator radiusAnim = new ValueAnimator().ofFloat(0, maxRadius);
            radiusAnim.setDuration(1000);
            radiusAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            radiusAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    drawRadius = (float) animation.getAnimatedValue();
                }
            });
            radiusAnim.start();
            ValueAnimator alphaAnim = new ValueAnimator().ofInt(255, 0);
            alphaAnim.setDuration(1000);
            alphaAnim.setInterpolator(new LinearInterpolator());
            alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha = (int) animation.getAnimatedValue();
                }
            });
            alphaAnim.start();
        }

        public void setAlphaInterpolator(Interpolator alphaInterpolator) {
            this.alphaInterpolator = alphaInterpolator;
        }

        public void setRadiusInterpolator(Interpolator radiusInterpolator) {
            this.radiusInterpolator = radiusInterpolator;
        }

        public float getCX() {
            return cX;
        }

        public float getCY() {
            return cY;
        }

        public int getAlpha(){
            return alpha;
        }

        public float getDrawRadius() {
            return drawRadius;
        }
    }