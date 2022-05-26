package com.example.uidesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.util.Log;
import android.text.Html;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import com.friendlyarm.FriendlyThings.HardwareControler;
import com.friendlyarm.FriendlyThings.BoardType;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;



//英文操作界面
//以E结尾的java和xml文件都是英文操作界面
public class MainActivity2 extends AppCompatActivity {
    private Button language2;//定义语言切换按钮
    private Button mode1e;
    private Button mode2e;
    //串口初始化参数（不能改
    private String devName = "/dev/ttyAMA3";//ttyAMA3 UART3
    private int speed = 115200;//波特率
    private int dataBits = 8;
    private int stopBits = 1;
    private int devfd = -1;

    public static MainActivity2 instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //打开串口
        //*必须在第一个界面打开，否则容易出错
        devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );

        //切换语言BUTTON 跳转 MainActivity
        language2=findViewById(R.id.language2);
        language2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //操作模式BUTTON 跳转mode1E
        mode1e=findViewById(R.id.mode1e);
        mode1e.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MODE1E.class);
                startActivity(intent);
            }
        });

        //维护模式BUTTON 跳转mode2E
        mode2e=findViewById(R.id.mode2e);
        mode2e.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, LOGINE.class);
                startActivity(intent);
            }
        });
    }
}