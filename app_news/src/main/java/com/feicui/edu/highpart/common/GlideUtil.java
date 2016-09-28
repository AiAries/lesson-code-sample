package com.feicui.edu.highpart.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class GlideUtil {

    public static void setImg(Context context, String url, ImageView imageView/*, int defaultResImg*/)
    {
        GlideBuilder builder = new GlideBuilder(context);
        //sd卡缓存
        builder.setDiskCache(new DiskLruCacheFactory(context.getFilesDir().getPath(), 100 * 1024 * 1024));
        //ram缓冲
        builder.setMemoryCache(new LruResourceCache(8 * 1024 * 1024));
       
        Glide.with(context)
                .load(url)
                .centerCrop()/*.animate()*/
                .into(imageView);
    }
}
