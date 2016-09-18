package com.example.maxz.myfriend;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


public class MainActivity extends AppCompatActivity {

    //ประกาศตัวแปร
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binwiget
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);


    }//main method

    public void cickSignINMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        //chece
        if (userString.equals("") || passwordString.equals("")) {
            MyAlert myAlert = new MyAlert(this, R.drawable.kon48, "กรอบไม่ครบ", "กรอบให้ครับด้วยค่ะ");
            myAlert.myDiglog();

        } else {

            SynUser synUser = new SynUser();
            synUser.execute();

        }


    }//cickSignINMain

    private class SynUser extends AsyncTask<Void, Void, String> {

        private static final String URLPHP = "http://swiftcodingthai.com/18Sep/get_user.php";

        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(URLPHP).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                return null;
            }



        }//doInBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("My3", "JSON ==>" + s);
        }//onPostExecute
    }//main class


    public void click_signUPMain(View view) {

        startActivity(new Intent(MainActivity.this,Sign_upActivity.class));

    }


}//main class