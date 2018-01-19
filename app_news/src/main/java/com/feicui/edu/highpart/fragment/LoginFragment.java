package com.feicui.edu.highpart.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.feicui.edu.highpart.MainActivity;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.Register;
import com.feicui.edu.highpart.biz.UserManager;
import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.SharedPreferenceUtil;
import com.feicui.edu.highpart.common.SystemUtils;
import com.feicui.edu.highpart.exception.URLErrorException;
import com.feicui.edu.highpart.retrofit.LoginService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.feicui.edu.highpart.R.id.register;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class LoginFragment extends Fragment {

    private Context context;
    private Toolbar toolbar;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当activity创建的时候，回调
        if (getActivity() instanceof MainActivity) {
            final MainActivity activity = (MainActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.backToMainActivity(toolbar);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_login, null);
        final EditText et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        final EditText et_username = (EditText) view.findViewById(R.id.et_username);
        context = getContext();
        view.findViewById(register).setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //让注册对话框显示出来
                        new RegisterFragment().show(getFragmentManager(),null);
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container_login, new RegisterFragment()).commit();

                    }
                });
        view.findViewById(R.id.login).setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //登入
                        String username = et_username.getText().toString();
                        String pwd = et_pwd.getText().toString();
                        login(
                                username,
                                pwd
                        );
                    }
                });
        view.findViewById(R.id.forgetPwd).setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //忘记密码
                        new ForgetPwdFragment().show(getFragmentManager(),"");
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container_login, new ForgetPwdFragment()).commit();

                    }
                });
        return view;
    }
    private void login(final String username, final String pwd) {
        Map<String, String> p = new HashMap<>();
        //TODO 对用户名，密码，邮箱进行本地校验
//        * http://118.244.212.82:9094//newsClient/user_login?uid=admin&pwd=admin&
//        * imei=abc&ver=1&device=1
        p.put("uid", username);
        p.put("pwd", pwd);
        p.put("imei", SystemUtils.getIMEI(context));
        p.put("ver", CommonUtil.getVersionCode(context) + "");
        p.put("device", Const.PHONE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(
"http://118.244.212.82:9092/newsClient/user_login?uid=get&pwd=get&imei=1&ver=1&device=1"
                ).addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService service = retrofit.create(LoginService.class);
        Call<BaseEntity<Register>> call = service.login(/*p*/);
        call.enqueue(new Callback<BaseEntity<Register>>()
        {
            @Override
            public void onResponse(Call<BaseEntity<Register>> call, Response<BaseEntity<Register>> response)
            {
                boolean successful = response.isSuccessful();
                if (!successful)
                {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseEntity<Register> entity = response.body();
                Register loginInfo = entity.getData();
                String status = entity.getStatus();
                if ("0".equals(status)) {

                    if (loginInfo.getResult().equals("0")) {
                        //登入成功,保存token值
                        SharedPreferenceUtil.saveToken(context,loginInfo.getToken());
                        //保存用户名和密码
                        SharedPreferenceUtil.saveAccount(context,username,pwd);
                        getFragmentManager().beginTransaction().replace(R.id.container,
                                new UserInfoFragment()
                        ).commit();
                    }
                    Toast.makeText(context, loginInfo.getExplain(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, entity.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BaseEntity<Register>> call, Throwable t)
            {

            }
        });
//        String urlPath = UrlComposeUtil.getUrlPath(Const.URL_LOGIN, p);
//        new LoginTask(username,pwd).execute(urlPath);
    }

    class LoginTask extends AsyncTask<String, Void, String> {

        private final String username;
        private final String pwd;

        public LoginTask(String username, String pwd) {
            this.username = username;
            this.pwd = pwd;
        }

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
            BaseEntity entity = parseUser(s);
            String status = entity.getStatus();
            if ("0".equals(status)) {
                Register registerInfo = (Register) entity.getData();

                if (registerInfo.getResult().equals("0")) {
                    //登入成功,保存token值
                    SharedPreferenceUtil.saveToken(context,registerInfo.getToken());
                    //保存用户名和密码
                    SharedPreferenceUtil.saveAccount(context,username,pwd);
                    getFragmentManager().beginTransaction().replace(R.id.container,
                            new UserInfoFragment()
                    ).commit();
                } else {
                    //失败
                }
                Toast.makeText(context, registerInfo.getExplain(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, entity.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public BaseEntity parseUser(String jsonString) {
        Gson g = new Gson();
        Type t = new TypeToken<BaseEntity<Register>>() {
        }.getType();
        return g.fromJson(jsonString, t);
    }
}
