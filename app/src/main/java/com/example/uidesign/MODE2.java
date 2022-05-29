package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlyarm.FriendlyThings.HardwareControler;

import java.util.Timer;
import java.util.TimerTask;


public class MODE2 extends AppCompatActivity {
    private Button DistanceButton   ;
    private Button ColorButton      ;
    private Button SoundWaveButton  ;
    private Button ServoButton      ;
    private Button SlipWayButton1   ;
    private Button SlipWayButton2   ;
    private TextView DistanceText   ;
    private TextView ColorText      ;   
    private TextView SoundWaveText  ;

    //串口初始化参数
    private String devName = "/dev/ttyAMA3";//串口地址 ttyAMA3 UART3
    private int speed = 115200  ;//波特率
    private int dataBits = 8    ;//数据位
    private int stopBits = 1    ;//停止位
    private int devfd = -1      ;//串口句柄


    public static MODE2 InstanceMode2 = null;

    
    private  int state = 0;//待机状态

    //串口发送的字符串
    String  DistanceSendStr     = "3";//距离传感器测试发送的字符串
    String  ColorSendStr        = "4";//颜色传感器测试发送的字符串
    String  SoundWaveSendStr    = "5";//超声波传感器测试发送的字符串
    String  ServoSendStr        = "6";//舵机测试发送的字符串
    String  SlipWaySendStr1     = "7";//滑台1测试发送的字符串
    String  SlipWaySendStr2     = "8";//滑台2测试发送的字符串

    private final int BUFSIZE = 512;
    private byte[] buf = new byte[BUFSIZE];
    private Timer timer = new Timer();

