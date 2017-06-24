package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {
    private static final int LOADER_ID = 1;
    private static final String SEARCH_QUERY_KEY = "query";
    private TextView mEmptyStateView;
    private ArticleAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateView = (TextView) findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        ListView articleListView = (ListView) findViewById(R.id.list);
        articleListView.setEmptyView(mEmptyStateView);
        articleListView.setAdapter(mAdapter);
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        mAdapter.getItem(position).getWebUrl()
                ));
                if (browserIntent.resolveActivity(view.getContext().getPackageManager()) != null)
                    view.getContext().startActivity(browserIntent);
            }
        });

        if (getIntent() != null) handleIntent(getIntent());
    }

    //helpers
    private void handleIntent(Intent intent) {
        final String queryAction = intent.getAction();
        if (Intent.ACTION_SEARCH.equals(queryAction)) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Bundle bundle = new Bundle();
            bundle.putString(SEARCH_QUERY_KEY, query);
            lookupArticles(bundle);
        } else if (Intent.ACTION_MAIN.equals(queryAction)) {
            lookupArticles(null);
        }
    }

    private void lookupArticles(Bundle bundle) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            mProgressBar.setVisibility(View.VISIBLE);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            mEmptyStateView.setText(getString(R.string.no_internet));
        }
    }

    private String createUri(Bundle bundle) {
        String queryString;
        if (bundle != null) queryString = bundle.getString(SEARCH_QUERY_KEY);
        else queryString = "null";

        final String QUERY_URL = "http://content.guardianapis.com/search";
        final String ARG_QUERY = "q";
        final String ARG_ORDER = "order-By";
        final String ARG_API = "api-key";
        final String API_KEY = "test";
        final String ARG_SHOW_FIELDS = "show-fields";
        final String ARG_FIELDS_BYLINE = "byline";
        final String ARG_FIELDS_TRAILTEXT = "trailText";
        final String ARG_FIELDS_THUMBNAILS = "thumbnail";
        final String FIELDS_SEPARATOR = ",";

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter(ARG_QUERY, queryString);
        uriBuilder.appendQueryParameter(ARG_ORDER, orderBy);

        final boolean byline = sharedPrefs.getBoolean(getString(R.string.settings_use_byline_key), true);
        final boolean trailText = sharedPrefs.getBoolean(getString(R.string.settings_use_trailText_key), true);
        final boolean thumbnails = sharedPrefs.getBoolean(getString(R.string.settings_use_thumbnail_key), true);
        StringBuilder fieldsBuilder = new StringBuilder();
        if (byline) fieldsBuilder.append(ARG_FIELDS_BYLINE + FIELDS_SEPARATOR);
        if (trailText) fieldsBuilder.append(ARG_FIELDS_TRAILTEXT + FIELDS_SEPARATOR);
        if (thumbnails) fieldsBuilder.append(ARG_FIELDS_THUMBNAILS + FIELDS_SEPARATOR);
        if (fieldsBuilder.length() > 0) {
            fieldsBuilder.deleteCharAt(fieldsBuilder.length() - 1);         //seems simpler and less hassle than checking
            uriBuilder.appendQueryParameter(ARG_SHOW_FIELDS, fieldsBuilder.toString());
        }
        uriBuilder.appendQueryParameter(ARG_API, API_KEY);
        return uriBuilder.toString();

    }


    //main menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //loader stuff
    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this, createUri(args));
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateView.setText(getString(R.string.no_results));
        mAdapter.clear();
        if (data != null && !data.isEmpty()) mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }

    //intents
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
}
