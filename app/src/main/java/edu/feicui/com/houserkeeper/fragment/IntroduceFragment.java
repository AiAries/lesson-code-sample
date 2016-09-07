package edu.feicui.com.houserkeeper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.feicui.com.houserkeeper.ui.IntroduceActivity;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class IntroduceFragment  extends Fragment{
    //fragment是一个轻量级的activity
    //它的生命周期跟使用它的activity的界面相关联


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageView imageView = new ImageView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(params);
        //获取framen中的参数
        Bundle bundle = getArguments();
        int i = bundle.getInt("i");
        //IntroduceActivity.resIds[i] 这种1方案 可行 ，不推荐
        //方案2 推荐使用
        IntroduceActivity activity = (IntroduceActivity) getActivity();
        imageView.setBackgroundResource(activity.resIds[i]);
        return imageView;
    }
}
