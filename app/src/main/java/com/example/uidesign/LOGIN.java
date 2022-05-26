package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LOGIN extends AppCompatActivity {
    private Button login;//定义登录按钮
    public static LOGIN instance = null;
    EditText name;  //创建账号
    EditText passwd;  //创建密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //关闭其他页面
        if(MainActivity.instance!=null){
            MainActivity.instance.finish();
        }
        if(MainActivity2.instance!=null){
            MainActivity2.instance.finish();
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
        login=findViewById(R.id.login);

        name = findViewById(R.id.username);         //获取输入的账号
        passwd = findViewById(R.id.password);     //获取输入的密码
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
                    Toast.makeText(LOGIN.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LOGIN.this, MODE2.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LOGIN.this, "身份验证错误，禁止访问", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}