package com.example.android.smartphonenewsapp;

import android.content.Context;
import android.content.Intent;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SmartPhoneNewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<ItemNews>> {

    private static final String LOG_TAG = SmartPhoneNewsActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "https://content.guardianapis.com/search";
    private static final int NEWS_LOADER_ID = 1;

    private TextView mEmptyStateTextView;

    private ProgressBar mProgressBar;

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_phone_news);

        ListView newsListView = findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_state_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NewsAdapter(this, new ArrayList<ItemNews>());

        newsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ItemNews currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        // check if internet is available
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<ItemNews>> onCreateLoader(int i, Bundle bundle) {

        long longFromDate = 0;
        long longToDate = 0;
        String newsFrom = "";
        String newsTo = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String section = sharedPreferences.getString(getString(R.string.settings_choose_section_key), getString(R.string.settings_choose_section_default));

        String orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        longFromDate = Long.parseLong(sharedPreferences.getString(
                getString(R.string.settings_news_from_key), "0"));
        longToDate = Long.parseLong(sharedPreferences.getString(
                getString(R.string.settings_news_to_key), "0"));

        Date dateObjectFrom = new Date(longFromDate);
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.setTime(dateObjectFrom);
        newsFrom = dateFormat.format(calendarFrom.getTime());

        Date dateObjectTo = new Date(longToDate);
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(dateObjectTo);
        newsTo = dateFormat.format(calendarTo.getTime());

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("page-size", "50");
        uriBuilder.appendQueryParameter("q", "smartphone");
        uriBuilder.appendQueryParameter("api-key", "4f6d3d44-39b3-406a-95de-feb62fb2fd09");
        uriBuilder.appendQueryParameter("order-by", orderBy);

        if (!newsFrom.equals(getString(R.string.default_date))) {
            uriBuilder.appendQueryParameter("from-date", newsFrom);
        }
        if (!newsTo.equals(getString(R.string.default_date))) {
            uriBuilder.appendQueryParameter("to-date", newsTo);
        }
        if (!section.equals(getString(R.string.settings_choose_section_default))) {
            uriBuilder.appendQueryParameter("section", section);
        }
        if (longFromDate > longToDate){
            Toast.makeText(getBaseContext(), R.string.from_before_to_warning, Toast.LENGTH_LONG).show();

        }
        // Return the completed uri 'https://content.guardianapis.com/search?show-tags=contributor&show-fields=thumbnail&page-size=50&q=smartphone&api-key=4f6d3d44-39b3-406a-95de-feb62fb2fd09'.
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<ItemNews>> loader, List<ItemNews> news) {
        //Set visibility for progressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        // Set empty state text to display "No news found."
        mEmptyStateTextView.setText(R.string.empty_state);

        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ItemNews>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}