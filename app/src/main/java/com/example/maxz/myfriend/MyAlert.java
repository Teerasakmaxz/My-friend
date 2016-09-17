package com.example.maxz.myfriend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by maxz on 9/17/2016 AD.
 */
public class MyAlert {

    private Context context;
    private int anInt;
    private String titleString, messageString;

    public MyAlert(Context context, int anInt, String titleString, String messageString) {
        this.context = context;
        this.anInt = anInt;
        this.titleString = titleString;
        this.messageString = messageString;


    }



    public void myDiglog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);//อันโดใช้งานไม่ได้
        builder.setIcon(anInt);// show pic
        builder.setMessage(messageString);//show รายละเอียด
        builder.setTitle(titleString);//show หัวข้อ
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

}
