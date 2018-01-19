package com.feicui.edu.highpart.retrofit;

import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.Register;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Aries on 2016/10/22.
 */

public  interface LoginService
{
    @GET/*("/user_login?")*/
    Call<BaseEntity<Register>> login(/*@QueryMap Map<String, String> options*/);
}
