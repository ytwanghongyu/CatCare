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

import java.security.PrivateKey;
import java.util.Timer;
import java.util.TimerTask;


public class MODE1 extends AppCompatActivity {
    public static MainActivity instance ;
    //按钮声明
    private Button FeedButton;
    private Button ShitButton;
    private Button AutoButton;
    private Button MarginInquiryButton;
    private TextView MarginText;
    //串口初始化参数（不能改
    private String devName = "/dev/ttyAMA3";//ttyAMA3 UART3
    private int speed = 115200;//波特率
    private int dataBits = 8;
    private int stopBits = 1;
    private int devfd = -1;
    public static MODE1 InstanceMode1 = null;

    String feed_str = "1";
    String clean_str = "2";
    String auto_str = "a";
    String margin_str = "5";
    String auto_last_str = "z";
    String auto_stop_str = "d";

    //状态初始化：手动模式
    int state = 0;

    int recieve_state = 0;

    private final int BUFSIZE = 512;
    private byte[] buf = new byte[BUFSIZE];
    private Timer timer = new Timer();
    public TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

            //如果是自动模式，开启串口并发送“z”
            if(state==1){

                    devfd = HardwareControler.openSerialPort(devName, speed, dataBits, stopBits);
                    //发送数据
                    String str = auto_last_str;
                    //如果str结尾没有\n，则加上
                    if(str.charAt(str.length()-1) != '\n'){
                        str += "\n";
                    }
                    //串口发送
                    com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());

            }


        }
    };
    //计时器里的处理函数
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (HardwareControler.select(devfd, 0, 0) == 1) {       //判断是否有数据可读
                        int retSize = HardwareControler.read(devfd, buf, BUFSIZE);    //读取数据；要读取的数据都是返回值，一般返回值都是函数运行结果的状态
                        
                        if (retSize > 0) {
                            String str1 = new String(buf, 0, retSize);
                            
                            //待机状态
                            if(recieve_state==0){
                                break;
                            }
                            
                            //接收到数据
                            //超声波传感器收处理，the same as距离传感器收处理，but "mm" is changed to "%"
                            else if (recieve_state==1){
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
                                    MarginText.setText(str_distance+"%");
                                }
                                else{
                                    //设置%
                                    MarginText.setText("获取失败");
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
        if(devfd!=-1) {
            HardwareControler.close(devfd);
            devfd=-1;
        }
        super.onDestroy();
    }

    public void onPause(){
        if(devfd!=-1){
            HardwareControler.close(devfd);
            devfd=-1;
        }
        super.onPause();
    }

    public void onStop(){
        if(devfd!=-1){
            HardwareControler.close(devfd);
            devfd=-1;
        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode1);
        InstanceMode1 = this;
        devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
        //开启计时器事件
        if (devfd >= 0) {
            timer.schedule(task, 0, 500);
        } else {
            devfd = -1;
            Toast.makeText(MODE1.this,"Failed  to  open....",Toast.LENGTH_LONG).show();
        }

        //按钮初始化
        FeedButton = (Button) findViewById(R.id.feed_btn);
        ShitButton = (Button) findViewById(R.id.shit_btn);
        AutoButton = (Button) findViewById(R.id.auto_btn);
        MarginInquiryButton = (Button) findViewById(R.id.margin_inquiry_btn);
        MarginText = findViewById(R.id.margin_txt);


        if( MainActivity.InstanceMain!=null){
            MainActivity.InstanceMain.finish();
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
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
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
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
            }
        });

        //自动按钮触发函数
        AutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果状态是手动模式，则转换为自动模式
                if(state == 0){
                    //开启串口
                    if(devfd == -1){
                        devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                    }
                    //发送数据
                    String str = auto_str;
                    //如果str结尾没有\n，则加上
                    if(str.charAt(str.length()-1) != '\n'){
                        str += "\n";
                    }
                    //串口发送
                    com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
                    //切换按钮显示@String/ManualOperate
                     AutoButton.setText(R.string.ManualOperate);
                    //更改状态
                    state = 1;
                }


                
                
                //如果状态是自动模式，则转换为手动模式
                else if(state == 1){
                    //开启串口
                    if(devfd == -1){
                        devfd = HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );
                    }
                    //发送数据
                    String str = auto_stop_str;
                    //如果str结尾没有\n，则加上
                    if(str.charAt(str.length()-1) != '\n'){
                        str += "\n";
                    }
                    //串口发送
                    com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
                    //切换按钮显示@String/AutoOperate
                    AutoButton.setText(R.string.AutoOperate);
                    //更改状态
                    state = 0;
                }

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
                //如果str结尾没有\n，则加上
                if(str.charAt(str.length()-1) != '\n'){
                    str += "\n";
                }
                //串口发送
                com.friendlyarm.FriendlyThings.HardwareControler.write(devfd, str.getBytes());
                recieve_state = 1;
            }
        });
    }
}