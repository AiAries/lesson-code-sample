package edu.feicui.com.houserkeeper.adapter;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleAdapterDemo extends ListActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        ArrayList<Map<String,String >> list = new ArrayList<>();
        for (int i = 0; i < 50; i++)
        {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("id","item"+i);
            list.add(hashMap);
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
////                android.R.layout.simple_list_item_checked,list);
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                list,
                android.R.layout.simple_list_item_multiple_choice,
                new String[]{"id"},
                new int[]{android.R.id.text1}
        );

        if (listView != null)
        {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setAdapter(adapter);
            listView.setItemChecked(5,true);
            listView.getCheckedItemIds();
        }
//        CheckedTextView checkedTextView;
    }

}
