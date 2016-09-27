package com.feicui.edu.highpart.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feicui.edu.highpart.MainActivity;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.adapter.LoginLogAdapter;
import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.LoginLog;
import com.feicui.edu.highpart.bean.User;
import com.feicui.edu.highpart.biz.UserManager;
import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.DensityUtil;
import com.feicui.edu.highpart.common.GetPictureUtil;
import com.feicui.edu.highpart.common.OkHttpUtil;
import com.feicui.edu.highpart.common.SharedPreferenceUtil;
import com.feicui.edu.highpart.common.SystemUtils;
import com.feicui.edu.highpart.common.UrlComposeUtil;
import com.feicui.edu.highpart.exception.URLErrorException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/9/19 0019.
 * <p/>
 * //1.下载用户数据 user_home?ver=版本号&imei=手机标识符&token =用户令牌
 * 2.解析，封装到一个对象user
 * 3.把数据设置到view中
 */
public class UserInfoFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_PICK = 2;
    boolean isShowing = false;//是否显示泡泡窗口
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.integral)
    TextView integral;
    @Bind(R.id.comment_count)
    TextView commentCount;

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.btn_exit)
    Button btnExit;
    private Context context;
    @Bind(R.id.icon)
    CircleImageView icon;
    private PopupWindow window;
    private MainActivity activity;

    @OnClick({R.id.icon, R.id.btn_exit})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.icon:
                showPopupWindow();
                break;
            case R.id.btn_exit:
                //清空账户信息
                SharedPreferenceUtil.saveAccount(context,null,null);
                activity.loadUserInfo();
                toolbar.performClick();
                break;
        }
    }

    private void showPopupWindow() {
        if (isShowing) {
            //如果泡泡窗口显示
            return;
        }
        window = new PopupWindow(context);
        window.setWidth(CommonUtil.getDisplayWidth(context));
//        window.setWidth(300);
        window.setHeight(DensityUtil.dip2px(context, 200));
        View view1 = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null);
        window.setContentView(view1);
        view1.findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPictureUtil.openCamera(UserInfoFragment.this);
                window.dismiss();
                isShowing =false;
            }
        });
        view1.findViewById(R.id.pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPictureUtil.openGallery(UserInfoFragment.this);
                window.dismiss();
                isShowing =false;
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//             UserInfoFragment.this.startActivityForResult(intent,REQUEST_PICK);
//                Bitmap bitmap = BitmapFactory.decodeFile(GetPictureUtil.getFilePathString(getActivity()));
//                icon.setImageBitmap(bitmap);
            }
        });
        view1.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                isShowing =false;
            }
        });
        //指定泡泡窗口显示的位置
        window.showAsDropDown(
                btnExit,//参照对象
                0,
                0 - DensityUtil.dip2px(context, 200)
        );
//        window.
        isShowing = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null) {
            //防止空指针出现
            return;
        }

        if (resultCode == Activity.RESULT_OK) {
            GetPictureUtil.onActivityResult(requestCode, data, this);
            String path = GetPictureUtil.getFilePathString(getActivity());
//            if (requestCode==REQUEST_PICK) {
//                Uri data1 = data.getData();
//                path = data1.getPath();
//            } else if (requestCode == REQUEST_CAMERA) {
//                Bundle extras = data.getExtras();
//            }
//            Glide.with(context)
//                    .load(path)//可以加载本地，也可以下载网络
//                    .centerCrop()//对bitmap像素缩放
//                    .placeholder(R.drawable.a9)//默认图片
//                    .crossFade()//动画效果
//                    .into(icon);//把下载的图片放到imageview中
            //上传图片到服务器 user_image?token=用户令牌& portrait =头像
            File file = new File(path);
            new UploadFileTask(file).execute(Const.URL_USER_IMAGE);
        }

//        GetPictureUtil.onActivityResult(requestCode, data, this);
//        if (resultCode == Activity.RESULT_OK) {
//            String pathString = GetPictureUtil.getFilePathString(getActivity());
//            Toast.makeText(context, "photo" + pathString, Toast.LENGTH_SHORT).show();
//            GetPictureUtil.clearTemp(getActivity());
//            Glide.with(context)
//                    .load(pathString)//可以加载本地，也可以下载网络
//                    .centerCrop()//对bitmap像素缩放
//                    .placeholder(R.drawable.a9)//默认图片
//                    .crossFade()//动画效果
//                    .into(icon);//把下载的图片放到imageview中
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, null);
        //第一步
        ButterKnife.bind(this, view);
        context = getContext();
//        下载用户数据
        loadUserInfo();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当activity创建的时候，回调
        if (getActivity() instanceof MainActivity) {
            activity = (MainActivity) getActivity();
            activity.setSupportActionBar(toolbar);//设置支持toolbar
            activity.backToMainActivity(toolbar);//给toolbar设置点击事件
//            activity.loadUserInfo();
        }
    }

    private void loadUserInfo() {
        Map<String, String> p = new HashMap<>();
        //user_home?ver=版本号&imei=手机标识符&token =用户令牌
        p.put("ver", CommonUtil.getVersionCode(context) + "");
        p.put("imei", SystemUtils.getIMEI(context));
        p.put("token", SharedPreferenceUtil.getToken(context));
        String urlPath = UrlComposeUtil.getUrlPath(Const.URL_USER_INFO, p);
        new LoadUserTask().execute(urlPath);
    }

    class UploadFileTask extends AsyncTask<String, Void, String> {

        private File file;

        public UploadFileTask(File file) {

            this.file = file;
        }

        @Override
        protected String doInBackground(String... params) {
//            UserManager m = new UserManager();
//            try {
//                return m.getUserInfo(params[0]);
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(context, "服务器访问失败", Toast.LENGTH_SHORT).show();
//            } catch (URLErrorException e) {
//                e.printStackTrace();
//                Toast.makeText(context, "参数有误", Toast.LENGTH_SHORT).show();
//            }
            try {
                return OkHttpUtil.postFile(params[0], file, SharedPreferenceUtil.getToken(getContext()));
                //Caused by: android.os.NetworkOnMainThreadException
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && s.contains("OK")) {
                loadUserInfo();//成功后，重新加载用户数据
                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
            }


        }
    }
    class LoadUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            UserManager m = new UserManager();
            try {
                return m.getUserInfo(params[0]);
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
            //解析返回字符串
            BaseEntity baseEntity = parseUserInfo(s);
            if (baseEntity == null) {
                return;
            }
            if (baseEntity.getStatus().equals("0")) {
                //成功 ，把数据设置到view中
                setDataToView(baseEntity);
            } else {
                //失败
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setDataToView(BaseEntity baseEntity) {
        User user = (User) baseEntity.getData();
        String portrait = user.getPortrait();
        name.setText(user.getUid());
        integral.setText("积分："+user.getIntegration());
        commentCount.setText(user.getComnum()+"");
        Glide.with(this).load(portrait)
        .centerCrop().into(icon);
//        icon.setImageResource(R.drawable.a9);
        List<LoginLog> loginlog = user.getLoginlog();
        LoginLogAdapter adapter = new LoginLogAdapter(context);
        adapter.appendData(loginlog,true);
        list.setAdapter(adapter);

    }

    private BaseEntity parseUserInfo(String s) {
        Gson g = new Gson();
        Type t = new TypeToken<BaseEntity<User>>() {
        }.getType();
        return g.fromJson(s, t);
    }
}
