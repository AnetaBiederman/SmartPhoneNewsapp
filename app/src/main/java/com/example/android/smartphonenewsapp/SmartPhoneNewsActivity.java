package com.example.android.smartphonenewsapp;

import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SmartPhoneNewsActivity extends AppCompatActivity {

    /**
     * Adapter for the list of news
     */
    private NewsAdapter mAdapter;

    private static final String LOG_TAG = SmartPhoneNewsActivity.class.getName();

    /**
     * URL to query the USGS dataset for news information
     */
    private static final String USGS_REQUEST_URL =
            "http://content.guardianapis.com/search?section=technology&q=smart%20phone&api-key=4f6d3d44-39b3-406a-95de-feb62fb2fd09";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_phone_news);
    }
    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(List<ItemNews> news) {

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes the list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<ItemNews>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                ItemNews currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Start the AsyncTask to fetch the news data
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<ItemNews>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link ItemNews}s as the result.
         */
        @Override
        protected List<ItemNews> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<ItemNews> results = QueryUtils.fetchNewsData(urls[0]);
            return results;
        }

        @Override
        protected void onPostExecute(List<ItemNews> news) {
            // Clear the adapter of previous news data
            mAdapter.clear();

            if (news != null && !news.isEmpty()) {
                mAdapter.addAll(news);
                updateUi(news);
            }
        }
    }
}

