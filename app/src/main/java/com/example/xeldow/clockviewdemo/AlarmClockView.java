package com.example.xeldow.clockviewdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * @description:
 * @author: Xeldow
 * @date: 2019/8/23
 */
public class AlarmClockView extends View {

    /**
     * 取下层非交集部分
     */
    private static final PorterDuff.Mode MODE = PorterDuff.Mode.DST_OUT;
    /**
     * 屏幕适应性参数
     */
    private static final float BIG_RADIUS = 1 / 6F;
    private static final float SMALL_RADIUS = 1 / 12F;
    private static final float CLOCKWISE_LENGTH = 1 / 10F;
    private static final float MINUTE_LENGTH = 1 / 12f;
    /**
     * 用于遮挡部分圆
     */
    private static final float MASK_RADIUS = 2 / 10F;

    // 大圆盘圈
    private Paint bigCirclePaint;
    // 指针Paint
    private Paint handPaint;
    // 小圆Paint
    private Paint smallCirclePaint;
    private Path path;
    private Path rootPath;
    private int screenW, screenH;
    /**
     * 大圆半径
     */
    private float radius_big, small_radius, clockwise_length, minute_length,
            mask_radius;

    public AlarmClockView(Context context) {
        super(context);
    }

    public AlarmClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //抗锯齿
        bigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigCirclePaint.setStyle(Paint.Style.STROKE);
        bigCirclePaint.setColor(Color.WHITE);
        bigCirclePaint.setStrokeWidth(40);

        handPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        handPaint.setStyle(Paint.Style.STROKE);
        handPaint.setColor(Color.WHITE);
        handPaint.setStrokeWidth(20);

        smallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallCirclePaint.setStyle(Paint.Style.FILL);
        smallCirclePaint.setColor(Color.WHITE);

        screenW = MeasureUtil.getWindowWidth((Activity) context);
        screenH = MeasureUtil.getWindowHeight((AppCompatActivity) context);

        int size = Math.min(screenW, screenH);
        radius_big = size * BIG_RADIUS;
        small_radius = size * SMALL_RADIUS;
        clockwise_length = size * CLOCKWISE_LENGTH;
        minute_length = size * MINUTE_LENGTH;
        mask_radius = size * MASK_RADIUS;
        // 设置指针
        path = new Path();
        path.moveTo(screenW / 2 - 5, screenH / 2 + 15);//起点
        path.lineTo(screenW / 2 - 5, screenH / 2 - clockwise_length + 15);
        path.moveTo(screenW / 2 - 15, screenH / 2 + 15);
        path.lineTo(screenW / 2 + minute_length - 5, screenH / 2 + 15);

        rootPath = new Path();
        rootPath.moveTo(screenW / 2 - radius_big / 2, screenH / 2 + radius_big);
        rootPath.lineTo(screenW / 2 - radius_big / 2 + 5, screenH / 2 + radius_big + 5);
        rootPath.moveTo(screenW / 2 + radius_big / 2, screenH / 2 + radius_big);
        rootPath.lineTo(screenW / 2 + radius_big / 2 - 5, screenH / 2 + radius_big + 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.argb(255, 255, 128, 103));

        canvas.drawCircle(screenW / 2, screenH / 2, radius_big, bigCirclePaint);
        canvas.drawPath(path, handPaint);

        // 画耳朵圆
        drawEarCircle(canvas, 30, -50);
        drawEarCircle(canvas, -30, 50);
        //画闹钟的脚
//        canvas.drawPath(rootPath, handPaint);
        canvas.drawLine(
                screenW / 2 - radius_big / 2,
                screenH / 2 + radius_big - 10,
                screenW / 2 - radius_big / 2 - 30,
                screenH / 2 + radius_big + 20, handPaint);
        canvas.drawLine(
                screenW / 2 + radius_big / 2,
                screenH / 2 + radius_big - 10,
                screenW / 2 + radius_big / 2 + 30,
                screenH / 2 + radius_big + 20, handPaint);
    }

    private void drawEarCircle(Canvas canvas, int degree, int offset) {
        int layerID = canvas.saveLayer(0, 0, screenW, screenH, null,
                Canvas.ALL_SAVE_FLAG);//保留全部的状态
        //先保存了之前的状态所以现在才敢旋转
        canvas.rotate(degree, screenW / 2, screenH / 2);
        canvas.drawCircle(screenW / 2 - offset, screenH / 2 - radius_big - (small_radius / 3),
                small_radius, smallCirclePaint);
        // 用一个大圆去遮罩
        PorterDuffXfermode xfermode = new PorterDuffXfermode(MODE);
        smallCirclePaint.setXfermode(xfermode);
        canvas.drawCircle(screenW / 2, screenH / 2, mask_radius,
                smallCirclePaint);
        smallCirclePaint.setXfermode(null);
        canvas.restoreToCount(layerID);//回到save之前的状态
    }

}
