package edu.feicui.com.houserkeeper.ui;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.feicui.com.houserkeeper.biz.FileManager;

public class FilemgrActivity extends Activity {
	private Thread thread;// 用来读取文件的线程
	private FileManager fileManager;// 文件管理类(逻辑)

	private TextView tv_textsize; // 文件总大小

	private View view_all; // 全部的item(当加载结束后，将有单击事件)
	private View view_txt; // 文档的item(同上)
	private View view_video; // 视频的item(同上)
	private View view_audio; // 音乐的item(同上)
	private View view_image; // 图像的item(同上)
	private View view_apk; // apk的item(同上)
	private View view_zip; // zip的item(同上)
	private TextView tv_all_size; // 全部item的大小(当加载过程中，实时更新此分类的文件大小)
	private TextView tv_txt_size;// 文档item的大小(同上)
	private TextView tv_video_size;// 视频item的大小(同上)
	private TextView tv_audio_size;// 音乐item的大小(同上)
	private TextView tv_image_size;// 图像item的大小(同上)
	private TextView tv_apk_size;// apk item的大小(同上)
	private TextView tv_zip_size;// zip item的大小(同上)
	private ProgressBar pb_all_loading;// 全部item的加载中图像(当加载过程中会显示,加载完后将隐藏)
	private ProgressBar pb_txt_loading;// 文档item的加载中图像(同上)
	private ProgressBar pb_video_loading;// 视频item的加载中图像(同上)
	private ProgressBar pb_audio_loading;// 音乐item的加载中图像(同上)
	private ProgressBar pb_image_loading;// 图像item的加载中图像(同上)
	private ProgressBar pb_apk_loading;// apk item的加载中图像(同上)
	private ProgressBar pb_zip_loading;// zip item的加载中图像(同上)
	private ImageView iv_all_righticon; // 全部item的右侧图像(当加载结束后，将显示出来(加载中显示的是loading))
	private ImageView iv_txt_righticon; // 文档item的右侧图像(同上)
	private ImageView iv_video_righticon; // 视频item的右侧图像(同上)
	private ImageView iv_audio_righticon; // 音乐item的右侧图像(同上)
	private ImageView iv_image_righticon; // 图像item的右侧图像(同上)
	private ImageView iv_apk_righticon; // apk item的右侧图像(同上)
	private ImageView iv_zip_righticon; // zip item的右侧图像(同上)

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filemgr);
		// 初始化ActionBar @see super class ActionBarActivity
		String title = getResources().getString(R.string.filemgr);
		initActionBar(title, R.drawable.btn_homeasup_default, -1, clickListener);
		initMainUI();
		asynLoadData();
	}

	private void initMainUI() {
		//
		tv_textsize = (TextView) findViewById(R.id.tv_filesize);
		//
		view_all = findViewById(R.id.file_classlist_all);
		view_txt = findViewById(R.id.file_classlist_txt);
		view_video = findViewById(R.id.file_classlist_video);
		view_audio = findViewById(R.id.file_classlist_audio);
		view_image = findViewById(R.id.file_classlist_image);
		view_apk = findViewById(R.id.file_classlist_apk);
		view_zip = findViewById(R.id.file_classlist_zip);
		//textView
		tv_all_size = (TextView) findViewById(R.id.file_all_size);
		tv_txt_size = (TextView) findViewById(R.id.file_txt_size);
		tv_video_size = (TextView) findViewById(R.id.file_video_size);
		tv_audio_size = (TextView) findViewById(R.id.file_audio_size);
		tv_image_size = (TextView) findViewById(R.id.file_image_size);
		tv_apk_size = (TextView) findViewById(R.id.file_apk_size);
		tv_zip_size = (TextView) findViewById(R.id.file_zip_size);
		//图片
		iv_all_righticon = (ImageView) findViewById(R.id.file_all_righticon);
		iv_txt_righticon = (ImageView) findViewById(R.id.file_txt_righticon);
		iv_video_righticon = (ImageView) findViewById(R.id.file_video_righticon);
		iv_audio_righticon = (ImageView) findViewById(R.id.file_audio_righticon);
		iv_image_righticon = (ImageView) findViewById(R.id.file_image_righticon);
		iv_apk_righticon = (ImageView) findViewById(R.id.file_apk_righticon);
		iv_zip_righticon = (ImageView) findViewById(R.id.file_zip_righticon);
		//进度条
		pb_all_loading = (ProgressBar) findViewById(R.id.file_all_loading);
		pb_txt_loading = (ProgressBar) findViewById(R.id.file_txt_loading);
		pb_video_loading = (ProgressBar) findViewById(R.id.file_video_loading);
		pb_audio_loading = (ProgressBar) findViewById(R.id.file_audio_loading);
		pb_image_loading = (ProgressBar) findViewById(R.id.file_image_loading);
		pb_apk_loading = (ProgressBar) findViewById(R.id.file_apk_loading);
		pb_zip_loading = (ProgressBar) findViewById(R.id.file_zip_loading);
	}
	*//**异步加载数据*//*
	private void asynLoadData() {
		fileManager = FileManager.getFileManager();
		fileManager.setSearchFileListener(searchFileListener);
		//启动线程进行文件搜索
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				fileManager.searchSDCardFile();
			}
		});
		thread.start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		fileManager.setStopSearch(true);
		//终端线程释放资源
		thread.interrupt();
		thread = null;
	}
	//实时更新各类文件大小
	@Override
	protected void myHandleMessage(Message msg) {
		// TODO Auto-generated method stub
		//文件实时更新操作
		if (msg.what == 1) {
			tv_textsize.setText(CommonUtil.getFileSize(fileManager.getAnyFileSize()));
			tv_all_size.setText(CommonUtil.getFileSize(fileManager.getAnyFileSize()));
			String typeName = (String) msg.obj;
			if (typeName.equals(FileTypeUtil.TYPE_APK)) {
				tv_apk_size.setText(CommonUtil.getFileSize(fileManager.getApkFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_AUDIO)) {
				tv_audio_size.setText(CommonUtil.getFileSize(fileManager.getAudioFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_IMAGE)) {
				tv_image_size.setText(CommonUtil.getFileSize(fileManager.getImageFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_TXT)) {
				tv_txt_size.setText(CommonUtil.getFileSize(fileManager.getTxtFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_VIDEO)) {
				tv_video_size.setText(CommonUtil.getFileSize(fileManager.getVideoFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_ZIP)) {
				tv_zip_size.setText(CommonUtil.getFileSize(fileManager.getZipFileSize()));
			}
		}
		//文件结束更新操作
		if (msg.what == 2) {
			tv_textsize.setText(CommonUtil.getFileSize(fileManager.getAnyFileSize()));
			tv_all_size.setText(CommonUtil.getFileSize(fileManager.getAnyFileSize()));
			tv_apk_size.setText(CommonUtil.getFileSize(fileManager.getApkFileSize()));
			tv_audio_size.setText(CommonUtil.getFileSize(fileManager.getAudioFileSize()));
			tv_image_size.setText(CommonUtil.getFileSize(fileManager.getImageFileSize()));
			tv_txt_size.setText(CommonUtil.getFileSize(fileManager.getTxtFileSize()));
			tv_video_size.setText(CommonUtil.getFileSize(fileManager.getVideoFileSize()));
			tv_zip_size.setText(CommonUtil.getFileSize(fileManager.getZipFileSize()));
			pb_all_loading.setVisibility(View.GONE);
			pb_txt_loading.setVisibility(View.GONE);
			pb_video_loading.setVisibility(View.GONE);
			pb_audio_loading.setVisibility(View.GONE);
			pb_image_loading.setVisibility(View.GONE);
			pb_apk_loading.setVisibility(View.GONE);
			pb_zip_loading.setVisibility(View.GONE);
			iv_all_righticon.setVisibility(View.VISIBLE);
			iv_txt_righticon.setVisibility(View.VISIBLE);
			iv_video_righticon.setVisibility(View.VISIBLE);
			iv_audio_righticon.setVisibility(View.VISIBLE);
			iv_image_righticon.setVisibility(View.VISIBLE);
			iv_apk_righticon.setVisibility(View.VISIBLE);
			iv_zip_righticon.setVisibility(View.VISIBLE);
			view_all.setOnClickListener(clickListener);
			view_txt.setOnClickListener(clickListener);
			view_video.setOnClickListener(clickListener);
			view_audio.setOnClickListener(clickListener);
			view_image.setOnClickListener(clickListener);
			view_apk.setOnClickListener(clickListener);
			view_zip.setOnClickListener(clickListener);
		}
	};
	//回调接口的初始化
	private FileManager.SearchFileListener searchFileListener = new FileManager.SearchFileListener() {
		@Override
		public void searching(String typeName) {
			Message msg = mainHandler.obtainMessage();
			msg.what = 1;
			msg.obj = typeName;
			mainHandler.sendMessage(msg);
		}

		@Override
		public void end(boolean isExceptionEnd) {
			// TODO Auto-generated method stub
			mainHandler.sendEmptyMessage(2);
		}
	};
	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int viewID = v.getId();
			switch (viewID) {
				case R.id.iv_left:
					finish();
					break;
				case R.id.file_classlist_all:
				case R.id.file_classlist_txt:
				case R.id.file_classlist_video:
				case R.id.file_classlist_audio:
				case R.id.file_classlist_image:
				case R.id.file_classlist_apk:
				case R.id.file_classlist_zip:
					Intent intent = new Intent(FilemgrActivity.this, edu.feicui.app.phone.activity.FilemgrShowActivity.class);
					intent.putExtra("id", viewID);
					startActivityForResult(intent, 1);
					break;
				default:
					break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		if (requestCode == 1) {
			tv_textsize.setText(CommonUtil.getFileSize(fileManager.getAnyFileSize()));
			tv_all_size.setText(CommonUtil.getFileSize(fileManager.getAnyFileSize()));
			tv_apk_size.setText(CommonUtil.getFileSize(fileManager.getApkFileSize()));
			tv_audio_size.setText(CommonUtil.getFileSize(fileManager.getAudioFileSize()));
			tv_image_size.setText(CommonUtil.getFileSize(fileManager.getImageFileSize()));
			tv_txt_size.setText(CommonUtil.getFileSize(fileManager.getTxtFileSize()));
			tv_video_size.setText(CommonUtil.getFileSize(fileManager.getVideoFileSize()));
			tv_zip_size.setText(CommonUtil.getFileSize(fileManager.getZipFileSize()));
		}
	}*/
}
