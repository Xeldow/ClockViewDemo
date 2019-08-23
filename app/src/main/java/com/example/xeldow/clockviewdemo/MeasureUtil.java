package com.example.xeldow.clockviewdemo;

/**
 * @description:
 * @author: Xeldow
 * @date: 2019/8/23
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;

/**
 * 常用的测量功能
 */
public class MeasureUtil {
    //获取屏幕宽度


    public static int getWindowWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);


        //窗口的宽度
        int screenWidth = dm.widthPixels;


        return screenWidth;
    }

    //获取屏幕高度


    public static int getWindowHeight(AppCompatActivity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);


//窗口高度
        int screenHeight = dm.heightPixels;


        return screenHeight;
    }

}