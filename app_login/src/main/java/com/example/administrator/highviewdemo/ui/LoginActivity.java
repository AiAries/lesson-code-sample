package com.example.administrator.highviewdemo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.highviewdemo.R;

public class LoginActivity extends AppCompatActivity {

    //ctrl +shift +U
    public static final int MAIN_REQUEST_CODE = 0;
    private EditText et_pwd;
    private EditText et_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = (EditText) findViewById(R.id.et_un);
        et_pwd = (EditText) findViewById(R.id.password);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox11);
        final SharedPreferences preferences1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences1.edit().putBoolean("isAutoLogin",isChecked).apply();
            }
        });
        //auto login ?
        boolean isAutoLogin = preferences1.getBoolean("isAutoLogin", false);
        if (isAutoLogin)
        {
            //从共享首选项获取数据
            String uname = preferences1.getString("username", "-1");
            String pwd = preferences1.getString("password", "-1");
            if (!uname.equals("-1")&&!pwd.equals("-1"))
            {
                et_username.setText(uname);
                et_pwd.setText(pwd);
                checkBox.setChecked(isAutoLogin);
                jumpToMainActivity();
            }

        }
    }

    public void login(View v) {
        //获取edittext身上的数据
        String u = et_username.getText().toString();
        //获取密码
        String p = et_pwd.getText().toString();
        if ("z".equals(u)&&"1".equals(p))
        {
            Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_SHORT).show();
            //存的代码
            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("username",u);
            edit.putString("password",p);
            edit.apply();
            jumpToMainActivity();
        }
        else {
            Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
       /* Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:6666"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        *//*默认会添加这个类型*//*
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);*/
    }

    private void jumpToMainActivity() {
        Intent intent = new Intent() ;
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    public void register(View v)
	{
//        startActivity(new Intent(this,RegisterActivity.class));
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, MAIN_REQUEST_CODE);
	}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode== Activity.RESULT_OK)
       {
           if (requestCode==MAIN_REQUEST_CODE)
           {
               String username = data.getStringExtra("username");
               Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
               et_username.setText(username);
           }
       }
    }
}
