
package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SerialPort";
    private Button language1;//定义语言切换按钮
    private Button mode1;//定义操作模式按钮
    private Button mode2;//定义维护模式按钮

    //串口初始化参数（不能改
    private String devName = "/dev/ttyAMA3";//ttyAMA3 UART3
    private int speed = 115200;//波特率
    private int dataBits = 8;
    private int stopBits = 1;
    private int devfd = -1;

    public static MainActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //关闭其他页面
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

        //切换语言BUTTON 跳转 MainActivity2
        language1=findViewById(R.id.language1);
        language1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        //操作模式BUTTON 跳转mode1
        mode1=findViewById(R.id.mode1);
        mode1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MODE1.class);
                startActivity(intent);
            }
        });

        //维护模式BUTTON 跳转mode2
        mode2=findViewById(R.id.mode2);
        mode2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LOGIN.class);
                startActivity(intent);
            }
        });
    }
}
