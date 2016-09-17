package com.example.maxz.myfriend;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Sign_upActivity extends AppCompatActivity {

    //ประกาศตัวแปร


    private EditText nameEditText, usernameEditText, passwordEditText, repasswordEditText;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private ImageView imageView;
    private String nameString,uesrString,passwordString,repasswordString, sexString,imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = (EditText) findViewById(R.id.editText);
        usernameEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        repasswordEditText = (EditText) findViewById(R.id.editText4);

        radioGroup = (RadioGroup) findViewById(R.id.regSex);
        maleRadioButton = (RadioButton) findViewById(R.id.radioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.radioButton2);
        imageView = (ImageView) findViewById(R.id.imageView);




    }//main method 2

    public void ciicksignUPSign(View view) {

        //get Value Edit Text ดึงค่าจาก Edit เป็น String //ตัดช่องว่าง
        //get Text คือ เอาสิ่งที่ใส่อยู่ในช่อง
        //toString คือ แปลงจากสิ่งที่อยู่ใน get Text
        nameString = nameEditText.getText().toString().trim();
        uesrString = usernameEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        repasswordString = repasswordEditText.getText().toString().trim();

        //เช็คช่องว่าง ว่ากรอกครบหรือยัง
        //check Space
        if (nameString.equals("") ||uesrString.equals("")||passwordString.equals("")||repasswordString.equals("")) {
            //have Space
            MyAlert myAlert = new MyAlert(this,R.drawable.doremon48,"มีช่องว่าง","กรุณากรอกทุกช่อง");
            myAlert.myDiglog();


        }




    }//clickSign Up


}//main class 2
