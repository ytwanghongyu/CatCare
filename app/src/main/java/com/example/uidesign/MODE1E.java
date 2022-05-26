package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.friendlyarm.FriendlyThings.HardwareControler;

public class MODE1E extends AppCompatActivity {
    private Button FeedButtonE;
    private Button ShitButtonE;
    private int devfd = -1;
    public static MODE1E instance = null;

    String feed_str = "1";
    String clean_str = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode1_e);
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
        if( MODE2.instance!=null){
            MODE2.instance.finish();
        }
        if( MODE2E.instance!=null){
            MODE2E.instance.finish();
        }
        //喂食按钮触发函数
        FeedButtonE = findViewById(R.id.feed_btn_e);
        FeedButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘1’，表示喂食
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = feed_str;
                //补换行符\n
                if (str.charAt(str.length()-1) != '\n') {
                    str = str + "\n";
                }
                HardwareControler.write(devfd, str.getBytes());

            }
        });

        //铲屎按钮触发函数
        ShitButtonE = findViewById(R.id.shit_btn_e);
        ShitButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘2’，表示铲屎
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = clean_str;
                //补换行符\n
                if (str.charAt(str.length()-1) != '\n') {
                    str = str + "\n";
                }
                HardwareControler.write(devfd, str.getBytes());
            }
        });



    }
}