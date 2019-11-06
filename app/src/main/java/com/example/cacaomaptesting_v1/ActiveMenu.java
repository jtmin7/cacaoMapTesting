package com.example.cacaomaptesting_v1;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.util.Size;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;

// 애니메이션 리스너를 위한 임포트
import android.animation.Animator;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

//클래스에 대한 주석
//@brief 간략한 설명
//@details 자세한 설명
//@author 저작권자
//@date 날짜
//@version 버전

//메소드에 대한 주석
//@brief 간략한 설명
//@details 자세한 설명
//@param 파라미터
//@return 반환
//@throws 발생 예외
//
//출처: https://onecellboy.tistory.com/342 [신불사 - 신현호라 불리는 사나이]


// Version inform: Major Minor Build Reversion
// 참조: http://blog.naver.com/PostView.nhn?blogId=akohong&logNo=220797797673&parentCategoryNo=&categoryNo=68&viewDate=&isShowPopularPosts=false&from=section
/**
 * @brief   ActiveMenu
 * @details When you select a menu, a layout menu box containing menus appears from to. Do not engage in any activity while the menu box is up. When the menu box is opened, the event is held when the menu is selected here. If you click anything other than the menu box, you send the menu box back out of the screen.
 * @author  cjcg7
 * @date    2019-11-01
 * @version 1.1.1.0
 */
public class ActiveMenu {
    private View menuview;
    private View parentview;
    private final ViewSize viewsize = new ViewSize();

    public ActiveMenu(View menuview, View parentview, View fixview){
        this.menuview = menuview;
        this.parentview = parentview;

        setMenuEvent(fixview, menuview);
    }
    /**
     *@brief    setViewAnim
     *@details  입력되는 파라미터에 따라서 뷰에 애니메이션을 설정한다
     *@param    View animview
     *@param    int animtype
     *@param    int from
     *           시작 좌표
     *@param    int to
     *           종료 좌표
     *@param    int duration
     *@param    int repeatcount
     *
     *@return   void
     *@throws
     */
    public void setViewAnim(View animview, int animtype, int from, int to, int duration, int repeatcount){

//        ObjectAnimator objanim = ObjectAnimator.ofFloat(animview, "translationX", from, to);
//        img_X.setDuration(duration);
//        img_X.setRepeatCount(ObjectAnimator.INFINITE);
//        img_X.start();
        ObjectAnimator objanim = null;;

        switch(animtype){
            case AnimType.TRANS_X:
                objanim = ObjectAnimator.ofFloat(animview, AnimType.STR_TRANS_X, from, to);
                break;
            case AnimType.TRANS_Y:
                objanim = ObjectAnimator.ofFloat(animview, AnimType.STR_TRANS_Y, from, to);
                break;
            case AnimType.TRANS_XY:
                objanim = ObjectAnimator.ofPropertyValuesHolder(
                        animview,
                        PropertyValuesHolder.ofFloat(AnimType.STR_TRANS_X, from, to),
                        PropertyValuesHolder.ofFloat(AnimType.STR_TRANS_Y, from, to)
                );
                break;
            case AnimType.SCALE_X:
                objanim = ObjectAnimator.ofFloat(animview, AnimType.STR_SCALE_X, (float)from/100, (float)to/100);
                break;
            case AnimType.SCALE_Y:
                objanim = ObjectAnimator.ofFloat(animview, AnimType.STR_SCALE_Y, (float)from/100, (float)to/100);
                break;
            case AnimType.SCALE_XY:
                objanim = ObjectAnimator.ofPropertyValuesHolder(
                        animview,
                        PropertyValuesHolder.ofFloat(AnimType.STR_SCALE_X, (float)from/100, (float)to/100),
                        PropertyValuesHolder.ofFloat(AnimType.STR_SCALE_Y, (float)from/100, (float)to/100)
                        );
                break;
            case AnimType.ALPHA:
                objanim = ObjectAnimator.ofFloat(animview, AnimType.STR_ALPHA, from / 100, to / 100);
                break;
        }
        objanim.setDuration(duration);
        objanim.setRepeatCount(repeatcount);
        objanim.start();

        objanim.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.e("애니가 끝났다", "끝이다");
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        // 애니메이션 리스너
        // 출처: https://stackoverflow.com/questions/38377936/setting-up-an-animationlistener-for-an-objectanimator
    }

