package com.feicui.edu.highpart.biz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.feicui.edu.highpart.MyApplication;
import com.feicui.edu.highpart.bean.Comment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class LocalCommentDBManager {

    private static final String TAG = "LocalNewsDBManager";

    /**
     * 插入新闻
     *
     * @param activity
     * @param comment
     */
    public static long insert(Activity activity, Comment comment) {
        MyApplication application = (MyApplication) activity.getApplication();
        ContentValues values = new ContentValues();
        values.put(NewsContract.LocalCommentEntry.COLUMN_NAME_ENTRY_ID, comment.getCid());
        values.put(NewsContract.LocalCommentEntry.COLUMN_NAME_CONTENT, comment.getContent());
        values.put(NewsContract.LocalCommentEntry.COLUMN_NAME_PORTRAIT, comment.getPortrait());
        values.put(NewsContract.LocalCommentEntry.COLUMN_NAME_STAMP, comment.getStamp());
        values.put(NewsContract.LocalCommentEntry.COLUMN_NAME_UID, comment.getUid());
        long count = application.commentSQLiteOP.getWritableDatabase().insert(NewsContract.LocalCommentEntry.TABLE_NAME, null, values);
        return count;
    }

//    public static boolean isExistNews(LocalNewsSQLiteOP sqLiteOP, int nid) {
//        String selection = NewsContract.LocalCommentEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
//
//        String[] selectionArg = new String[]{nid + ""};
//        Cursor cursor = sqLiteOP.getWritableDatabase().query(
//                NewsContract.LocalCommentEntry.TABLE_NAME,
//                new String[]{
//                        NewsContract.LocalCommentEntry.COLUMN_NAME_ENTRY_ID
//                },//返回那些列
//                selection,
//                selectionArg,
//                null,
//                null,
//                null
//        );
//        return cursor.getCount() > 0;
//    }

    /**
     * 查询所有的已收藏的新闻数据
     * @param sqLiteOP
     * @return
     */
    public static ArrayList<Comment> query(LocalCommentSQLiteOP sqLiteOP) {
        ArrayList<Comment> data = new ArrayList<>();
        Cursor cursor = sqLiteOP.getWritableDatabase().query(
                NewsContract.LocalCommentEntry.TABLE_NAME,
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
            Comment n = new Comment();
            String icon = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalCommentEntry.COLUMN_NAME_PORTRAIT));
            String content = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalCommentEntry.COLUMN_NAME_CONTENT));
            String stamp = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalCommentEntry.COLUMN_NAME_STAMP));
            String uid = cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalCommentEntry.COLUMN_NAME_UID));
            n.setPortrait(icon);
            n.setContent(content);
            n.setStamp(stamp);
            n.setUid(uid);
            data.add(n);
        }
        return data;
    }

}
