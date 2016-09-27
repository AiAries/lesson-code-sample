package com.feicui.edu.highpart.common;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class Const {
    private static final String URL = "http://118.244.212.82:9092/newsClient/";
    public static final String PHONE = "0";//表示从手机端登入
    public static final int WEB = 1;//表示从网页登入
    //新闻的请求路径
    public static final String URL_NEW_LIST = URL + "news_list?";
    //登入的请求路径
    public static final String URL_LOGIN = URL + "user_login?";
    //register
    public static final String URL_REGISTER = URL + "user_register?";
    //forget pwd /user_forgetpass?ver=" + args[0] + "&email=" + args[1]
    public static final String URL_FORGET_PWD = URL + "user_forgetpass?";
    //用户数据
    public static final String URL_USER_INFO = URL + "user_home?";
    //用户图片
    public static final String URL_USER_IMAGE = URL + "user_image?";
    //用户评论
    public static final String URL_USER_COMMENT = URL + "cmt_commit?";
    //用户评论数量
    public static final String URL_USER_COMMENT_COUNT = URL + "cmt_num?";
    //用户评论信息
    public static final String URL_USER_COMMENT_INFO = URL + "cmt_list?";
    //大图新闻
    public static final String URL_BIG_PIC = URL + "news_image?";

}
