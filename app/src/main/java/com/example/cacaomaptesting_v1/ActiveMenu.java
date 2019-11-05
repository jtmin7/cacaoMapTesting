package com.example.cacaomaptesting_v1;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.Animation;

// 애니메이션 리스너를 위한 임포트
import android.animation.Animator;
import android.util.Log;

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
    public void setMenuEvent(View fixview, final View animview){

        fixview.setClickable(true);
        fixview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                setViewAnim(animview, AnimType.TRANS_XY, 0, parentview.getWidth(), 3000, 2);
                setViewAnim(animview, AnimType.SCALE_XY, 100, 200, 3000, 2);
                setViewAnim(animview, AnimType.ALPHA, 50, 100, 3000, 2);

            }
        });
    }

}


/**
 * @brief   AnimType
 * @details 애니메이션 타입들을 문자상수로 정의해 놓았다
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