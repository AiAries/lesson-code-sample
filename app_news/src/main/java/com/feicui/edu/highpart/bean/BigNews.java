package com.feicui.edu.highpart.bean;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class BigNews {
  private String image;
  private String introduct;

    public BigNews(String image, String introduct) {
        this.image = image;
        this.introduct = introduct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }
}
