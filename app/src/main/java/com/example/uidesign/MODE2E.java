package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MODE2E extends AppCompatActivity {
    private Button DistanceButtonE       ;
    private Button DistanceSoundButtonE  ;
    private Button ColorButtonE          ;
    private Button TemperatureButtonE    ;
    private Button ServoButton1E         ;
    private Button ServoButton2E         ;
    private Button SlipWayButtonE        ;
    private int devfd = -1;

    public static MODE2E instance = null;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode2_e);
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

        DistanceButtonE      =findViewById(R.id.distance_btn_e);
        DistanceSoundButtonE =findViewById(R.id.distance_sound_btn_e);
        ColorButtonE         =findViewById(R.id.color_btn_e);
        TemperatureButtonE   =findViewById(R.id.temperature_btn_e);
        ServoButton1E        =findViewById(R.id.servo_btn_1_e);
        ServoButton2E        =findViewById(R.id.servo_btn_2_e);
        SlipWayButtonE       =findViewById(R.id.slipway_btn_e);


        //距离传感器测试 按钮触发函数
        DistanceButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘3’
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "3";
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //超声波测距传感器测试 按钮触发函数
        //暂时没用
        DistanceSoundButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘’，表示
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "";
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //颜色传感器测试 按钮触发函数
        ColorButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘4’
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "4";
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //温度湿度传感器测试 按钮触发函数
        //暂时没用
        TemperatureButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘’
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "";
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //舵机1测试 按钮触发函数
        ServoButton1E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘5’
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "5";
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //舵机2测试 按钮触发函数
        ServoButton2E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘6’
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "6";
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //滑台测试 按钮触发函数
        SlipWayButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口发送‘7’
                //编码见   上位机toMCU_通信格式编码.pdf
                String str = "7";
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });




    }
}