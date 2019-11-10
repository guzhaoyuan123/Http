package com.example.http.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.http.util.HttpsUtil;
import com.example.http.R;
import com.example.http.news.NewsJson;
import com.example.http.news.Result;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUrlActivity extends AppCompatActivity {

    private static final String DOWNLOAD_URL = "https://github.com/zhayh/AndroidExample/blob/master/README.md";
    private static final String path = "/data/data/com.example.http/files/Mob/";
    private static final String UPLOAD_FILE_URL = "http://v.juhe.cn/joke/randJoke.php?key=83d1b5f8deb0348e6a072364f9bc773c";
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    private static final String PIC_URL1 = "http://p6.qhimg.com/bdm/960_593_0/t01a676f085326404d3.jpg";
    private ImageView imageView;
    private TextView tvHttpurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url);
        initView();
    }

    private void initView() {
        imageView = findViewById(R.id.iv_img);
        tvHttpurl = findViewById(R.id.tx_httpurl);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                tvHttpurl.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                new workThread().start();
                break;
            case R.id.btn_post:
                tvHttpurl.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(HttpUrlActivity.this).load(PIC_URL1).into(imageView);
                break;
            case R.id.btn_up:
                imageView.setVisibility(View.GONE);
                tvHttpurl.setVisibility(View.VISIBLE);
                final String fileName = path + File.separator + "readme.md";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadFile(UPLOAD_FILE_URL, fileName);
                    }
                }).start();
                break;
            case R.id.btn_down:
                String path = getFilesDir().getAbsolutePath();
                downFile(DOWNLOAD_URL, path);
                break;
        }
    }

    private void downFile(final String url, final String path) {
        // 1. 创建Requet对象    
        Request request = new Request.Builder().url(url).build();
        // 2. 创建OkHttpClient对象，发送请求，并处理回调    
        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                if (response.isSuccessful()) {
                    // 1. 获取下载文件的后缀名                
                    String ext = url.substring(url.lastIndexOf(".") + 1);
                    // 2. 根据当前时间创建文件名，避免重名冲突              
                    final String fileName = System.currentTimeMillis() + "." + ext;
                    // 3. 获取响应主体的字节流              
                    InputStream is = response.body().byteStream();
                    // 4. 将文件写入path目录              
                    writeFile(is, path, fileName);
                    // 5. 在界面给出提示信息            
                    tvHttpurl.post(new Runnable() {
                        @Override
                        public void run() {
                            tvHttpurl.setText(fileName + "下载成功，存放在" + path);
                        }
                    });
                }
            }
             @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("OkHttpActivity", e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvHttpurl.setText("文件下载失败");
                    }
                });
            }
        }); }


  public static void writeFile(InputStream is, String path, String fileName)
            throws IOException {
        // 1. 根据path创建目录对象，并检查path是否存在，不存在则创建    
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // 2. 根据path和fileName创建文件对象，如果文件存在则删除    
        File file = new File(path, fileName);
        if (file.exists()) {
            file.delete();
        }
        // 3. 创建文件输出流对象，根据输入流创建缓冲输入流对象，    
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        // 4. 以每次1024个字节写入输出流对象    
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.flush();
        // 5. 关闭输入流、输出流对象    
         fos.close();
         bis.close();
    }

    private void uploadFile(String url, String fileName) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new File(fileName)))
                .build();
        try {
            Response response = client.newCall(request).execute();
            final String str = response.body().string();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvHttpurl.setText("上传成功" + str);
                }
            });
        } catch (final IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvHttpurl.setText("上传失败" + e.getMessage());
                    Log.e("tag", ">>>>>>>>" + e.getMessage());
                }
            });
        }
    }


    private class workThread extends Thread {
        @Override
        public void run() {
            try {
                final String json = HttpsUtil.get("https://v.juhe.cn/toutiao/index?type=&key=aeed60af1d88866d68868bb5c973a77b");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvHttpurl.setText(json);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
