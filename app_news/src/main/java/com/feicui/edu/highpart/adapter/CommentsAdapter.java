package com.feicui.edu.highpart.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.bean.Comment;
import com.feicui.edu.highpart.common.LogUtil;

public class CommentsAdapter extends MyBaseAdapter<Comment> {

	public CommentsAdapter(Context context) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		HoldView holdView=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item_list_comment, null);
			holdView=new HoldView(convertView);
			convertView.setTag(holdView);
		}else{
			holdView=(HoldView) convertView.getTag();
		}
		Comment comment=myList.get(position);

		LogUtil.d(LogUtil.TAG, "position--->"+position +"--- cid="+comment.getCid());
	  	holdView.tv_comment.setText(comment.getContent());
		holdView.tv_time.setText(comment.getStamp());
		holdView.tv_user.setText(comment.getUid());
		Glide.with(context).load(comment.getPortrait())
				.centerCrop()//对bitmap像素缩放
				.placeholder(R.drawable.a3)//默认图片
				.crossFade()//动画效果
				.into(holdView.iv_list_image);//把下载的图片放到imageview中

		return convertView;
	}
	
	public class HoldView {
		public ImageView iv_list_image;
		public TextView tv_user;
		public TextView tv_time;
		public TextView tv_comment;

		public HoldView(View view) {
			iv_list_image = (ImageView) view.findViewById(R.id.iv_header);
			tv_user = (TextView) view.findViewById(R.id.tv_username);
			tv_time = (TextView) view.findViewById(R.id.tv_time);
			tv_comment = (TextView) view.findViewById(R.id.tv_content);
		}
	}
	
}
