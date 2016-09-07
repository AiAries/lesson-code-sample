package com.example.administrator.highviewdemo.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.highviewdemo.R;
import com.example.administrator.highviewdemo.entity.StrData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
       /* //找到控件listview
        ListView listView = (ListView) findViewById(R.id.main_listView);
        //准备数据
        String[] data = StrData.main_str;
        //创建适配器ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                data
        );
        //给listview绑定adapter
        assert listView != null;
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //获取选中的item的id
        //long[] checkedItemIds = listView.getCheckedItemIds();*/

        //获取listview控件
        ListView listView = getListView();
        //准备数据
        String[] main_str = StrData.main_str;
        int len = main_str.length;
        String[] strFrom = new String[]{"image","text"};
        int[] resId = new int[]{R.mipmap.logo_1,R.mipmap.logo_2,R.mipmap.logo_3,
                R.mipmap.logo_4,R.mipmap.logo_5};

        //准备适配器 SimpleAdapter
        List<Map<String,Object>> data = new ArrayList<>();
        for (int i = 0; i <len; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put(strFrom[0],resId[i]);
            //通过图片的Id生成图片
            map.put(strFrom[1],main_str[i]);
            data.add(map);
        }
        //Cursor mCursor = this.getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
        //准备适配器
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.item_simple_adapter,
                strFrom,
                new int[] {R.id.imageView,R.id.textView}
        );
//        ListAdapter
//        adapter.set
//        String[] main_str = StrData.main_str;
//        MyAdapter adapter = new MyAdapter(main_str,R.layout.item_list_view,this);
        //给listview绑定适配器
        listView.setAdapter(adapter);
    }
}
