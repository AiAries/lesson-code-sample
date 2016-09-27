package com.feicui.edu.highpart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feicui.edu.highpart.asyntask.HttpUtil;
import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.NewsGroup;
import com.feicui.edu.highpart.bean.User;
import com.feicui.edu.highpart.biz.UserManager;
import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.SharedPreferenceUtil;
import com.feicui.edu.highpart.common.SystemUtils;
import com.feicui.edu.highpart.common.UrlComposeUtil;
import com.feicui.edu.highpart.exception.URLErrorException;
import com.feicui.edu.highpart.fragment.CommentFragment;
import com.feicui.edu.highpart.fragment.FavoriteFragment;
import com.feicui.edu.highpart.fragment.LocalFragment;
import com.feicui.edu.highpart.fragment.LoginFragment;
import com.feicui.edu.highpart.fragment.NewsGroupFragment;
import com.feicui.edu.highpart.fragment.PicFragment;
import com.feicui.edu.highpart.fragment.UserInfoFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String PREFERENCES_FILE = "mymaterialapp_settings";
    private boolean mUserLearnedDrawer;
    private CircleImageView header;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);

        View view = View.inflate(this, R.layout.drawer_header, null);
        mNavigationView.addHeaderView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否跳转到登入界面，同时关闭抽屉mNavigationView
                mDrawerLayout.closeDrawer(mNavigationView);
                //取出用户名和密码
                String[] account = SharedPreferenceUtil.getAccount(MainActivity.this);
                String username = account[0];
                String pwd = account[1];
                if (username == null || pwd == null) {
                    //用户名密码为空，跳到登入界面
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.container, new LoginFragment()).commit();
                } else {
                    //跳到用户信息界面
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.container, new UserInfoFragment()).commit();
                }

            }
        });
        header = (CircleImageView) view.findViewById(R.id.iv_header);
        username = (TextView) view.findViewById(R.id.tv_username);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new NewsGroupFragment()).commit();
                        break;
                    case R.id.navigation_item_2:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new FavoriteFragment()).commit();
                        break;
                    case R.id.navigation_item_3:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new LocalFragment()).commit();
                        break;

                    case R.id.navigation_item_4:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new CommentFragment()).commit();
                        break;
                    case R.id.navigation_item_5:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new PicFragment()).commit();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer(mNavigationView);
                return true;

            }
        });
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.
                add(R.id.container, new NewsGroupFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //当返回键被按下的时候，回调此方法
        //连续按两次，根据两次点击的时间差
        if (CommonUtil.isFastDoubleClick()) {
            finish();//退出界面
        } else {
            Toast.makeText(MainActivity.this, "请再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //加载用户信息
        if (SharedPreferenceUtil.getAccount(this)[0] != null) {
            loadUserInfo();
        }
    }

    public void loadUserInfo() {
        if (SharedPreferenceUtil.getAccount(this)[0] == null) {
            //用户名为空就设置默认图片头像
            username.setText("登入");
            header.setImageResource(R.mipmap.biz_pc_main_info_profile_avatar_bg_dark);
            return;
        }
        Map<String, String> p = new HashMap<>();
        //user_home?ver=版本号&imei=手机标识符&token =用户令牌
        p.put("ver", CommonUtil.getVersionCode(this) + "");
        p.put("imei", SystemUtils.getIMEI(this));
        p.put("token", SharedPreferenceUtil.getToken(this));
        String urlPath = UrlComposeUtil.getUrlPath(Const.URL_USER_INFO, p);
        new LoadUserTask().execute(urlPath);
    }

    class LoadUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            UserManager m = new UserManager();
            try {
                return m.getUserInfo(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "服务器访问失败", Toast.LENGTH_SHORT).show();
            } catch (URLErrorException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "参数有误", Toast.LENGTH_SHORT).show();
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
                User user = (User) baseEntity.getData();
                String portrait = user.getPortrait();
                Glide.with(MainActivity.this).load(portrait)
                        .centerCrop().into(header);
                username.setText(user.getUid());
            } else {
                //失败 清空账户信息，不会自动登入
                SharedPreferenceUtil.saveAccount(MainActivity.this,null,null);
                Toast.makeText(MainActivity.this, "token 值失效", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private BaseEntity parseUserInfo(String s) {
        Gson g = new Gson();
        Type t = new TypeToken<BaseEntity<User>>() {
        }.getType();
        return g.fromJson(s, t);
    }

    /**
     * 给每个fragment调用，回到新闻主界面
     */
    public void backToMainActivity(Toolbar toolbar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//导航箭头
        //专门为退出登入用
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new NewsGroupFragment()).commit();
                selectedNewsItem();
            }
        });
        //点击toolbar的导航触发
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUserInfo();//刷新导航头部数据信息
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new NewsGroupFragment()).commit();
                selectedNewsItem();
            }
        });
    }
    public void selectedNewsItem() {
        Menu menu = mNavigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
    }
    public void setUpNavDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.mipmap.ic_drawer);
            //给图片设置的点击事件
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

    }


    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public void pullParseAssetXmlFile() {
        //解析assets目录的xml文件
        XmlPullParser xmlPullParser = Xml.newPullParser();
        try {
            ArrayList<Person> persons = new ArrayList<>();
            Person p = null;
            InputStream stream = getAssets().open("person.xml");
            xmlPullParser.setInput(stream, "utf-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //初始化动作

                        break;
                    case XmlPullParser.START_TAG:
                        //开始标签对应的事件
                        String name = xmlPullParser.getName();
                        if (name.equals("person")) {
                            p = new Person();
                            String id = xmlPullParser.getAttributeValue(null, "id");
                            p.id = Integer.parseInt(id);
                            Log.d(TAG, "id: " + id);
                        } else if (name.equals("name")) {
                            String text = xmlPullParser.nextText();
                            p.name = text;
                            Log.d(TAG, "name: " + text);

                        } else if (name.equals("age")) {
                            String age = xmlPullParser.nextText();
                            p.age = Integer.parseInt(age);
                            persons.add(p);
                            Log.d(TAG, "age: " + age);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //结束标签对应的事件
                        break;
                }
                eventType = xmlPullParser.next();
            }
            for (Person person : persons) {
                Toast.makeText(MainActivity.this, "" + person.age + person.name + person.id, Toast.LENGTH_SHORT).show();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    //解析带namespace的xml文件
    public void pullParse() {
        //解析assets目录的xml文件
        try {
//            XmlPullParser xmlPullParser = Xml.newPullParser();
//            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            XmlResourceParser xmlPullParser = getResources().getXml(R.xml.aa);
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //初始化动作
                        break;
                    case XmlPullParser.START_TAG:
                        //开始标签对应的事件
                        String name = xmlPullParser.getName();
                        if (name.equals("TextView")) {
                            //String tag = xmlPullParser.getAttributeName(2);
                            //int attributeResourceValue = xmlPullParser.getAttributeResourceValue("android", tag, 0);

                            //Toast.makeText(MainActivity.this, attributeResourceValue + "", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //结束标签对应的事件
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

    }

    //解析json数据
    public void parseNewsGroupJsonString() {
        String url = "http://192.168.2.35:8080/newsClient/news_sort?ver=1&imei=1";
        String data = HttpUtil.getJsonString(url);
        if (data == null) {
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "onCreate: " + data);

        Gson gson = new Gson();
        Type type = new TypeToken<NewsGroup<List<NewsGroup.DataBean<List<NewsGroup.DataBean.SubgrpBean>>>>>() {
        }.getType();

        NewsGroup newsGroup = gson.fromJson(data, type);
        Log.d(TAG, "parseNewsGroup: " + newsGroup.getMessage());
        List<NewsGroup.DataBean> data1 = (List<NewsGroup.DataBean>) newsGroup.getData();
        for (NewsGroup.DataBean dataBean : data1) {
            String group = dataBean.getGroup();
            Log.d(TAG, "parseNewsGroup: " + group);
            List<NewsGroup.DataBean.SubgrpBean> subgrp = (List<NewsGroup.DataBean.SubgrpBean>) dataBean.getSubgrp();
            for (NewsGroup.DataBean.SubgrpBean subgrpBean : subgrp) {
                //Log.d(TAG, "parseNewsGroup: "+subgrpBean.getSubgroup());
            }
        }


       /* //解析数据 json
        try {
            JSONObject j = new JSONObject(data);
            NewsGroup ng = new NewsGroup();

            String message = j.getString("message");
            int status = j.getInt("status");

            ng.setMessage(message);
            ng.setStatus(status);
            Log.d(TAG, "message: " + message + "status:" + status);

            JSONArray array = j.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                int gid = o.getInt("gid");
                String group = o.getString("group");
                NewsGroup.DataBean dataBean = new NewsGroup.DataBean();
                dataBean.setGid(gid);
                dataBean.setGroup(group);
                Log.d(TAG, "gid: " + gid + "group:" + group);
                JSONArray array1 = o.getJSONArray("subgrp");
                for (int k = 0; k < array1.length(); k++) {

                    JSONObject o1 = array1.getJSONObject(k);
                    int subid = o1.getInt("subid");
                    String subgroup = o1.getString("subgroup");
                    Log.d(TAG, "subid: " + subid + "subgroup:" + subgroup);
                    NewsGroup.DataBean.SubgrpBean bean = new NewsGroup.DataBean.SubgrpBean();
                    bean.setSubgroup(subgroup);
                    bean.setSubid(subid);
                    dataBean.getSubgrp().add(bean);
                }
                ng.getData().add(dataBean);
            }

        } catch (JSONException e) {
            Log.d(TAG, "run: json 字符串数据格式有问题");
        }*/
    }


}


