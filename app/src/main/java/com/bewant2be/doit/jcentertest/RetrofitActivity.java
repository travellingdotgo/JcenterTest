package com.bewant2be.doit.jcentertest;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bewant2be.doit.jcentertest.retrofit.FaceRequest;
import com.bewant2be.doit.jcentertest.retrofit.FaceResponse;
import com.bewant2be.doit.jcentertest.retrofit.SmsResponse;
import com.bewant2be.doit.jcentertest.retrofit.SmsService;
import com.bewant2be.doit.utilslib.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class RetrofitActivity extends AppCompatActivity {

    private static String TAG = "RetrofitActivity";
    private static String phoneNo = "1805876310";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        if(phoneNo.length()!=11){
            ToastUtil.toastComptible(getApplicationContext(), "phoneNo length invalid");
            return;
        }

        reqSMS();
        SystemClock.sleep(1000);
        register();
    }


    // .    .    .    .    .    .    .  1 SMS .    .    .    .    .    .    .
    private void reqSMS(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://portal.zjlianhua.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SmsService service = retrofit.create(SmsService.class);

        boolean syncPost = false; // for test

        try{
            if(syncPost){
                Call<SmsResponse> call = service.getBook(phoneNo);
                SmsResponse response = call.execute().body();

                Log.i(TAG, "response: " + response.toString() );
            }else {
                Call<SmsResponse> call = service.getBook(phoneNo);
                call.enqueue(new Callback<SmsResponse>() {
                    @Override
                    public void onResponse(Call<SmsResponse> call, Response<SmsResponse> response) {
                        if (response.isSuccessful()) {
                            Log.i(TAG, "getBook  is Successful: "
                                    + "\n errcode=" + response.body().getErrcode()
                                    + "\n errmsg=" + response.body().getErrmsg()   );
                        } else {
                            Log.i(TAG, "getBook not Successful:  " + response.body() );
                        }
                    }

                    @Override
                    public void onFailure(Call<SmsResponse> call, Throwable t) {
                        Log.i(TAG, "getBook onFailure:  " + t.toString() );
                    }
                });

            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }

    }


    private void register(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://portal.zjlianhua.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SmsService service = retrofit.create(SmsService.class);

        String filename = "/sdcard/portrait.jpg";

        //构建要上传的文件
        File file = new File(filename);
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

        String descriptionString = "This is a description";
        RequestBody description = RequestBody.create( MediaType.parse("multipart/form-data"), descriptionString);

        Map<String,RequestBody> map = new HashMap<String,RequestBody>();
        map.put("name", parseRequestBody("name1") );
        map.put("sex", parseRequestBody("F") );
        map.put("mobile", parseRequestBody("139") );
        map.put("idno", parseRequestBody("330") );
        map.put("address", parseRequestBody("浙江省") );
        Call<FaceResponse> call = service.upload(map,description, body);
        call.enqueue(new Callback<FaceResponse>() {
            @Override
            public void onResponse(Call<FaceResponse> call, Response<FaceResponse> response) {

                if(response.isSuccessful()){
                    System.out.println("success");

                    if(response.code()==200){
                        if(response.body()!=null){
                            Log.e(TAG, "body content: "
                                    + "\n errcode=" + response.body().getErrcode()
                                    + "\n key=" + response.body().getKey()
                                    + "\n qrurl=" + response.body().getQrurl() );
                        }else{
                            Log.e(TAG, "body null:" + response.body() );
                        }
                    }else{
                        Log.e(TAG, "response.code(): " + response.code());
                    }


                }else{
                    Log.e(TAG, "response failed");
                }

            }

            @Override
            public void onFailure(Call<FaceResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "error:" + t.getMessage());
            }
        });
    }

    //

    public static RequestBody parseRequestBody(String value) {
        return RequestBody.create(MediaType. parse("text/plain"), value);
    }

    public static RequestBody parseImageRequestBody(File file) {
        return RequestBody.create(MediaType. parse("image/*"), file);
    }

    public static String parseImageMapKey(String key, String fileName) {
        return key + "\"; filename=\"" + fileName;
    }

}
