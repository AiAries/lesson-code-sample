package edu.feicui.com.houserkeeper.entity;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class TelClassList {
    private  String name;
    private  int idx;

    public TelClassList(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
