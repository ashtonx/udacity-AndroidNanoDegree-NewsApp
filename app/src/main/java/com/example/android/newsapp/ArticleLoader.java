package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {
    private String mQuery;

    public ArticleLoader(Context context, String query){
        super(context);
        mQuery=query;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if(mQuery == null) return null;
        return QueryUtils.fetchData(mQuery);
    }
}
