package com.example.cacaomaptesting_v1;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.util.DisplayMetrics;
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
import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

import android.view.WindowManager;

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
 * @author  cjcg7 (email: cjcg7@naver.com   phone: 010-2717-0674)
 * @date    2019-11-01
 * @version 1.1.1.0
 */
public class ActiveMenu {
    private ArrayList<LinearLayout> menuViewList = new ArrayList<>();
    private View menuview;
    private View parentview;
    private LayoutUnits layoutunits = new LayoutUnits();
    private float idealiconsize = 0.f;

    private final ViewSize viewsize = new ViewSize();

    public ActiveMenu(ArrayList<LinearLayout> menuviewlist, View parentview, View fixview, float idealiconsize){
        this.menuViewList= menuviewlist;
        this.parentview = parentview;
        this.idealiconsize = idealiconsize;

        setMenuEvent(fixview, menuViewList);
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
    public void setMenuEvent(final View fixview, final ArrayList<LinearLayout> animviewlist ){// animview){

        fixview.setClickable(true);
        fixview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                // ** 부모뷰의 크기에 따른 상대위치 얻기

                ViewSize animviewsize = setRelativeSizeOnParentView((LinearLayout)animviewlist.get(0), parentview, ViewAxis.WIDTH, 0.1f);

                // float idealiconsize, float iconsize, int parentviewsize, int iconcount
                layoutunits = getLayoutUnits(idealiconsize, animviewsize.height,
                        parentview.getHeight(), animviewlist.size());


                for (int index=0; index<animviewlist.size(); index++){
                    setRelativeSizeOnParentView((LinearLayout)animviewlist.get(index), parentview, ViewAxis.WIDTH, 0.1f);

                    setViewPivotInit((LinearLayout)animviewlist.get(index));
                    setViewAnim(animviewlist.get(index), AnimType.TRANS_Y, (int)idealiconsize, (int)(idealiconsize + index*layoutunits.interIconSpaceHomePivot), 3000, 0);
                }

//                animview.setPivotX(getLinLayViewSize((LinearLayout)animview).width * 0.5f);
//                animview.setPivotY(getLinLayViewSize((LinearLayout)animview).height * 0.5f);

//                setViewAnim(animview, AnimType.SCALE_XY, 100, 50, 3000, 2);
                //setViewAnim(animview, AnimType.ALPHA, 100, 100, 3000, 2);

                // LinearLayout objview, View parentview, ViewAxis referenceaxis, float scale



                ((TextView)fixview).setText("준비가 되었다");

                // ** Testing Code: getLinLayViewSIze
//                ViewSize myv = new ViewSize();
//                myv = getLinLayViewSize((LinearLayout)animview);

                String txt = " (1): " + layoutunits.idealIconSize + " (2): " + layoutunits.interIconSpace +
                        " (3): " + layoutunits.interIconSpaceHomePivot + " (4): " + layoutunits.fixIconPosition +
                        " width: " + getLinLayViewSize(animviewlist.get(0)).width + " height: " + getLinLayViewSize(animviewlist.get(0)).height;
                ((TextView)fixview).setText(txt);


                // ** Testing Code: getCreatingViewSize
//                // 1초 후 이벤트 발생
//                final Timer m_timer = new Timer();
//                TimerTask m_task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        String txt = "height: " + viewsize.height + " width: " + viewsize.width;
//                        ((TextView)fixview).setText(txt);
//                        if(1 == 1){
//                            m_timer.cancel();
//                            //타이머 중단
//                        }
//                    }
//                };
//                m_timer.schedule(m_task, 1000);
            }
        });
    }
    /**
     *@brief    getViewSize
     *@details  뷰 사이즈를 얻어 매개변수의 viewsize 인수에 할당하는 함수다
     *@param    View objview 이 뷰의 크기를 얻는다
     *@param    ViewSize viewsize 얻은 뷰 크기를 이 매개변수에 할당한다
     *@return   void
     *@throws
     */
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

    /**
     *@brief    getLinLayViewSize
     *@details  (getVerticalLinearLayoutViewSizeFromChildView) orientation 이 vertical 인 LinearLayout 뷰의 크기를 얻는다(자식뷰들을 통해 크기를 계산한다)(세로는 모든 자식뷰들의 높이를 더하고 가로는 가장 가로 크기가 큰 자식뷰의 크기로 정한다)
     *@param    LinearLayout linlayview 이 뷰에 크기를 구한다
     *@return   ViewSize 구한 레이아웃 크기를 반환한다
     *@throws
     */
    ViewSize getLinLayViewSize(LinearLayout linlayview){
        ViewSize viewsize = new ViewSize();

        // 자식 뷰 구하는 부분
        // 참조: https://stackoverrun.com/ko/q/1997355
        // 뷰의 구조에 대한 참조: https://mattlee.tistory.com/74
        final int childViewCount = linlayview.getChildCount();
        for(int i=0; i<childViewCount; i++){
            viewsize.height += linlayview.getChildAt(i).getHeight();
            viewsize.width = Math.max(viewsize.width, linlayview.getChildAt(i).getWidth());
        }
        return viewsize;
    }

    /**
     *@brief    setRelativeSizeOnParentView
     *@details  (SetRelativeSizeBasedOnParentview) 부모뷰의 기준축에 크기의 배율(scale) 만큼 objview 의 크기를 변경한다(이때 피봇은 objview의 가운데로 설정하였다)
     *@param    LinearLayout objview 이 뷰의 크기를 변화시킨다
     *@param    View parentview 이 뷰의 크기와 배율(scale)을 바탕으로 objview 크기를 정한다
     *@param    int referenceaxis 부모 뷰의 크기에 어떤 축을 기준으로 할지 이 매개변수로 정한다 (유형은 ViewAxis 정보타입 클래스다)
     *@param    float scale 부모 뷰의 크기에 배율을 설정한다 0.5는 부모뷰 크기에 반을 의미한다
     *@return   ViewSize 변경된 뷰의 크기를 반환한다
     */
    ViewSize setRelativeSizeOnParentView(LinearLayout objview, View parentview, int referenceaxis, float scale){
        int animscale;
        int parentviewsize = 0;
        ViewSize objviewsize = new ViewSize();
        if(referenceaxis == ViewAxis.HEIGHT){
              parentviewsize = parentview.getHeight();
        }
        else{
            parentviewsize = parentview.getWidth();
        }
        animscale = (int)(parentviewsize * scale / getLinLayViewSize(objview).width * 100);

        // 변경된 크기의 사이즈를 얻는다
        objviewsize.width = (int)(parentviewsize * scale);
        objviewsize.height= getLinLayViewSize(objview).height * animscale / 100;


        // 피봇을 뷰의 가운데로 설정한다
        setViewPivotToCenter(objview);

        // View animview, int animtype, int from, int to, int duration, int repeatcount
        // 뷰의 크기를 원하는 크기만큼 줄이는 애니
        setViewAnim(objview, AnimType.SCALE_XY, animscale, animscale, 0, 0);

        return objviewsize;
    }

    /**
     *@brief    setViewPivotToCenter
     *@details  objview 피봇을 부모뷰 상에서  objview 의 가운데 점으로 둔다. (피봇의 의미가 translation 에서는 이동 위치의 기준점을 의미하므로 0, 0으로 하는게 맞다 때문에 이 함수는 무의미하다 하지만 뷰가 처음 배치되고 뷰의 크기를 오브젝트애니메이션을 이용해 조정하려 할때는 이 함수를 이용하면 제자리에서 커지고 작아진다 그 예가 setRelativeSizeOnParentView 안에서 쓰임을 보면 알 수 있다)
     *@param    LinearLayout objview 이 뷰의 피봇을 변화시킨다
     *@return   void
     */
    void setViewPivotToCenter(LinearLayout objview){
        objview.setPivotX(getLinLayViewSize(objview).width * 0.5f);
        objview.setPivotY(getLinLayViewSize(objview).height * 0.5f);
    }

    /**
     *@brief    setViewPivotInit
     *@details  objview 피봇을 부모뷰의 0, 0 좌표로 둔다 (오브젝트애니메이션을 사용할때 피봇 초기화에 유용하다)
     *@param    LinearLayout objview 이 뷰의 피봇을 변화시킨다
     *@return   void
     */
    void setViewPivotInit(LinearLayout objview){
        objview.setPivotX(0.f);
        objview.setPivotY(0.f);
    }


    /**
     *@brief    getLayoutUnits
     *@details  이상적인 아이콘 크기(보통 0.9cm), 아이콘 사이즈, 부모뷰 사이즈, 아이콘 갯수를 받아서 화면에 배치(가로/세로 일열배치)시 균형있게 배치될 수 있는 아이콘간 거리 계산해 반환한다
     *@param    float idealiconsize     이상적인 아이콘 크기
     *@param    float iconsize          아이콘 사이즈 (축(x/y)을 정해야 한다)
     *@param    int parentviewsize      부모뷰 사이즈 (축(x/y)을 정해야 한다)
     *@param    int iconcount           아이콘 갯수
     *@return   LayoutUnits             아이콘을 배치하는데 필요한 정보를 담은 LayoutUnits 정보 타입 클래스
     */
    LayoutUnits getLayoutUnits(float idealiconsize, float iconsize, int parentviewsize, int iconcount){
        int compactWidth = (int)(parentviewsize - 2*idealiconsize);
        int interIconSpace = (int)((compactWidth - (iconcount * iconsize)) / (iconcount-1));
        LayoutUnits thislayoutunits = new LayoutUnits();
        thislayoutunits.idealIconSize = idealiconsize;
        thislayoutunits.interIconSpace = interIconSpace;
        thislayoutunits.interIconSpaceHomePivot = interIconSpace + iconsize;
        return thislayoutunits;
    }




}


