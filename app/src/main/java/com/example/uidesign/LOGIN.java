package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LOGIN extends AppCompatActivity {
    private Button login;//定义登录按钮
    public static LOGIN InstanceLogin = null;
    EditText name;  //创建账号
    EditText passwd;  //创建密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InstanceLogin = this;

        if( MainActivity.InstanceMain!=null){
            MainActivity.InstanceMain.finish();
        }


        login=findViewById(R.id.login);

        name = findViewById(R.id.username);         //获取输入的账号
        passwd = findViewById(R.id.password);     //获取输入的密码

        String SuccessfullyLogin = (String) getResources().getText(R.string.SuccessfullyLogin);
        String FailedLogin = (String) getResources().getText(R.string.FailedLogin);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //这是能够登录的账号密码
                String Usename = "ad";
                String Upwd = "ad";

                //创建两个String类，储存从输入文本框获取到的内容
                String user = name.getText().toString().trim();
                String pwd = passwd.getText().toString().trim();

                //进行判断，如果两个内容都相等，就显现第一条语句，反之，第二条。
                if(user.equals(Usename) & pwd.equals(Upwd)){
                    Toast.makeText(LOGIN.this,SuccessfullyLogin , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LOGIN.this, MODE2.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LOGIN.this, FailedLogin, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}