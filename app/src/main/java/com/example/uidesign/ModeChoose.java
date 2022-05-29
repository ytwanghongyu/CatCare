
package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.media.MediaPlayer;
import java.util.Locale;
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

public class ModeChoose extends AppCompatActivity {

    private static final String TAG = "SerialPort";
    private Button language1;//定义语言切换按钮
    private Button mode1;//定义操作模式按钮
    private Button mode2;//定义维护模式按钮


    public static ModeChoose instance = null;

    //语音播报状态
    int VoiceHelp = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modechoose);

        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();

        //语音播报
        if(VoiceHelp == 1){
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.helpen);
            mediaPlayer.start();
        }

        //关闭其他页面
       // MainActivity.instance.finish();
        if( LOGIN.instance!=null){
            LOGIN.instance.finish();
        }
        if( MODE1.instance!=null){
            MODE1.instance.finish();
        }
        if( MODE2.instance!=null){
            MODE2.instance.finish();
        }



        //切换语言BUTTON 跳转 ModeChoose2
        language1=findViewById(R.id.language1);
        language1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                //修改配置
                LanguageSwitch.settingLanguage(ModeChoose.this,LanguageSwitch.getInstance());
                //activity銷毀重建
                ModeChoose.this.recreate();
                //如果正在播放，停止当前播放
                if(VoiceHelp == 1){
                    MediaPlayer mediaPlayer = MediaPlayer.create(ModeChoose.this, R.raw.helpen);
                    mediaPlayer.stop();
                    VoiceHelp = 0;
                }


            }
        });


        //操作模式BUTTON 跳转mode1
        mode1=findViewById(R.id.mode1);
        mode1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeChoose.this, MODE1.class);
                startActivity(intent);
            }
        });

        //维护模式BUTTON 跳转mode2
        mode2=findViewById(R.id.mode2);
        mode2.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeChoose.this, LOGIN.class);
                startActivity(intent);
            }
        });
    }


}
