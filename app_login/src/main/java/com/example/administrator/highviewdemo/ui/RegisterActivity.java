package com.example.administrator.highviewdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.highviewdemo.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText et_un = (EditText) findViewById(R.id.et_un);
        EditText et_pwd = (EditText) findViewById(R.id.et_pwd);
        Button submit = (Button) findViewById(R.id.button);
        assert submit != null;
        assert et_un != null;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = et_un.length();
                if (length >=6&& length <=16)
                {
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                    //关闭注册页面之前，把用户传递给登入界面
                    Intent data = new Intent();
                    data.putExtra("username",et_un.getText().toString());
                    setResult(Activity.RESULT_OK,data);
                    //结束当前Activity
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"用户名不合法，长度必须为6-16",Toast.LENGTH_LONG).show();
                    //把输入框的用户名清空
                    et_un.setText("");
                    //让用户名的编辑框获取焦点
                }
            }
        });
    }

}
