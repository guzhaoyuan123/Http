package com.example.http.weather;

public class FutureBean {
    /**
     * date : 2019-02-22
     * temperature : 1/7℃
     * weather : 小雨转多云
     * wid : {"day":"07","night":"01"}
     * direct : 北风转西北风
     */

    private String date;
    private String temperature;
    private String weather;
    private WidBean wid;
    private String direct;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public WidBean getWid() {
        return wid;
    }

    public void setWid(WidBean wid) {
        this.wid = wid;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }


}
