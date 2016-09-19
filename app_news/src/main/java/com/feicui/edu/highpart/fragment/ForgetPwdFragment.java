package com.feicui.edu.highpart.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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
import com.feicui.edu.highpart.common.UrlComposeUtil;
import com.feicui.edu.highpart.exception.URLErrorException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class ForgetPwdFragment extends DialogFragment {

    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_forget_pwd, null);
        builder.setView(view);

        final EditText et_email = (EditText) view.findViewById(R.id.et_email);
        context = getContext();
        view.findViewById(R.id.btn_find_pwd).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击按钮，执行找回密码的动作
                        findPwd(
                                et_email.getText().toString().trim()
                        );
                    }
                }
        );
        return builder.create();
    }

    //找回密码
    private void findPwd(String email) {
        //?ver=" + args[0] + "&email=" + args[1]
        //TODO 校验邮箱的格式是否符合规则

        Map<String, String> map = new HashMap<>();
        map.put("ver", CommonUtil.getVersionCode(context) + "");
        map.put("email", email);
        String urlPath = UrlComposeUtil.getUrlPath(Const.URL_FORGET_PWD, map);

        //异步任务请求网络。。。
        new FindTask().execute(urlPath, "ttt");
    }

    class FindTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                //获取参数
                String url = params[0];
                //String ttt = params[1];
                UserManager userManager = new UserManager();
                return userManager.forgetPwd(url);
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
            if (s != null) {
                //对json字符串进行解析
                //{"message":"OK","status":0,"data":{"result":0,"explain":"已成功发送到邮箱"}}
                BaseEntity baseEntity = parseResult(s);
                Register data = (Register) baseEntity.getData();
                if ("0".equals(baseEntity.getStatus())) {
                    //成功找回
                } else {
                    //失败
                }
                Toast.makeText(context, data.getExplain(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "找回密码失败", Toast.LENGTH_SHORT).show();
            }
        }

        private BaseEntity parseResult(String s) {
            Gson g  = new Gson();
            Type t = new TypeToken<BaseEntity<Register>>(){}.getType();

            BaseEntity entity = g.fromJson(s, t);
            return entity;
        }
    }


}
