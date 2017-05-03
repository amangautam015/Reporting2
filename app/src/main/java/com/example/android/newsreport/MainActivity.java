package com.example.android.newsreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
public int ff;
    public MainActivity(int f){

        ff=f;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        int value ; // or other values
        if(b!=null){
            value = b.getInt("k");}
        else {value=0;}
        ff=value;
//String Source ;
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.word_list);
      //  ActionBar actionBar = getActionBar();
       // getActionBar().setIcon(R.drawable.ic_action_name);
     //   actionBar.setCustomView(R.layout.actionbar_view);
        //AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
        //String[] countries = getResources().getStringArray(R.array.countries_array);
// Create the adapter and set it to the AutoCompleteTextView
     //   ArrayAdapter<String> adapter1 =
       //         new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        //textView.setAdapter(adapter1);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //if(textView.getText().toString()== null){
        //Source = "International";}
        ///else {
           // Source = textView.getText().toString();
        //}

        // Create an adapter that knows which fragment should be shown on each page
        CatogeryAdapter adapter = new CatogeryAdapter(ff,MainActivity.this,getSupportFragmentManager());

        // Set the adapter onto the view pager
      //  viewPager.setAdapter(adapter);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, com.example.android.newsreport.SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
