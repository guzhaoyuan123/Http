package com.example.http.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.http.R;
import com.example.http.weather.FutureBean;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<FutureBean> futureBeans;
    private Context context;

    public WeatherAdapter(List<FutureBean> futureBeans, Context context) {
        this.futureBeans = futureBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_item,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvData = view.findViewById(R.id.wh_data);
        viewHolder.tvWen = view.findViewById(R.id.wh_wen);
        viewHolder.tvTianqi = view.findViewById(R.id.wh_qingkuang);
        viewHolder.tvFeng = view.findViewById(R.id.wh_fengxiang);
        viewHolder.imImg = view.findViewById(R.id.wh_img);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvData.setText(futureBeans.get(i).getDate());
        viewHolder.tvWen.setText(futureBeans.get(i).getTemperature());
        viewHolder.tvTianqi.setText(futureBeans.get(i).getWeather());
        viewHolder.tvFeng.setText(futureBeans.get(i).getDirect());
        viewHolder.imImg.setImageResource(R.mipmap.qing);
    }

    @Override
    public int getItemCount() {
        return futureBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View v)
        {
            super(v);
        }
        TextView tvData,tvWen,tvTianqi,tvFeng;
        ImageView imImg;
    }

}
