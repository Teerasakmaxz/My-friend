package com.example.maxz.myfriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    //ประกาศตัวแปร


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }//main method

    public void click_signUPMain(View view) {

        startActivity(new Intent(MainActivity.this,Sign_upActivity.class));

    }


}//main class