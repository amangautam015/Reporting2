package com.example.android.newsreport;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 29-04-2017.
 */

public class QueryUtils1 {
    private static String news;

    private QueryUtils1() {
    }

    public static String extractNews(String y) {
try {

    JSONObject jsonObj = new JSONObject(y);
   news = jsonObj.getString("country");
}
     //   String x = y.substring(14,y.length()-1);
      /*  List<News> news = new ArrayList<>();
        try {

            JSONObject jsonObj = new JSONObject(x);
            JSONObject o = jsonObj.getJSONObject("articles");
            JSONArray results = o.getJSONArray("results");
            for(int  i =0 ;i<results.length();i++){
                JSONObject obj1 = results.getJSONObject(i);
                String url =obj1.getString("url");
                String headline = obj1.getString("title");
                String body = obj1.getString("body");
                String image = obj1.getString("image");
                String time = (obj1.getString("time"));
                String date = obj1.getString("date");
                JSONObject obj2 = obj1.getJSONObject("source");
                String source = obj2.getString("title");

                news.add(new News(date,(country + " | "),body,headline,image,url,time,source));
//Log.e("Query Utils","wtf");

            }
        }*/
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QuCOCOCCOCO", "Problem parsing the news JSON results", e);
        }
        return news;
    }
}
