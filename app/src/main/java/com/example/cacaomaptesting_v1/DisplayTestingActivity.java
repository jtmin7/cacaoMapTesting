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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

// ** 타이머 사용을 위한 임포트
import java.lang.reflect.Array;
import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;

// ** 뷰 생성시 나타나는 이벤트를 위한 임포트
import android.view.ViewTreeObserver;

// ** 기기 dpi 얻기 위한 임포트
import android.util.DisplayMetrics;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class DisplayTestingActivity extends AppCompatActivity {

    ArrayList<Animator> animators = new ArrayList<>();

    int relativelayoutheight= 0;
    int relativelayoutwidth = 0;
    RelativeLayout relativeview = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);



        setContentView(R.layout.displaytestingactivity);



        final ImageView iv_mask = (ImageView) findViewById(R.id.mask);
        final ImageView iv_mask02 = (ImageView) findViewById(R.id.mask02);

        relativeview = (RelativeLayout)findViewById(R.id.relativeView);


        // ** 뷰 생성되고 나서 나타나는 이벤트 리스너 등록
        final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                Log.e("리니어레이아웃", "그려지다");


                relativelayoutheight = relativeview.getHeight();
                relativelayoutwidth = relativeview.getWidth();
                Log.e(String.valueOf(pxToDp(relativelayoutheight)) + " , ", String.valueOf(dpToPx(400)));
//        relativeview.getHeight()/2, -relativeview.getHeight()/2
                // 2700, 900
                int gap = 00;
                int standard = relativelayoutheight;

//                int fromnum = -standard + gap;
//                int tonum = standard - gap;
                int fromnum = 0;
                int tonum = relativelayoutwidth;

                // 애니메이션 기준 피봇은 왼쪽 위다
                // ** 애니메이션 만들기
                animators.add(getAnTranslationY(iv_mask, 0, 0, 10000/3));
                animators.add(getAnTranslationY(iv_mask02, 0, 0, 10000/3));
                animators.add(getAnTranslationX(iv_mask, fromnum - iv_mask.getWidth(), tonum, 10000/3));
                animators.add(getAnTranslationX(iv_mask02, fromnum, tonum, 10000/3));
                animators.add(getAnScale(iv_mask02, 0.1f, 0.1f, 5000));




                AnimatorSet set = new AnimatorSet();
//        set.playTogether(animators.get(0), animators.get(1));
                set.playTogether(
                        animators
                );
                set.start();


                LinearLayout li = (LinearLayout) findViewById(R.id.testinglayout);
                RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeView);
                // View menuview, View parentview, View fixview
                ActiveMenu am = new ActiveMenu(li, rl, (View) findViewById(R.id.stateTxt02));


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





//
//        relativeview.getHeight()/2, -relativeview.getHeight()/2
        // 2700, 900


        final TextView tv_statetxt01 = (TextView)findViewById(R.id.stateTxt01);
        final TextView tv_statetxt02 = (TextView)findViewById(R.id.stateTxt02);
        final TextView tv_statetxt03 = (TextView)findViewById(R.id.stateTxt03);
        tv_statetxt01.setText("00001");
        tv_statetxt02.setText("00002");
        tv_statetxt03.setText("00003");


        // ** myTimer
        final Timer m_timer = new Timer();
        TimerTask m_task = new TimerTask() {

            int count  = 0;
            @Override
            public void run() {

                Log.e("카운터: ", String.valueOf(count));

                int[] location = new int[2];
                iv_mask02.getLocationOnScreen(location);

                final String txt = "height: " + String.valueOf(height) + " , mask_y_pos: " + mask_y_pos;
                final String viewsize = "[뷰 크기] " + "left: " + iv_mask02.getLeft()
                        + " right: " + iv_mask02.getRight()
                        + " top: " + iv_mask02.getTop()
                        + " bottom: " + iv_mask02.getBottom();

                // 현재 절대위치 나타냄
                final String absoluteposition =  "[절대위치] " + "(0): " + location[0] + " (1): " + location[1];
                //final String txt04 = "relativeLayout: " + "height: " + relativeview.getHeight();

                final String fromto = "from 0 - (viewith)" + iv_mask.getWidth() + " \nto (relativeheight)" + relativelayoutwidth;

                tv_statetxt01.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_statetxt01.setText("relativelayoutSize: " + String.valueOf(relativelayoutwidth));
                    }
                });
                tv_statetxt02.post(new Runnable() {
                    @Override
                    public void run() {
                        //tv_statetxt02.setText(fromto);
                    }
                });
                tv_statetxt03.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_statetxt03.setText(absoluteposition);
                    }
                });
                count += 1;

                if(1 == 1){
                    //m_timer.cancel();
                    //타이머 중단
                }
            }
        };
        m_timer.schedule(m_task, 500, 200);
        // ** 타이머 출처: http://blog.naver.com/PostView.nhn?blogId=highkrs&logNo=220283709171
        // ** 타이머에서 뷰 텍스트 설정 방법 출처: https://lktprogrammer.tistory.com/173
        // ** 텍스트 뷰도 처음에는 final 로 선언해줘야 됀다

        // ** 기기 dpi 알아내기
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d("device dpi", "=>" + metrics.densityDpi);
        //출처: https://altongmon.tistory.com/401 [IOS를 Java]

    }
    public void displayMain(View v){
        finish();
    }

    public void playAnim(View v){
        int tonum = relativelayoutwidth;
        int id= v.getId();
        LinearLayout li = (LinearLayout) findViewById(R.id.testinglayout);

//        ObjectAnimator object01 = ObjectAnimator.ofFloat(asdfjlasdfkl,
//                "translationX", 0, tonum - 200);
//        object01.setInterpolator(new AccelerateDecelerateInterpolator());
//        object01.setDuration(3000);
//        object01.start();
    }
    public void playAnim02(View v){
        int tonum = relativelayoutwidth;
        int id= v.getId();
        LinearLayout li_testing = (LinearLayout) findViewById(id);
        ObjectAnimator object01 = ObjectAnimator.ofFloat(li_testing,
                "translationX", tonum - 200, 0);
        object01.setInterpolator(new AccelerateDecelerateInterpolator());
        object01.setDuration(3000);
        object01.start();
    }

    private ObjectAnimator getAnTranslationX(View iv_img, float from, float to, int duration){
        ObjectAnimator img_X = ObjectAnimator.ofFloat(iv_img, "translationX", from, to);
        img_X.setDuration(duration);
        img_X.setRepeatCount(ObjectAnimator.INFINITE);
        return img_X;
    }
    private ObjectAnimator getAnTranslationY(View iv_img, float from, float to, int duration){
        ObjectAnimator img_X = ObjectAnimator.ofFloat(iv_img, "translationY", from, to);
        img_X.setDuration(duration);
//        img_X.setRepeatCount(ObjectAnimator.INFINITE);
        img_X.setRepeatCount(ObjectAnimator.INFINITE);
        return img_X;
    }
    private ObjectAnimator getAnScale(View iv_img, float from, float to, int duration){
        ObjectAnimator img_scale = ObjectAnimator.ofPropertyValuesHolder(iv_img,
                PropertyValuesHolder.ofFloat("scaleX", from, to),
                PropertyValuesHolder.ofFloat("scaleY", from, to));
        img_scale.setDuration(duration);
        img_scale.setRepeatCount(ObjectAnimator.INFINITE);
        return img_scale;
    }

    public int dpToPx(int inputdp){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return inputdp * metrics.densityDpi / 160;
    }
    public int pxToDp(int inputpx){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return inputpx * 160 / metrics.densityDpi;
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
