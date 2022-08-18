package com.xiangke.myandroiddemo.http;

import com.xiangke.http.BaseResponse;
import com.xiangke.myandroiddemo.bean.LoginBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @Date:2022/8/10 15:53
 * @Author:dengjin
 * @Name:
 */
public interface LoginApi {
    @POST("sacw/center/app/loginByDevice")
    Observable<BaseResponse<LoginBean>> login(@Body RequestBody body);
}
