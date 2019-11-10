package com.example.http.weather;

import java.util.List;

public class ResultBean {
    /**
     * city : 苏州
     * realtime : {"temperature":"4","humidity":"82","info":"阴","wid":"02","direct":"西北风","power":"3级","aqi":"80"}
     * future : [{"date":"2019-02-22","temperature":"1/7℃","weather":"小雨转多云","wid":{"day":"07","night":"01"},"direct":"北风转西北风"},{"date":"2019-02-23","temperature":"2/11℃","weather":"多云转阴","wid":{"day":"01","night":"02"},"direct":"北风转东北风"},{"date":"2019-02-24","temperature":"6/12℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"东北风转北风"},{"date":"2019-02-25","temperature":"5/12℃","weather":"小雨转多云","wid":{"day":"07","night":"01"},"direct":"东北风"},{"date":"2019-02-26","temperature":"5/11℃","weather":"多云转小雨","wid":{"day":"01","night":"07"},"direct":"东北风"}]
     */

    private String city;
    private RealtimeBean realtime;
    private List<FutureBean> future;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public List<FutureBean> getFuture() {
        return future;
    }

    public void setFuture(List<FutureBean> future) {
        this.future = future;
    }
}
