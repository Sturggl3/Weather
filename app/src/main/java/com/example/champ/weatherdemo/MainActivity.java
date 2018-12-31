package com.example.champ.weatherdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MainActivity extends Activity {
    RequestQueue queue = null;
    EditText et_city;
    TextView tv_city,tv_date,tv_temp,tv_cond,tv_qlty,tv_dir;
    Editable city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        et_city = (EditText) findViewById(R.id.et_city);
        tv_city = (TextView) findViewById(R.id.id_tv_city);
        tv_date = (TextView) findViewById(R.id.id_tv_date);
        tv_temp = (TextView) findViewById(R.id.id_tv_temp);
        tv_cond = (TextView) findViewById(R.id.id_tv_cond);
        tv_dir = (TextView) findViewById(R.id.id_tv_dir);
        tv_qlty = (TextView) findViewById(R.id.id_tv_qlty);
    }
    //将这里的yourkey替换成你申请到的key
    public void weatherClick(View view){
        city = et_city.getText();
        String url = "https://free-api.heweather.com/v5/weather?city="+city+"&key=yourkey";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject);
                Gson gson =new Gson();
                WeatherBean weatherBean = gson.fromJson(jsonObject.toString(),WeatherBean.class);
                String cityName = weatherBean.getHeWeather5().get(0).getBasic().getCity();
                String date = weatherBean.getHeWeather5().get(0).getDaily_forecast().get(0).getDate();
                String temp = weatherBean.getHeWeather5().get(0).getNow().getTmp();
                String cond = weatherBean.getHeWeather5().get(0).getNow().getCond().getTxt();
                String dir = weatherBean.getHeWeather5().get(0).getNow().getWind().getDir();
                String qlty = weatherBean.getHeWeather5().get(0).getAqi().getCity().getQlty();

                tv_city.setText("城市："+cityName);
                tv_date.setText("日期："+date);
                tv_temp.setText("温度："+temp);
                tv_cond.setText("天气情况："+cond);
                tv_dir.setText("风向："+dir);
                tv_qlty.setText("空气质量："+qlty);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        });
        queue.add(request);
    }

}
