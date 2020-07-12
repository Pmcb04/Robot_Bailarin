package com.bennyplo.android_mooc_graphics_3d;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Paint;




public class MainActivity extends AppCompatActivity {


    private MyView mMyView=null;//a custom view for drawing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//hide the title bar

        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;


        mMyView=new MyView(this, widthPixels, heightPixels);
        setContentView(mMyView);


        System.out.println("w " + widthPixels);
        System.out.println("h " + heightPixels);


    }
}
