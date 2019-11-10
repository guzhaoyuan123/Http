package com.example.http.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.http.R;
import com.example.http.news.Data;

import java.util.List;

public class NewAdapter extends BaseAdapter {

    private List<Data> datas;
    private Context context;

    public NewAdapter(List<Data> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item2,viewGroup,false);
        Data data = (Data) getItem(i);

        TextView textView = view.findViewById(R.id.mtvtitle);
        TextView textView2 = view.findViewById(R.id.mtvtitle2);
        ImageView imageView = view.findViewById(R.id.mimv);


        textView.setText(data.getTitle());
        textView2.setText(data.getAuthor_name());
        Glide.with(context).load(data.getThumbnail_pic_s()).into(imageView);

        return view;
    }
}
