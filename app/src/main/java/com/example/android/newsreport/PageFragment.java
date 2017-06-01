package com.example.android.newsreport;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment implements OnRefreshListener,LoaderCallbacks<List<News>> {
    public String URL_REQUEST;
    public int flag;
    public String ty;

    public  ImageButton ib;
    public String u="ignoreKeywords=&keywords=";
    public   String URL_REQUEST1 ;// "http://eventregistry.org/json/article?categoryUri=dmoz%2FNews&ignoreKeywords=&keywords=India&lang=eng&action=getArticles&articlesSortBy=date&resultType=articles&articlesIncludeArticleCategories=true&articlesIncludeArticleLocation=true&articlesIncludeArticleImage=true&articlesIncludeConceptSynonyms=true&articlesIncludeConceptImage=true&articlesIncludeConceptDescription=true&articlesIncludeConceptDetails=true&articlesIncludeConceptTrendingScore=true&articlesIncludeSourceDescription=true&articlesIncludeSourceLocation=true&articlesIncludeSourceImportance=true&articlesIncludeSourceDetails=true&callback=JSON_CALLBACK";
    private static final int NEWS_LOADER_ID = 1;
    private static final int COUNTRY_LOADER_ID=2;
    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;
    public View loadingIndicator;
    public SwipeRefreshLayout swipeLayout;
    public PageFragment() {
        // Required empty public constructor
    }
    public static final String MESSAGE = "MESSAGE";
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    private int mPage;

    public static PageFragment newInstance(int m,int page,String k) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(EXTRA_MESSAGE,k);
        args.putInt(MESSAGE,m);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        URL_REQUEST1 = getArguments().getString(EXTRA_MESSAGE);
        flag = getArguments().getInt(MESSAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        ListView newsListView = (ListView) rootView.findViewById(R.id.list);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);

        ib=(ImageButton)rootView.findViewById(R.id.buttonc);

        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getU());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID,null, this);
            //
        }else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible

            swipeLayout.setRefreshing(false);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText("NO INTERNET CONNECTION :((");

        }
        ib = (ImageButton) rootView.findViewById(R.id.buttonc);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                dowork();
                loadingIndicator.setVisibility(View.GONE);
            }
        });
        // loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        return rootView;
    }

    @Override
    public android.support.v4.content.Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String ty  = sharedPrefs.getString(getString(R.string.settings_key),getString(R.string.settings_default));
        //  URL_REQUEST = URL_REQUEST1.replaceAll(u+"International",u+ty);
        return new NewsLoader(flag,getActivity(),URL_REQUEST1,ty);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<News>> loader, List<News> news) {
        // View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);
        // Set empty state text to display "No news found."

        mEmptyStateTextView.setText("No new update OR Slow internet");

        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link news}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);

        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<News>> loader) {
        mAdapter.clear();
    }


    @Override
    public void onRefresh() {

        getLoaderManager().destroyLoader(NEWS_LOADER_ID);
        mEmptyStateTextView.setText("");
        PageFragment.newInstance(flag,mPage,URL_REQUEST);
        mEmptyStateTextView.setText("");
        getLoaderManager().initLoader(NEWS_LOADER_ID,null, this);
        // Intent intent = new Intent(getActivity(), PageFragment.class);
        //swipeLayout.setRefreshing(false);
        //startActivity(intent);
    }
    public void dowork(){
        flag=1; loadingIndicator.setVisibility(View.VISIBLE);  swipeLayout.setRefreshing(false);
        //  getLoaderManager().destroyLoader(NEWS_LOADER_ID);
        //mEmptyStateTextView.setText("");
        //PageFragment.newInstance(flag,mPage,URL_REQUEST);
        //mEmptyStateTextView.setText("");
        //getLoaderManager().initLoader(NEWS_LOADER_ID,null, this);
        //

        Bundle b= new Bundle();
        b.putInt("k",flag);
        Intent intent = new Intent(getActivity(), MainActivity.class);

        intent.putExtras(b);

        startActivity(intent);

    }
/*public void message(View view){
    flag=1;
    loadingIndicator.setVisibility(View.VISIBLE);
    getLoaderManager().destroyLoader(NEWS_LOADER_ID);
    mEmptyStateTextView.setText("");
    PageFragment.newInstance(flag,mPage,URL_REQUEST);
    mEmptyStateTextView.setText("");
    getLoaderManager().initLoader(NEWS_LOADER_ID,null, this);

}*/






   /* @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this,URL_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "Nonews  found."
        mEmptyStateTextView.setText("No new update at requested date.OR Slow internet");

        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link news}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();

    } */
}







