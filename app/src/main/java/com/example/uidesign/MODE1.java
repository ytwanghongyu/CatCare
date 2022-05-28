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


public class MODE1 extends AppCompatActivity {
    
    //按钮声明
    private Button FeedButton;
    private Button ShitButton;
    private Button AutoButton;
    private Button MarginInquiryButton;
    //串口初始化参数（不能改
    private String devName = "/dev/ttyAMA3";//ttyAMA3 UART3
    private int speed = 115200;//波特率
    private int dataBits = 8;
    private int stopBits = 1;
    private int devfd = -1;
    public static MODE1 instance = null;

    String feed_str = "1\n";
    String clean_str = "2\n";
    String auto_str = "a\n";
    String margin_str = "b\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode1);

        //按钮初始化
        FeedButton = (Button) findViewById(R.id.feed_btn);
        ShitButton = (Button) findViewById(R.id.shit_btn);
        AutoButton = (Button) findViewById(R.id.auto_btn);
        MarginInquiryButton = (Button) findViewById(R.id.margin_inquiry_btn);

        //关闭其他页面
        if(MainActivity.instance!=null){
            MainActivity.instance.finish();
        }
        if( LOGIN.instance!=null){
            LOGIN.instance.finish();
        }
        if( MODE2.instance!=null){
            MODE2.instance.finish();
        }
        //喂食按钮触发函数
        FeedButton.setOnClickListener(new View.OnClickListener() {
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
        ShitButton.setOnClickListener(new View.OnClickListener() {
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

        //自动按钮触发函数
        AutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启串口
                if(devfd == -1){
                    devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                }
                //发送数据
                String str = auto_str;
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //余量查询按钮触发函数
        MarginInquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启串口
                if(devfd == -1){
                    devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                }
                //发送数据
                String str = margin_str;
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });
    }
}