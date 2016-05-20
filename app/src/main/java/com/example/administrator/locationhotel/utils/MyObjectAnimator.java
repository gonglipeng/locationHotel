package com.example.administrator.locationhotel.utils;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class MyObjectAnimator {

    public static void rotationX2(View view){
        ObjectAnimator//
                .ofFloat(view, "rotationX", 180.0F, 360.0F)//
                .setDuration(500)//
                .start();
    }
    public static void rotationX1(View view){
        ObjectAnimator//
                .ofFloat(view, "rotationX", 0.0F, 180.0F)//
                .setDuration(500)//
                .start();
    }
}