    private TimerTask task= new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
            //测试代码 记得删

        }
    };
    //计时器里的处理函数
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (com.friendlyarm.FriendlyThings.HardwareControler.select(devfd, 0, 0) == 1) {       //判断是否有数据可读
                        int retSize = com.friendlyarm.FriendlyThings.HardwareControler.read(devfd, buf, BUFSIZE);    //读取数据；要读取的数据都是返回值，一般返回值都是函数运行结果的状态
                        
                        if (retSize > 0) {
                            String str1 = new String(buf, 0, retSize);
                            //待机状态
                            if(state == 0){
                                break;
                            }
                            //距离传感器收处理
                            else if (state == 3) {

                                //获取字符串
                                String str_get = new String(buf, 0, retSize);
                                //str_decode:解码后的str
                                char[] str_dcd = new char[3];
                                //解码
                                str_get.getChars(2, 5, str_dcd, 0);
                                //char 转 string
                                String str_dis = String.valueOf(str_dcd);
                                str_dis = str_dis + "mm";
                                //显示
                                DistanceText.setText(str_dis);

                            }
                            //颜色传感器收处理 
                            else if (state == 5){
                                //获取字符串
                                String str_get = new String(buf, 0, retSize);
                                //str_decode:解码后的str
                                char[] str_key = new char[2];
                                char[] str_dcdR = new char[3];
                                char[] str_dcdG = new char[3];
                                char[] str_dcdB = new char[3];
                                //解码
                                str_get.getChars (0, 2, str_key, 0);
                                str_get.getChars(2,5,str_dcdR,0);
                                str_get.getChars(5,8,str_dcdG,0);
                                str_get.getChars(8,11,str_dcdB,0);
                                //颜色传感器 判断str_key是否为"02"
                                if(str_key[0] == '0' && str_key[1] == '2'){
                                    //获取颜色
                                    String str_colorR = new String(str_dcdR);
                                    String str_colorG = new String(str_dcdG);
                                    String str_colorB = new String(str_dcdB);
                                    //str_colorR开头加"R:"
                                    //str_colorG开头加",G:"
                                    //str_colorB开头加",B:"
                                    ColorText.setText("R:"+str_colorR+",G:"+str_colorG+",B:"+str_colorB);
                                }
                                else{
                                    //设置颜色
                                    ColorText.setText("获取RGB失败");
                                }

                                break;
                            }
                            //超声波传感器收处理，the same as距离传感器收处理，but "mm" is changed to "%"
                            else if (state == 4){
                                //获取字符串
                                String str_get = new String(buf, 0, retSize);
                                //str_decode:解码后的str
                                char[] str_key = new char[2];
                                char[] str_decode = new char[3];
                                //解码
                                str_get.getChars (0, 2, str_key, 0);
                                str_get.getChars(2,5,str_decode,0);
                                //超声波传感器 判断str_key是否为"03"
                                if(str_key[0] == '0' && str_key[1] == '3'){
                                    //获取距离
                                    String str_distance = new String(str_decode);
                                    //use a for loop to remove all "0" before the first non-"0" char of SoundWaveText, if SoundWaveText are all "0", then set SoundWaveText to "0"
                                    for(int i = 0; i < str_distance.length(); i++){
                                        if(str_distance.charAt(i) != '0'){
                                            str_distance = str_distance.substring(i);
                                            break;
                                        }
                                    }
                                    //str_distance结尾加"%"
                                    SoundWaveText.setText(str_distance+"%");
                                }
                                else{
                                    //设置%
                                    SoundWaveText.setText("获取失败");
                                }
                                 break;
                            }

                        }
                    }
                    break;
            }
            super.handleMessage(msg); // 帮助处理信息的一个类
        }
    };

    @Override
    protected void onDestroy() {
        task.cancel();
        timer.cancel();


        super.onDestroy();
    }



    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode2);
        //关闭其他页面
        InstanceMode2 = this;



        if( MainActivity.InstanceMain!=null){
            MainActivity.InstanceMain.finish();
        }

        //串口开启
        devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );


        DistanceButton      =findViewById(R.id.distance_btn );
        SoundWaveButton     =findViewById(R.id.soundwave_btn);
        ColorButton         =findViewById(R.id.color_btn    );
        ServoButton         =findViewById(R.id.servo_btn    );
        SlipWayButton1      =findViewById(R.id.slipway_btn_1);
        SlipWayButton2      =findViewById(R.id.slipway_btn_2);
        DistanceText        =findViewById(R.id.distance_txt );
        ColorText           =findViewById(R.id.color_txt    );
        SoundWaveText       =findViewById(R.id.soundwave_txt);

        //开启计时器事件
        if (devfd >= 0) {
            timer.schedule(task, 0, 200);
        } else {
            devfd = -1;
            Toast.makeText(MODE2.this,"Failed  to  open....",Toast.LENGTH_LONG).show();
        }



        //按钮点击事件

        //距离传感器测试 按钮触发函数
        DistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口开启
                devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );

                //发送数据
                String str = DistanceSendStr;
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                int ret=com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
                if(ret>0){
                    DistanceText.setText("等待数据传输...");
                    //设置状态
                            state = 3;
                }
                else {
                    DistanceText.setText("串口发送失败");
                }
            }
        });





        //超声波测距传感器测试 按钮触发函数
        SoundWaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口开启
                devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );

                //发送数据
                String str = SoundWaveSendStr;
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                int ret=com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
                if(ret>0){
                    SoundWaveText.setText("等待数据传输...");
                    //设置状态
                    state = 4;
                }
                else {
                    SoundWaveText.setText("串口发送失败");
                }
            }
        });
//颜色传感器测试 按钮触发函数
        ColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启串口
                //串口开启
                devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );

                //发送数据
                String str = ColorSendStr;
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                int ret=com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
                if(ret>0){
                    ColorText.setText("等待数据传输...");
                    //设置状态
                    state = 5;
                }
                else {
                    ColorText.setText("串口发送失败");
                }
            }
        });
        //舵机测试 按钮触发函数
        ServoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口开启
                devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                //设置状态
                state = 6;
                //发送数据
                String str = ServoSendStr;
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });



        //滑台1测试 按钮触发函数
        SlipWayButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口开启
                devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                //设置状态
                state = 7;
                //发送数据
                String str = SlipWaySendStr1;
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //滑台2测试 按钮触发函数
        SlipWayButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //串口开启
                devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                //设置状态
                state = 8;
                //发送数据
                String str = SlipWaySendStr2;
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });
    }
}