
package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import com.friendlyarm.FriendlyThings.HardwareControler;

import android.os.Message;

public class MainActivity extends AppCompatActivity {

    public static MainActivity InstanceMain;

    private String devName = "/dev/ttyAMA3";//串口地址 ttyAMA3 UART3
    private int speed = 115200  ;//波特率
    private int dataBits = 8    ;//数据位
    private int stopBits = 1    ;//停止位
    private int devfd = -1      ;//串口句柄



    String  init_str  = "b" ;//初始化发送的字符串
    String  success_init_str = "c";//初始化成功响应字符串
    int open=0;

    private final int BUFSIZE = 512;
    private byte[] buf = new byte[BUFSIZE];
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
            //串口开启
            devfd = com.friendlyarm.FriendlyThings.HardwareControler.openSerialPort( devName, speed, dataBits, stopBits );

            //向MCU发送初始化请求
            if(open==0){
                //补换行符\n
                String str = init_str;
                if (str.charAt(str.length()-1) != '\n') {
                    str = str + "\n";
                }
                //串口写b 详见编码.pdf
                HardwareControler.write(devfd, str.getBytes());
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
                            //在这写收到的判断
                            if( str1.equals("5")){
                                Toast.makeText( MainActivity.this,"已进入工作范围，请操作", Toast.LENGTH_SHORT).show();
                                //向MCU发送初始化成功响应
                                open = 1;
                                //补换行符\n
                                String str = success_init_str;
                                if (str.charAt(str.length()-1) != '\n') {
                                    str = str + "\n";
                                }
                                //串口写c 详见编码.pdf
                                HardwareControler.write(devfd, str.getBytes());
                                //页面跳转
                                Intent intent = new Intent(MainActivity.this, ModeChoose.class);
                                startActivity(intent);

                            }
                        }
                    }
                    break;
            }
            super.handleMessage(msg); // 帮助处理信息的一个类
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        InstanceMain = this;

        if( LOGIN.InstanceLogin!=null){
            LOGIN.InstanceLogin.finish();
        }
        if( MODE1.InstanceMode1!=null){
            MODE1.InstanceMode1.finish();
        }
        if( ModeChoose.InstanceModeChoose!=null){
            ModeChoose.InstanceModeChoose.finish();
        }
        if( MODE2.InstanceMode2!=null){
            MODE2.InstanceMode2.finish();
        }

        //打开串口
        devfd = HardwareControler.openSerialPort(devName,speed,dataBits,stopBits);


        // 设备是否开启判别
        if (devfd >= 0) {
            timer.schedule(task, 0, 1000);
        } else {
            Toast.makeText(MainActivity.this,"Failed  to  open....",Toast.LENGTH_LONG).show();
        }


    }
}
