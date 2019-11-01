package com.example.cacaomaptesting_v1;

import android.animation.ObjectAnimator;
import android.view.View;

public class ActiveMenu {
    private View menuview;
    private View parentview;

    public ActiveMenu(View menuview, View parentview){
        this.menuview = menuview;
        this.parentview = parentview;

        int from = 0;
        int to = parentview.getWidth();
        int duration = 3000;

        ObjectAnimator img_X = ObjectAnimator.ofFloat(menuview, "translationX", from, to);
        img_X.setDuration(duration);
        img_X.setRepeatCount(ObjectAnimator.INFINITE);
        img_X.start();
    }

}
