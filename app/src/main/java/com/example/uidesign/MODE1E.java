package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.friendlyarm.FriendlyThings.HardwareControler;

import java.util.Timer;
import java.util.TimerTask;

public class MODE1E extends AppCompatActivity {
    private Button FeedButtonE;
    private Button ShitButtonE;
        //串口初始化参数（不能改
        private String devName = "/dev/ttyAMA3";//ttyAMA3 UART3
        private int speed = 115200;//波特率
        private int dataBits = 8;
        private int stopBits = 1;
        private int devfd = -1;
    public static MODE1E instance = null;


    String feed_str = "1\n";
    String clean_str = "2\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode1_e);

        FeedButtonE = (Button) findViewById(R.id.feed_btn_e);
        ShitButtonE = (Button) findViewById(R.id.shit_btn_e);


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
        FeedButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启串口
                if(devfd == -1){
                    devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                }
                //发送数据
                String str = feed_str;
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //铲屎按钮触发函数
        ShitButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启串口
                if(devfd == -1){
                    devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                }
                //发送数据
                String str = clean_str;
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });
    }
}
