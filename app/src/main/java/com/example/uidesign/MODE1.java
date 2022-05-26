package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.friendlyarm.FriendlyThings.HardwareControler;


public class MODE1 extends AppCompatActivity {

    private Button FeedButton;
    private Button ShitButton;
    private int devfd = -1;
    public static MODE1 instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mode1);

        //关闭其他页面
        if(MainActivity.instance!=null){
            MainActivity.instance.finish();
        }
        if(MainActivity2.instance!=null){
            MainActivity2.instance.finish();
        }
        if( LOGIN.instance!=null){
            LOGIN.instance.finish();
        }
        if( LOGINE.instance!=null){
            LOGINE.instance.finish();
        }
        if( MODE1.instance!=null){
            MODE1.instance.finish();
        }
        if( MODE1E.instance!=null){
            MODE1E.instance.finish();
        }
        if( MODE2.instance!=null){
            MODE2.instance.finish();
        }
        if( MODE2E.instance!=null){
            MODE2E.instance.finish();
        }
        //喂食按钮触发函数
        FeedButton = findViewById(R.id.feed_btn);
        FeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘1’，表示喂食
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "1";
                HardwareControler.write(devfd, str.getBytes());
                str = "\r";
                HardwareControler.write(devfd, str.getBytes());
                str = "\n";
                HardwareControler.write(devfd, str.getBytes());

            }
        });

        //铲屎按钮触发函数
        ShitButton  = findViewById(R.id.shit_btn);
        ShitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘2’，表示铲屎
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "2";
                HardwareControler.write(devfd, str.getBytes());

            }
        });
    }



}