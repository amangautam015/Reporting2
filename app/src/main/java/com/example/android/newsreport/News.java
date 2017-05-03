package com.example.android.newsreport;

/**
 * Created by Dell on 28-04-2017.
 */

public class News {
    private String date;
    private String country_name ;
    private String body;
    private String headline;
    private String imageResourceID;
    private String u;
    private String time;
private String source;
    public News (String mdate,String mcountry_name,String mbody,String mheadline,String  mimageResourceID,String mu ,String mtime,String msource) {
        country_name = mcountry_name;
        body = mbody;
        headline = mheadline;
        imageResourceID = mimageResourceID;
        u = mu;
        time= mtime;
        date= mdate;
source = msource;

    }
    public String getDate() {return date;}

    public String getSource() {
        return source;
    }

    public String getImageResourceID() {
        return imageResourceID;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getTime() {
        return time;
    }

    public String getBody() {
        return body;
    }

    public String getHeadline() {
        return headline;
    }

    public String getU() {
        return u;
    }

}
