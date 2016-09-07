package edu.feicui.com.houserkeeper.entity;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class TelNumInfo {
    private String name;
    private long    telNum;
    //alt + inset
    public TelNumInfo(String name, long telNum) {
        this.name = name;
        this.telNum = telNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTelNum() {
        return telNum;
    }

    public void setTelNum(long telNum) {
        this.telNum = telNum;
    }
}
