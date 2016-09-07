package edu.feicui.com.houserkeeper.adapter;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.feicui.com.houserkeeper.R;

/**
 * Created by Aries on 2016/7/28.
 */
public class SimpleCursorAdapterDemo extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
               android.R.layout.simple_list_item_1,
                c,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                new int[]{R.id.text},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                );
        listView.setAdapter(adapter);
    }
}
