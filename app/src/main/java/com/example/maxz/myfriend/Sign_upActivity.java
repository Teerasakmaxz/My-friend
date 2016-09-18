package com.example.maxz.myfriend;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private String nameString, uesrString, passwordString, repasswordString, sexString, imageString,imagePathString
            ,ImageNameString;
    private boolean statusABoolean = true;

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


        //imagae controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"โปรดเลือกรูปภาพ"),1);
            }//OnClick
        });


    }//main method 2

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode ==1)&&(resultCode ==RESULT_OK)) {
            Log.d("MyFrienfV1 ","Result ==>OK");

            //หา path รูป
            Uri uri = data.getData();
            imagePathString = myFinndPathImage(uri);
            Log.d("MyFrienfV1", "imagePathString ==>" + imagePathString);
            //result Complete

            //Setup Image to ImageView
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }//try

            statusABoolean = false;
        }//if


    }//onActivity

    private String myFinndPathImage(Uri uri) {


        String strResult = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();
            int intIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            strResult = cursor.getString(intIndex);

        } else {

            strResult = uri.getPath();

        }
        return strResult;
    }//myFinndPathImage

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
        if (nameString.equals("") || uesrString.equals("") || passwordString.equals("") || repasswordString.equals("")) {
            //have Space
            MyAlert myAlert = new MyAlert(this, R.drawable.doremon48, "มีช่องว่าง", "กรุณากรอกทุกช่อง");
            myAlert.myDiglog();


        } else if (!passwordString.equals(repasswordString)) {

            MyAlert myAlert = new MyAlert(this, R.drawable.nobita48, "password ผิด", "พิม password ให้เหมือนกัน");
            myAlert.myDiglog();

            //pass not macth
            //isChecked คือ ถ้ามมีการเซ็คจะเป็นจริง
        } else if (!(maleRadioButton.isChecked()) || femaleRadioButton.isChecked()) {
            MyAlert myAlert = new MyAlert(this, R.drawable.bird48, "ยังไม่เลือก Sex", "กรุณาเลือกเพศ");
            myAlert.myDiglog();

            //check Sex
        } else if (statusABoolean) {
            MyAlert myAlert = new MyAlert(this, R.drawable.kon48, "ยังไม่เลือกรูปเลย", "กรุณาเลือกรูปด้วยค่ะ");
            myAlert.myDiglog();
        } else {

        }


    }//clickSign Up


}//main class 2
