package com.example.http.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.http.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_httpUrl:
                intent = new Intent(MainActivity.this, HttpUrlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_Okhttp:
                Intent intent2 = new Intent(MainActivity.this, HttpUrlActivity.class);
                startActivity(intent2);
                break;
            case R.id.three:
                intent = new Intent(MainActivity.this, ThreeActivity.class);
                startActivity(intent);
                break;
        }
    }
}


