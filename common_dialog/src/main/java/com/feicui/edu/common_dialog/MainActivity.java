package com.feicui.edu.common_dialog;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_alert).setOnClickListener(this);
        findViewById(R.id.btn_date).setOnClickListener(this);
        findViewById(R.id.btn_popu).setOnClickListener(this);
        findViewById(R.id.btn_time).setOnClickListener(this);
        findViewById(R.id.btn_custom_alert).setOnClickListener(this);
        findViewById(R.id.btn_progress).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_alert:

                final String[] data = {"北京", "上海", "广州"};
                //选择对话框AlertDialog,通过构造器Builder来构建和设置属性
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
//                builder.set
                builder.setIcon(R.mipmap.ic_launcher);
                //设置单选对话框
                builder.setSingleChoiceItems(
                        data,
                        0,//默认那个条目选中，给-1都不选中
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, data[which], Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                );
//                //builder.setMessage("是否退出当前编辑状态");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "你选择了退出编辑", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "你选择了继续编辑", Toast.LENGTH_SHORT).show();
//                    }
//                });
                //生成dialog
                builder.create();
                //展示对话框
                builder.show();
                break;
            case R.id.btn_custom_alert:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                //把xml文件渲染成一个view
                View view  = LayoutInflater.from(this).inflate(R.layout.dialog_custom_alert, null);
                builder1.setView(view);
                builder1.create().show();
                break;
            case R.id.btn_date:
                //日期选择对话框
                DatePickerDialog dialog1 = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                dialog1.show();
                break;
            case R.id.btn_popu:
                //找到对象所属的那个类PopupWindow
                final PopupWindow window = new PopupWindow(this);
                window.setWidth(300);
                //window.setHeight(160);
                View view1  = LayoutInflater.from(this).inflate(R.layout.dialog_custom_alert, null);
                window.setContentView(view1);
                view1.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                    }
                });
//                //要取消泡泡窗口,必须设置背景图片
//                window.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_custom_dialog_background));
                //指定泡泡窗口显示的位置
                window.showAsDropDown(
                        v,//参照对象
                        -60,//x轴偏移值
                        -60//y轴偏移值
                );
                //window.di
                break;
            case R.id.btn_time:
                //时间选择对话框
                TimePickerDialog dialog = new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //当时间设置好的时候，回回调此方法
                                Toast.makeText(MainActivity.this, hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
                            }
                        },
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false
                );
                dialog.show();
                break;
            case R.id.btn_progress:
                ProgressDialog  dialog2 = new ProgressDialog(this);
                dialog2.setMessage("登入中...");
                dialog2.setTitle("提示");
//                dialog2.set
                dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog2.show();
                break;
        }
    }
}
