package com.example.android.smartphonenewsapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<ItemNews> {

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param news    is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<ItemNews> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the news at the given position
     * in the list of news.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.smart_phone_item, parent, false);
        }

        // Find the news at the given position in the list of news
        ItemNews currentNews = getItem(position);

        //Get the object's properties
        String newsTitle = currentNews.getTitle();
        String newsDate = currentNews.getDate();
        String newSection = currentNews.getSection();
        String newTopic = currentNews.getTopic();

        // Find the TextView with view ID title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(newsTitle);

        // Find the TextView with view ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(newsDate);

        // Find the TextView with view ID date
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.article_section);
        sectionTextView.setText(newSection);

        // Find the TextView with view ID date
        TextView topicTextView = (TextView) listItemView.findViewById(R.id.article_topic);
        topicTextView.setText(newTopic);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}