package com.feicui.edu.highpart.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.Register;
import com.feicui.edu.highpart.biz.UserManager;
import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.SharedPreferenceUtil;
import com.feicui.edu.highpart.common.UrlComposeUtil;
import com.feicui.edu.highpart.exception.URLErrorException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class RegisterFragment extends android.support.v4.app.DialogFragment {
    private static final String TAG = "RegisterFragment";
    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_resgister, null);
        builder.setView(view);
        final EditText et_username = (EditText) view.findViewById(R.id.et_username);
        final EditText et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        final EditText et_email = (EditText) view.findViewById(R.id.et_email);
        context = getContext();


        view.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                register(
                        et_username.getText().toString(),
                        et_pwd.getText().toString(),
                        et_email.getText().toString()
                );
            }
        });

        return builder.create();
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        View view = inflater.inflate(R.layout.fragment_resgister, null);
//        final EditText et_username = (EditText) view.findViewById(R.id.et_username);
//        final EditText et_pwd = (EditText) view.findViewById(R.id.et_pwd);
//        final EditText et_email = (EditText) view.findViewById(R.id.et_email);
//        context = getContext();
//        view.findViewById(R.id.register).setOnClickListener(new View.OnClickListener()
//    {
//        @Override
//        public void onClick(View v)
//        {
//            //注册
//            register(
//                    et_username.getText().toString(),
//                    et_pwd.getText().toString(),
//                    et_email.getText().toString()
//            );
//        }
//    });
//
//        return view;
//    }

    private void register(String username, String pwd, String email) {
        //TODO 对用户名，密码，邮箱进行本地校验
        Map<String, String> p = new HashMap<>();
        // user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
        p.put("ver", CommonUtil.getVersionCode(context) + "");
        p.put("uid", username);
        p.put("email", email);
        p.put("pwd", pwd);
        String urlPath = UrlComposeUtil.getUrlPath(Const.URL_REGISTER, p);
        new RegisterTask().execute(urlPath);
    }
    class RegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            UserManager m = new UserManager();
            try {
                return m.register(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "服务器访问失败", Toast.LENGTH_SHORT).show();
            } catch (URLErrorException e) {
                e.printStackTrace();
                Toast.makeText(context, "参数有误", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Register registerInfo = parseRegister(s);
            if (registerInfo != null) {
                if (registerInfo.getResult().equals("0")) {
                    //注册成功,跳到用户信息界面,保存token值
                    SharedPreferenceUtil.saveToken(context,registerInfo.getToken());
                    //让dialog消失
                    dismiss();
                    //再跳转到用户信息界面
                    getFragmentManager().beginTransaction().replace(R.id.container,
                            new UserInfoFragment()
                    ).commit();
                } else {
                    //注册失败
                }
                Toast.makeText(context, registerInfo.getExplain(), Toast.LENGTH_SHORT).show();
            } else {
                //注册失败
                Toast.makeText(context, "failed ...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Register parseRegister(String jsonString) {
        Gson g = new Gson();
        Type t = new TypeToken<BaseEntity<Register>>() {
        }.getType();
        BaseEntity entity = g.fromJson(jsonString, t);
        return (Register) entity.getData();
    }
}
