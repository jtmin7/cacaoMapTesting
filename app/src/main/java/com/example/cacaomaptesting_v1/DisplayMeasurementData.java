package com.example.cacaomaptesting_v1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;
import java.util.ArrayList;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.AttributeSet;

public class DisplayMeasurementData extends View{
    private Paint paint;

    private int left = 100;
    private int top = 100;
    private int width = 300 * 4;
    private int height = 50;
    private int gap = 20;
    private int number = 15;

    public DisplayMeasurementData(Context context) {
        super(context);
    }

    // 뷰 클래스를 엑티비티 레이아웃에 넣기 위해서 이 함수를 오버라이딩 해야 한다
    public DisplayMeasurementData(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 뷰가 화면에 디스플레이 될때 자동으로 호출
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(); // 페인트 객체 생성
        paint.setColor(Color.GREEN); // 빨간색으로 설정



        ArrayList<Integer> arrayTop = new ArrayList<>();
        for(int i=0; i<number; i++){
            arrayTop.add(top + (height + gap)*i);
            canvas.drawRect(left, arrayTop.get(i), left + width, arrayTop.get(i) + height, paint);
        }

        // 좌표값과 페인트 객체를 이용해서 사각형을 그리는 drawRect()
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Random random = new Random();
        width = random.nextInt(600);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(super.getContext(), "MotionEvent.ACTION_DOWN : " +
                    event.getX() + ", " + event.getY() + " ||| " + width, Toast.LENGTH_SHORT).show();
        }

        invalidate();

        return super.onTouchEvent(event);
    }


}
