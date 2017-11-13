package com.bewant2be.doit.jcentertest.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SmsService {

    @GET("oto/cgi/eop/sms/{phone}")
    Call<SmsResponse> getBook(@Path("phone") String phone);

    /*
    @Multipart
    @POST("oto/cgi/eop/facecheck")
    Call<FaceResponse> upload(@Body FaceRequest faceRequest, @Part("description") RequestBody description, @Part MultipartBody.Part file);
    */

    @Multipart
    @POST("oto/cgi/eop/facecheck")
    Call<FaceResponse> upload(@PartMap Map<String,RequestBody> data, @Part("pic") RequestBody description, @Part MultipartBody.Part file);
    /*
    https://segmentfault.com/q/1010000008078870/a-1020000008081413
    */
}
