package com.xiangke.http;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @Date:2021/12/29 16:34
 * @Author:dengjin
 * @Name:
 */
public interface RequestApi {
    @GET
    Observable<BaseResponse<JsonObject>> getRequest(@Url String url);

    @POST
    Observable<BaseResponse<JsonObject>> postRequest(@Url String url, @Body RequestBody body);

    @POST("sacw/center/app/loginByDevice")
   <T> Observable<BaseResponse<T>> login(@Body RequestBody body);
}
