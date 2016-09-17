package com.feicui.edu.highpart.bean;

public class LoginLog {

    /** 登陆地址 */
    private String address;
    /**
     * 登陆时的设备 true:手机登陆 false:网页登陆
     */
    private int device;
    /** 登陆时间 */
    private String time;

    public LoginLog(String address, int device, String time) {
        this.address = address;
        this.device = device;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public int getDevice() {
        return device;
    }

    public String getTime() {
        return time;
    }

}
