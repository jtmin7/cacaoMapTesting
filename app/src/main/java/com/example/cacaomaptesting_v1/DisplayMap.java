package com.example.cacaomaptesting_v1;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

// ** 그림 그리기 위한 임포트
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayMap extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.displaymap);
    }

    public void displayMain(View v){
        finish();
    }

}
