package com.bewant2be.doit.jcentertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bewant2be.doit.jcentertest.bean.JsonBean;
import com.bewant2be.doit.utilslib.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GsonActivity extends AppCompatActivity {
    public final static String TAG = GsonActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        testGson1();
        testGson2();
        testGson3();
    }

    private void testGson1(){
        Log.i(TAG, " -  -  - testGson1 -  -  -");

        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);

        for (String tmp:strings) {
            Log.i(TAG, tmp);
            ToastUtil.toastComptible(getApplicationContext(),tmp);
        }
    }

    private void testGson2(){
        Log.i(TAG, " -  -  - testGson2 -  -  -");

        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());

        for (String tmp:stringList) {
            Log.i(TAG, tmp);
            ToastUtil.toastComptible(getApplicationContext(),tmp);
        }
    }

    private void testGson3(){
        Log.i(TAG, " -  -  - testGson3 -  -  -");

        try{
            InputStream inputStream = getApplicationContext().getAssets().open("Bean.json");
            JsonBean jsonBean = new GsonBuilder().create().fromJson(new InputStreamReader(inputStream), JsonBean.class);

            int size = jsonBean.getMiddleSchools().size();
            for (int i=0;i<size;i++){
                JsonBean.MiddleSchoolsBean middleSchool = jsonBean.getMiddleSchools().get(i);
                Log.i(TAG, "getSchoolName: " + middleSchool.getSchoolName() );

                for (JsonBean.MiddleSchoolsBean.DepartmentsBean tmp: middleSchool.getDepartments() ) {
                    Log.i(TAG, " -  - DepartmentName: " + tmp.getDepartmentName() );
                }
            }
        }catch (Exception e){
            Log.i(TAG, " -  -  - testGson3 -  -  -" + e.toString() );
        }
    }
}
