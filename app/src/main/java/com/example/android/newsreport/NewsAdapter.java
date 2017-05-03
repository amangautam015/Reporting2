package com.example.android.newsreport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Dell on 28-04-2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Activity context, ArrayList<News>news) {

        super(context,0,news);
    }
  int k =  getContext().getResources().getConfiguration().orientation;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null ) {

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_news_item,parent,false);
        }
        News currentFile = getItem(position);

        ImageView iv = (ImageView)  listItemView.findViewById(R.id.image);
if (k ==1 ) {Picasso.with(getContext()).load(currentFile.getImageResourceID()).placeholder(R.drawable.placeholder).resize(150,150).centerCrop().into(iv);
}
        else if(k==2) {Picasso.with(getContext()).load(currentFile.getImageResourceID()).placeholder(R.drawable.placeholder).resize(180,200).centerCrop().into(iv);}
        TextView tv1 = (TextView) listItemView.findViewById(R.id.country_name);
        tv1.setText(currentFile.getCountry_name());
        TextView tv2 = (TextView) listItemView.findViewById(R.id.body);
        tv2.setText(currentFile.getBody());
        TextView tv3 = (TextView) listItemView.findViewById(R.id.headline);
        tv3.setText(currentFile.getHeadline());
        TextView tv4 = (TextView) listItemView.findViewById(R.id.time);
TextView tv5 = (TextView) listItemView.findViewById(R.id.source);
        tv5.setText(currentFile.getSource());

      tv4.setText(currentFile.getTime()+"");
TextView tv7 = (TextView) listItemView.findViewById(R.id.date);
        tv7.setText(currentFile.getDate());

        return listItemView;
    }

}
