package com.example.http.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.http.adapter.NewAdapter;
import com.example.http.adapter.WeatherAdapter;
import com.example.http.util.HttpsUtil;
import com.example.http.R;
import com.example.http.news.Data;
import com.example.http.news.NewsJson;
import com.example.http.news.Result;
import com.example.http.weather.FutureBean;
import com.example.http.weather.RealtimeBean;
import com.example.http.weather.ResultBean;
import com.example.http.weather.WeatherJson;

import java.io.IOException;
import java.util.List;

public class ThreeActivity extends AppCompatActivity {

    private static final String IMGURL= "http://p5.qhimg.com/bdm/960_593_0/t016f2d85eae219d8ce.jpg";
    private static final String WHEATHERIP="http://apis.juhe.cn/simpleWeather/query?city=%E8%8B%8F%E5%B7%9E&key=4ae97f05f3c019e3af3d1b4289842b1e";
    private ListView listNews;
    private ImageView imgFirst;
    private LinearLayout linearLayout;
    private TextView tvCity,tvAqi,tvHumidity,tvTemperature,tvInfo,tvPower,tvDirect,tvData;
    private RecyclerView reWeatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        initView();
        Glide.with(this).load(IMGURL).into(imgFirst);
    }

    private void initView() {
        tvData = findViewById(R.id.tvWeatherData);
        listNews = findViewById(R.id.list_news);
        imgFirst = findViewById(R.id.img_first);
        linearLayout = findViewById(R.id.id_weather);
        tvCity = findViewById(R.id.tvWeatherCityName);
        tvAqi = findViewById(R.id.tvWeatherApi);
        tvDirect = findViewById(R.id.tvWeatherDirect);
        tvHumidity = findViewById(R.id.tvWeatherHumidity);
        tvInfo = findViewById(R.id.tvWeatherInfo);
        tvPower = findViewById(R.id.tvWeatherPower);
        tvTemperature = findViewById(R.id.tvWeatherTemperature);
        reWeatherList = findViewById(R.id.ReWeatherList);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_tian:
             listNews.setVisibility(View.GONE);
             imgFirst.setVisibility(View.GONE);
             linearLayout.setVisibility(View.VISIBLE);
             showWeather();
                break;
            case R.id.btn_youjia:
                break;
            case R.id.btn_toutiao:
                listNews.setVisibility(View.VISIBLE);
                imgFirst.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                    init();

                break;
        }
    }

    private void showWeather() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                OkHttpUtil.httpCoon();
                try {
                    String json = HttpsUtil.get(WHEATHERIP);
                    WeatherJson weatherJson = JSON.parseObject(json, WeatherJson.class);
                    final ResultBean resultBean = weatherJson.getResult();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<FutureBean> futureBeans = resultBean.getFuture();
                            RealtimeBean realtimeBean = resultBean.getRealtime();
                            tvData.setText(futureBeans.get(1).getDate());
                            tvCity.setText(resultBean.getCity());
                            tvAqi.setText(realtimeBean.getAqi());
                            tvDirect.setText(realtimeBean.getDirect());
                            tvHumidity.setText("湿度："+realtimeBean.getHumidity());
                            tvInfo.setText(realtimeBean.getInfo());
                            tvTemperature.setText(realtimeBean.getTemperature());
                            tvPower.setText("风力："+realtimeBean.getPower());

                            showWeatherResultBean(futureBeans);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {

//                OkHttpUtil.httpCoon();
                try {
                    String json2 = HttpsUtil.get("https://v.juhe.cn/toutiao/index?type=&key=aeed60af1d88866d68868bb5c973a77b");
                    NewsJson newsJson = JSON.parseObject(json2, NewsJson.class);
                    final Result result = newsJson.getResult();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showAdapter(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void showWeatherResultBean(List<FutureBean> resultBean) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        reWeatherList.setLayoutManager(linearLayoutManager);

        WeatherAdapter weatherAdapter = new WeatherAdapter(resultBean,ThreeActivity.this);
        reWeatherList.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();


    }
    private void showAdapter(final Result result) {
        NewAdapter newAdapter = new NewAdapter(result.getData(), ThreeActivity.this);
        listNews.setAdapter(newAdapter);
        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Data data = result.getData().get(i);
                Toast.makeText(ThreeActivity.this,">>>>>"+data.getAuthor_name(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
