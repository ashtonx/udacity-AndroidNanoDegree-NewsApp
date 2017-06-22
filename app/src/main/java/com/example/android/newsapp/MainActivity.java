package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>{
    private TextView mEmptyStateView;
    private ArticleAdapter mAdapter;
    private static final int LOADER_ID =1;
    private ProgressBar mProgressBar;
    private static final String QUERYTEST="technology";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateView=(TextView) findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        ListView articleListView = (ListView) findViewById(R.id.list);
        articleListView.setEmptyView(mEmptyStateView);
        articleListView.setAdapter(mAdapter);

        performSearch(QUERYTEST);

    }

    private void performSearch(String query){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();
        if (netInfo!=null && netInfo.isConnected()) {
            mProgressBar.setVisibility(View.VISIBLE);

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            mEmptyStateView.setText(getString(R.string.no_internet));
        }
    }

    //loader stuff


    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this, QUERYTEST);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateView.setText(getString(R.string.no_results));
        mAdapter.clear();
        if (data!=null && !data.isEmpty()) mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }
}
