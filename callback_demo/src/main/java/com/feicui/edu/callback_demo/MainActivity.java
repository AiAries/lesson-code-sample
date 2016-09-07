package com.feicui.edu.callback_demo;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity implements LoadMoreListener {

    public final int LOAD_SUCCESSED = 1;
    public final int LOAD_START = 2;
    public final int LOAD_FAILED = 0;
    private ListView listView;
    private View view;

    boolean isLoad;
    //1.在主线程，创建handler的匿名子类对象
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_START:
                    //当触发了load，就直接滚到底部
                    listView.smoothScrollToPosition(15);
//                    listView.setSmoothScrollbarEnabled();
                    view.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "load start", Toast.LENGTH_SHORT).show();
                    break;
                case LOAD_FAILED:
//                    listView.removeFooterView(view);
                    view.setVisibility(View.GONE);
                    isLoad = false;
                    Toast.makeText(MainActivity.this, "load failed", Toast.LENGTH_SHORT).show();
                    break;
                case LOAD_SUCCESSED:
                    //每次移除都会刷新Listview，触发load方法
//                    listView.removeFooterView(view);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    view.setVisibility(View.GONE);
                    isLoad = false;
                    Toast.makeText(MainActivity.this, "load success", Toast.LENGTH_SHORT).show();
                    break;
            }
            //view.setVisibility(View.GONE);
        }
    };
    private MyAdapter adapter;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = getListView();

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(i + "-------data------");
        }

        adapter = new MyAdapter(this, data);
        //设置监听器
        adapter.setListener(this);
        //给listview添加底部视图
        view = inflater.inflate(R.layout.footer_view, null);
        listView.addFooterView(view, "正在加载", false);
        listView.setAdapter(adapter);
    }

    @Override
    public void loadMore() {
        //模拟下载数据
        load();
        //Toast.makeText(MainActivity.this, "load more", Toast.LENGTH_SHORT).show();
    }

    private void load() {
        if (isLoad) {
            return;
        }
        //改变下载的标记
        isLoad = true;
        new Thread() {
            @Override
            public void run() {
                super.run();
                //开始下载
                try {

                    Message msg = handler.obtainMessage(LOAD_START);
                    handler.sendMessage(msg);
                    Thread.sleep(5000);
                    //下载完成,发送一条消息，告诉主线程刷新UI
                    ArrayList<String> data = new ArrayList<>();
                    for (int i = 0; i < 40; i++) {
                        data.add(i + "----new---data------");
                    }
                    adapter.setData(data);
                    msg = handler.obtainMessage(LOAD_SUCCESSED);
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    //下载失败
                    Message msg = handler.obtainMessage(LOAD_FAILED);
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
