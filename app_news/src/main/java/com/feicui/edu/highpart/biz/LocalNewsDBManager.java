package com.feicui.edu.highpart.biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.feicui.edu.highpart.bean.News;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class LocalNewsDBManager {

    private static final String TAG = "LocalNewsDBManager";

    /**
     * 插入新闻
     *
     * @param sqLiteOP
     * @param news
     */
    public static long insert(LocalNewsSQLiteOP sqLiteOP, News news) {
        ContentValues values = new ContentValues();
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_ENTRY_ID, news.getNid());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_TITLE, news.getTitle());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_SUMMARY, news.getSummary());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_LINK, news.getLink());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_ICON, news.getIcon());
        long count = sqLiteOP.getWritableDatabase().insert(NewsContract.LocalNewsEntry.TABLE_NAME, null, values);
        return count;
    }

    public static boolean isExistNews(LocalNewsSQLiteOP sqLiteOP, int nid) {
        String selection = NewsContract.LocalNewsEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";

        String[] selectionArg = new String[]{nid + ""};
        Cursor cursor = sqLiteOP.getWritableDatabase().query(
                NewsContract.LocalNewsEntry.TABLE_NAME,
                new String[]{
                        NewsContract.LocalNewsEntry.COLUMN_NAME_ENTRY_ID
                },//返回那些列
                selection,
                selectionArg,
                null,
                null,
                null
        );
        return cursor.getCount() > 0;
    }

    /**
     * 查询所有的已收藏的新闻数据
     * @param sqLiteOP
     * @return
     */
    public static ArrayList<News> query(LocalNewsSQLiteOP sqLiteOP) {
        ArrayList<News> data = new ArrayList<>();
        Cursor cursor = sqLiteOP.getWritableDatabase().query(
                NewsContract.LocalNewsEntry.TABLE_NAME,
                null,//返回所有的列
                null,
                null,
                null,
                null,
                null
        );
        int count = cursor.getCount();
        Log.d(TAG, "count: " + count);
        while (cursor.moveToNext()) {
            News n = new News();
            String icon = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_ICON));
            String link = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_LINK));
            String title = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_TITLE));
            String summary = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_SUMMARY));
            n.setIcon(icon);
            n.setSummary(summary);
            n.setTitle(title);
            n.setLink(link);
            data.add(n);
        }
        return data;
    }

}
