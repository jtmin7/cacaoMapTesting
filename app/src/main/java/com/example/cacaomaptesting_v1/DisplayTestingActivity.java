package com.example.cacaomaptesting_v1;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

// ** 타이머 사용을 위한 임포트
import java.lang.reflect.Array;
import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;

import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DisplayTestingActivity extends AppCompatActivity {

    ArrayList<Animator> animators = new ArrayList<>();
    int mypos = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.displaytestingactivity);



        final ImageView iv_mask = (ImageView) findViewById(R.id.mask);
        final ImageView iv_mask02 = (ImageView) findViewById(R.id.mask02);


        // ** 뷰 생성되고 나서 나타나는 이벤트 리스너 등록
        final RelativeLayout relativeview = (RelativeLayout)findViewById(R.id.relativeView);
        final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                Log.e("리니어레이아웃", "그려지다");

                // 한 번만 이 이벤트 호출하고 리스너 삭제
                // 출처: https://m.blog.naver.com/PostView.nhn?blogId=blackzaket&logNo=220198032622&proxyReferer=https%3A%2F%2Fwww.google.com%2F
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    relativeview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        };
        relativeview.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
        // 참조: https://jjun5050.tistory.com/21


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        final int height = size.y;

        iv_mask.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int mask_Height = iv_mask.getMeasuredHeight();
        final int mask_y_pos = height - mask_Height;






//        relativeview.getHeight()/2, -relativeview.getHeight()/2
        // 2700, 900
        animators.add(getAnTranslationY(iv_mask, 1000, 0, 10000/3));
        animators.add(getAnTranslationY(iv_mask02, 1000, 0, 10000/3));
        animators.add(getAnScale(iv_mask02, 0.1f, 0.1f, 5000));


        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animators.get(0), animators.get(1));
        set.playTogether(
                animators
        );
        set.start();

        final TextView tv_statetxt01 = (TextView)findViewById(R.id.stateTxt01);
        final TextView tv_statetxt02 = (TextView)findViewById(R.id.stateTxt02);
        final TextView tv_statetxt03 = (TextView)findViewById(R.id.stateTxt03);
        tv_statetxt01.setText("00001");
        tv_statetxt02.setText("00002");
        tv_statetxt03.setText("00003");


        // ** myTimer
        Timer m_timer = new Timer();
        TimerTask m_task = new TimerTask() {

//            final mypos = relativeview.getHeight()/2;
            int count  = 0;
            @Override
            public void run() {

                Log.e("카운터: ", String.valueOf(count));

//                getLeft(), getRight(), getTop(), getBottom()
//                - getLocationOnScreen()
//                - 사용방법
//                int[] location = new int[2];
//
//                view.getLocationOnScreen(location);

                int[] location = new int[2];
                iv_mask02.getLocationOnScreen(location);

                final String txt = "height: " + String.valueOf(height) + " , mask_y_pos: " + mask_y_pos;
                final String txt02 = "[상대위치] " + "left: " + iv_mask02.getLeft()
                        + " right: " + iv_mask02.getRight()
                        + " top: " + iv_mask02.getTop()
                        + " bottom: " + iv_mask02.getBottom();

                final String txt03 =  "[절대위치] " + "(0): " + location[0] + " (1): " + location[1];
                //final String txt04 = "relativeLayout: " + "height: " + relativeview.getHeight();

                tv_statetxt01.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_statetxt01.setText(String.valueOf(mypos));
                    }
                });
                tv_statetxt02.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_statetxt02.setText(txt);
                    }
                });
                tv_statetxt03.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_statetxt03.setText(txt03);
                    }
                });
                count += 1;

                if(1 == -1){
                    //m_timer.cancel();
                    //타이머 중단
                }
            }
        };
        m_timer.schedule(m_task, 500, 200);
        // ** 타이머 출처: http://blog.naver.com/PostView.nhn?blogId=highkrs&logNo=220283709171
        // ** 타이머에서 뷰 텍스트 설정 방법 출처: https://lktprogrammer.tistory.com/173
        // ** 텍스트 뷰도 처음에는 final 로 선언해줘야 됀다

    }
    public void displayMain(View v){
        finish();
    }
    public void playAnim(View v){

//        int index = 0;
//        for (Animator element : animators){
//            if(index !=2)
//                element.pause();
//            // index 증가
//            index++;
//        }

    }

    private ObjectAnimator getAnTranslationX(ImageView iv_img, float from, float to, int duration){
        ObjectAnimator img_X = ObjectAnimator.ofFloat(iv_img, "translationX", from, to);
        img_X.setDuration(duration);
        img_X.setRepeatCount(ObjectAnimator.INFINITE);
        return img_X;
    }
    private ObjectAnimator getAnTranslationY(ImageView iv_img, float from, float to, int duration){
        ObjectAnimator img_X = ObjectAnimator.ofFloat(iv_img, "translationY", from, to);
        img_X.setDuration(duration);
        img_X.setRepeatCount(ObjectAnimator.INFINITE);
        return img_X;
    }
    private ObjectAnimator getAnScale(ImageView iv_img, float from, float to, int duration){
        ObjectAnimator img_scale = ObjectAnimator.ofPropertyValuesHolder(iv_img,
                PropertyValuesHolder.ofFloat("scaleX", from, to),
                PropertyValuesHolder.ofFloat("scaleY", from, to));
        img_scale.setDuration(duration);
        img_scale.setRepeatCount(ObjectAnimator.INFINITE);
        return img_scale;
    }

    // ** 리스너 삭제를 위한 메소드
//    private static void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
//        if (observer == null) {
//            return ;
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            observer.removeGlobalOnLayoutListener(listener);
//        } else {
//            observer.removeOnGlobalLayoutListener(listener);
//        }
//    }
////    출처: https://damedame.tistory.com/entry/View의-위치-사이즈-구하기 [다메다메의 IT와 19금게임]
}