/**
 * @brief   ViewSize
 * @details 정보타입 클래스(뷰의 크기를 정하고 얻을 변수를 설정하였다)
 * @author  cjcg7 (email: cjcg7@naver.com   phone: 010-2717-0674)
 * @date    2019-11-01
 * @version 1.1.1.0
 */
class ViewSize{
    int width = 0;
    int height = 0;
}
class ViewAxis{
    int axis = -1;
    static final int WIDTH = 0;
    static final int HEIGHT = 1;

}

/**
 * @brief   AnimType
 * @details 정보타입 클래스(애니메이션 타입들을 문자상수로 정의해 놓았다)
 * @author  cjcg7 (email: cjcg7@naver.com   phone: 010-2717-0674)
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

/**
 * @brief   AnimType
 * @details 정보타입 클래스(뷰 배치에 도움되는 값들을 갖는다)
 * @author  cjcg7 (email: cjcg7@naver.com   phone: 010-2717-0674)
 * @date    2019-11-01
 * @version 1.1.1.0
 */
class LayoutUnits{
    float idealIconSize;
    float interIconSpace;
    float interIconSpaceHomePivot;
    float fixIconPosition;
    LayoutUnits(){
        idealIconSize = 0.f;
        interIconSpace = 0.f;
        interIconSpaceHomePivot = 0.f;
        fixIconPosition = 0.f;
    }
}