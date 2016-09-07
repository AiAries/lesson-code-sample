package edu.feicui.com.houserkeeper.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class RunningAppInfo {

    String packageName;//进程名，包名
    String label;//标签名
    boolean isSelect;//是否勾选中checkbox

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    String memorySize;//进程占内存的大小
    Drawable  appIcon;//应用的图标
    boolean isSysApp;//是否是系统应用，true 表示是系统应用
    int importance;//进程类别

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isSysApp() {
        return isSysApp;
    }

    public void setSysApp(boolean sysApp) {
        isSysApp = sysApp;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public RunningAppInfo(String label, String packageName, String memorySize, Drawable appIcon, boolean isSysApp, int importance) {
        this.packageName = packageName;
        this.memorySize = memorySize;
        this.label = label;
        this.appIcon = appIcon;
        this.isSysApp = isSysApp;
        this.importance = importance;
    }
}
