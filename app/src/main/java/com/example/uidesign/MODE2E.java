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

public class MODE2E extends AppCompatActivity {
    private Button DistanceButton;
    private Button SoundWaveButton;
    private Button ColorButton;
    private Button ServoButton;
    private Button SlipWayButton1;
    private Button SlipWayButton2;
    private TextView DistanceText;
    private TextView ColorText;
    private TextView SoundWaveText;
    
    
    private int devfd = -1;

    public static MODE2E instance = null;

    private  int state = 0;

    String  DistanceSendStr     = "3\n";//距离传感器测试发送的字符串
    String  ColorSendStr        = "4\n";//颜色传感器测试发送的字符串
    String  SoundWaveSendStr    = "5\n";//超声波传感器测试发送的字符串
    String  ServoSendStr        = "6\n";//舵机测试发送的字符串
    String  SlidWaySendStr1     = "7\n";//滑台1测试发送的字符串
    String  SlidWaySendStr2     = "8\n";//滑台2测试发送的字符串

    private final int BUFSIZE = 512;
    private byte[] buf = new byte[BUFSIZE];
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
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
                            
                            //距离传感器收处理
                            if(state == 0){
                                break;
                            }
                            else if (state == 3){
                                //获取字符串
                                String str_get = new String(buf, 0, retSize);
                                //str_decode:解码后的str
                                char[] str_key = new char[2];
                                char[] str_decode = new char[3];
                                //解码
                                str_get.getChars (0, 2, str_key, 0);
                                str_get.getChars(2,5,str_decode,0);
                                //判断str_key是否为"01"
                                if(str_key[0] == '0' && str_key[1] == '1'){
                                    //获取距离
                                    String str_distance = new String(str_decode);
                                    //str_distance结尾加"mm"
                                    DistanceText.setText(str_distance+"mm");
                                    //设置距离
                                    DistanceText.setText(str_distance);
                                }
                                else{
                                    //设置距离
                                    DistanceText.setText("获取距离失败");
                                }
                                //状态复位
                                state = 0;
                            }
                            //颜色传感器收处理 
                            else if (state == 4){
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
                                    //设置颜色
                                    ColorText.setText(str_colorR+" "+str_colorG+" "+str_colorB);
                                }
                                else{
                                    //设置颜色
                                    ColorText.setText("获取颜色失败");
                                }
                                //状态复位
                                state = 0;
                            }
                            //超声波传感器收处理，the same as距离传感器收处理，but "mm" is changed to "%"
                            else if (state == 5){
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
                                    //设置距离
                                    SoundWaveText.setText(str_distance);
                                }
                                else{
                                    //设置距离
                                    SoundWaveText.setText("获取距离失败");
                                }
                                //状态复位
                                state = 0;
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

        DistanceButton      =findViewById(R.id.distance_btn_e);
        SoundWaveButton     =findViewById(R.id.soundwave_btn_e);
        ColorButton         =findViewById(R.id.color_btn_e);
        ServoButton         =findViewById(R.id.servo_btn_e);
        SlipWayButton1       =findViewById(R.id.slipway_btn_1_e);
        SlipWayButton2       =findViewById(R.id.slipway_btn_2_e);
        DistanceText        =findViewById(R.id.distance_txt_e );
        ColorText           =findViewById(R.id.color_txt_e    );
        SoundWaveText       =findViewById(R.id.soundwave_txt_e);






    }
}