package com.feicui.edu.highpart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.feicui.edu.highpart.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_login, new LoginFragment()).commit();

    }
}
