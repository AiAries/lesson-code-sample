package edu.feicui.com.houserkeeper.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import edu.feicui.com.houserkeeper.R;
import edu.feicui.com.houserkeeper.adapter.TelClassAdapter;
import edu.feicui.com.houserkeeper.db.DBRead;
import edu.feicui.com.houserkeeper.entity.TelClassList;
import edu.feicui.com.houserkeeper.util.MyAssetManager;

public class ShowTelTypeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private File file;
    private ArrayList<TelClassList> data;
    private TelClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tel_type);
        initToolBar();
        initView();
    }

    @Override
    public void initView() {

        ListView listView = (ListView) findViewById(R.id.show_tel_class_list_view);
        //data
//        file = MyAssetManager.copyAssetsFileToSDFile(this,"db/commonnum.db","commonnum.db");
        data = new ArrayList<>();
        //create adapter
        adapter = new TelClassAdapter(data, this);
        //设置适配器
        listView.setAdapter(adapter);
        //设置listview条目点击事件
        listView.setOnItemClickListener(this);

        new QueryTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳到指定界面
        TelClassList tel = (TelClassList) parent.getItemAtPosition(position);
        //bundle是一个好比一个数据包
        Bundle bundle = new Bundle();
        bundle.putSerializable("file", file);
        bundle.putInt("idx", tel.getIdx());
        myStartActivity(ShowTelNumActivity.class, bundle);
    }

    class QueryTask extends AsyncTask<Void, Void, ArrayList<TelClassList>> {
        @Override
        protected ArrayList<TelClassList> doInBackground(Void... params) {
            file = MyAssetManager.copyAssetsFileToSDFile(ShowTelTypeActivity.this, "db/commonnum.db", "commonnum.db");
            return DBRead.readTelClassList(file);
        }

        @Override
        protected void onPostExecute(ArrayList<TelClassList> telClassLists) {
            //super.onPostExecute(telClassLists);
            if (telClassLists != null)
                //修改适配器中的数据
                adapter.setData(telClassLists);
            //刷新ListView中的条目
            adapter.notifyDataSetChanged();
        }
    }

}