    /**
     *@brief    setMenuEvent
     *@details  입력된 뷰에 클릭 이벤트를 설정한다
     *@param    View fixview
     *            고정된 버튼으로 여기에 이벤트가 설정된다
     *@param    View animview
     *            고정되지 않은 뷰로 메뉴를 누르면 나타날 메뉴 뷰다
     *@return   void
     *@throws
     */
    // 인수: 고정뷰, 애니뷰
    public void setMenuEvent(final View fixview, final View animview){

        fixview.setClickable(true);
        fixview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                // ** 부모뷰의 크기에 따른 상대위치 얻기
                setViewAnim(animview, AnimType.TRANS_XY, 0, (int)(parentview.getWidth()*0.3), 3000, 2);
                //setViewAnim(animview, AnimType.SCALE_XY, 100, 200, 3000, 2);
                //setViewAnim(animview, AnimType.ALPHA, 100, 100, 3000, 2);

                getCreatingViewSize(animview);

                ((TextView)fixview).setText("준비가 되었다");

                // 1초 후 이벤트 발생
                final Timer m_timer = new Timer();
                TimerTask m_task = new TimerTask() {
                    @Override
                    public void run() {
                        String txt = "height: " + viewsize.height + " width: " + viewsize.width;
                        ((TextView)fixview).setText(txt);
                        if(1 == 1){
                            m_timer.cancel();
                            //타이머 중단
                        }
                    }
                };
                m_timer.schedule(m_task, 1000);
            }
        });
    }
    public void getViewSize(final View objview, ViewSize viewsize){
        viewsize.width = objview.getWidth();
        viewsize.height = objview.getHeight();
    }

    /**
     *@brief    getCreatingViewSize
     *@details  매개변수로 전달된 뷰에 뷰 생성시 발생하는 이벤트 리스너를 걸어서 뷰가 생성된 후에 뷰의 크기를 얻는다 이미 생성된 뷰의 경우 이함수는 리스너가 걸리지 않으므로 의미가 없다 위 getViewSize 함수가 낳다
     *@param    View objview 이 뷰에 이벤트 리스너를 걸고 뷰가 생성되면 뷰 크기를 이 클래스의 viewsize 변수에 할당한다
     *@return   void
     *@throws
     */
    private void getCreatingViewSize(final View objview){
        // ** 뷰 생성되고 나서 나타나는 이벤트 리스너 등록
        viewsize.height = objview.getHeight();
        viewsize.width = objview.getWidth();

        final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                Log.e("리니어레이아웃", "그려지다");

                viewsize.height = objview.getHeight();
                viewsize.width = objview.getWidth();

                // 한 번만 이 이벤트 호출하고 리스너 삭제
                // 출처: https://m.blog.naver.com/PostView.nhn?blogId=blackzaket&logNo=220198032622&proxyReferer=https%3A%2F%2Fwww.google.com%2F
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    objview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    objview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        };
        objview.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
    }

}


/**
 * @brief   ViewSize
 * @details 정보타입 클래스(뷰의 크기를 정하고 얻을 변수를 설정하였다)
 * @author  cjcg7
 * @date    2019-11-01
 * @version 1.1.1.0
 */
class ViewSize{
    int width = 0;
    int height = 0;
}

/**
 * @brief   AnimType
 * @details 정보타입 클래스(애니메이션 타입들을 문자상수로 정의해 놓았다)
 * @author  cjcg7
 * @date    2019-11-01
 * @version 1.1.1.0
 */
class AnimType{
    static final public int TRANS_X =   0;
    static final public int TRANS_Y =   1;
    static final public int TRANS_XY =  2;
    static final public int SCALE_X =   3;
    static final public int SCALE_Y =   4;
    static final public int SCALE_XY =  5;
    static final public int ALPHA =     6;

    static final public String STR_TRANS_X = "translationX";
    static final public String STR_TRANS_Y = "translationY";
    static final public String STR_SCALE_X = "scaleX";
    static final public String STR_SCALE_Y = "scaleY";
    static final public String STR_ALPHA = "alpha";
}