package com.example.android.newsreport;

/**
 * Created by Dell on 29-04-2017.
 */

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of Newss by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {
public int flag;
    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();
    public String u="ignoreKeywords=&keywords=";
    /** Query URL */
    private String mUrl;
private String mCountry;
    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public NewsLoader(int m,Context context, String url,String ty) {
        super(context);
        mUrl = url;
        mCountry=ty;
        flag=m;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of Newss.
        if(flag==0){
            String k;
            String i;
            mCountry=mCountry.trim();
            i=mCountry.replaceAll("\\s","%20");
            k = mUrl.replaceAll(u+"International",u+i);
            List<News> Newss = Utils.fetchNewsData(k,mCountry);
            return Newss;
        }
        else {
            String cc= Utils1.fetchcountryData("http://ip-api.com/json");
            String k;
            String i;
            cc=cc.trim();
            i=cc.replaceAll("\\s","%20");
            // k = mUrl.replaceAll(u+"International",u+i);
            k = mUrl.replaceAll(u+"International",u+i);
            List<News> Newss = Utils.fetchNewsData(k,cc);
            return Newss;

        }
    }
}