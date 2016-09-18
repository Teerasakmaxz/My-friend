package com.example.maxz.myfriend;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
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
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class Sign_upActivity extends AppCompatActivity {

    //ประกาศตัวแปร


    private EditText nameEditText, usernameEditText, passwordEditText, repasswordEditText;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private ImageView imageView;
    private String nameString, uesrString, passwordString, repasswordString, sexString, imageString, imagePathString, imageNameString;
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
                startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูปภาพ"), 1);
            }//OnClick
        });

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        sexString = "Male";
                        break;
                    case R.id.radioButton2:
                        sexString = "Female";
                        break;
                }//switch

            }//onCheck
        });

    }//main method 2

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            Log.d("MyFrienfV1 ", "Result ==>OK");

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
            //Upload Image to Server
            uploadImageToServer();
            insertDataToServer();
        }


    }//clickSign Up

    private void insertDataToServer() {

        imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
        imageNameString = "http://swiftcodingthai.com/18Sep/Image" + imageNameString;
        Log.d("MyFrienfV1", "imageNameString ==>" + imageNameString);

        Log.d("MyFrienfV2", "Name=" + nameString);
        Log.d("MyFrienfV2", "Sex=" + sexString);
        Log.d("MyFrienfV2", "User=" + uesrString);
        Log.d("MyFrienfV2", "Password=" + passwordString);
        Log.d("MyFrienfV2", "Image=" + imageNameString);

        MyUpdeatUser myUpdeatUser = new MyUpdeatUser(this);
        myUpdeatUser.execute();

    }//insertDataToServer

    //<ความปลอดภัย,โพสเกจบา>
    private class MyUpdeatUser extends AsyncTask<Void, Void, String> {

        private Context context;
        private static final String URL = "http://swiftcodingthai.com/18Sep/add_user_max.php";

        public MyUpdeatUser(Context context) {
            this.context = context;
        }

        @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            Log.d("MyFrienfV2", "Result ==>" + s);

            if (Boolean.parseBoolean(s)) {
                Toast.makeText(context,"อัพข้อมูลเรียบร้อยจ้า",Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Toast.makeText(context,"มรข้อผิดพลาดไม่สามารถอัพข้อมูลได้",Toast.LENGTH_SHORT).show();

            } //if
            }//onpost
        //พยายามต่อเน็ต
            @Override
        protected String doInBackground(Void... params) {

                try {

                    OkHttpClient okHttpClient = new OkHttpClient();
                    //ทำให้ข้อมูลเป็นก้อน แล้วโยน
                    RequestBody requestBody = new FormEncodingBuilder()
                            .add("isAdd", "true")
                            .add("Name", nameString)
                            .add("Sex", sexString)
                            .add("User", uesrString)
                            .add("Password", passwordString)
                            .add("Image", imageNameString)
                            .build();//ตาม PHP และเงื่อนไขการPost

                    Request.Builder builder = new Request.Builder();
                    Request request = builder.url(URL).post(requestBody).build();
                    Response response = okHttpClient.newCall(request).execute();
                    return response.body().string();

                } catch (Exception e) {
                    return null;
                }


        }
    }//MyUpdeatUser class

    private void uploadImageToServer() {

        //New Policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "18Sep@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image");
            simpleFTP.stor(new File(imagePathString));
            simpleFTP.disconnect();

            Log.d("MyFrienfV1", "UploadImage==> OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//uploadImageToServer


}//main class 2
