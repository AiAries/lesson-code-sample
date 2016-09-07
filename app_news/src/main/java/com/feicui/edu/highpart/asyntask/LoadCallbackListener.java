package com.feicui.edu.highpart.asyntask;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public interface LoadCallbackListener<T> {

    void onSuccess(T t);
    void onFailed(T t);
}
