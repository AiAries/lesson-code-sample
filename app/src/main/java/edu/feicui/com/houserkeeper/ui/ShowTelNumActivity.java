package edu.feicui.com.houserkeeper.ui;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import edu.feicui.com.houserkeeper.adapter.TelNumAdapter;
import edu.feicui.com.houserkeeper.db.DBRead;
import edu.feicui.com.houserkeeper.entity.TelNumInfo;

public class ShowTelNumActivity extends ListActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //获取上个界面传递过来的值
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int idx = bundle.getInt("idx", 1);
        File file = (File) bundle.getSerializable("file");
        //获取数据库数据
        ArrayList<TelNumInfo> telNumInfos = DBRead.readTelNum(file, idx);
        //创建适配器
        TelNumAdapter adapter = new TelNumAdapter(telNumInfos, this);

        ListView listView = getListView();
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        TelNumInfo tel = (TelNumInfo) parent.getItemAtPosition(position);
        intent.setData(Uri.parse("tel:" + tel.getTelNum()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ShowTelNumActivity.this, "please open call phone  permission ", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }
}
