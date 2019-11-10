package com.example.http.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.http.util.HttpsUtil2.getSSLSocketFactory;
import static com.example.http.util.HttpsUtil2.getTrustManager;

public class HttpsUtil {
//    原生获取json数据
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


//    获取新闻json数据
    public static String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


//    获取种类新闻
    public static String getType(String type) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://v.juhe.cn/toutiao/index?type="+type+"&key=aeed60af1d88866d68868bb5c973a77b")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

        public static OkHttpClient handleSSLHandshakeByOkHttp() {
        X509TrustManager trustManager = (X509TrustManager) getTrustManager()[0];

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                //支持HTTPS请求，跳过证书验证
                .sslSocketFactory(getSSLSocketFactory(), trustManager)
                .hostnameVerifier(new HttpsUtil2.TrustAllHostnameVerifier())
                .build();
    }
}
