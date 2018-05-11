package com.example.android.smartphonenewsapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        String newsSection = currentNews.getSection();
        String newsPillar = currentNews.getPillarName();
        String newsAuthor = currentNews.getAuthor();

        // Find the TextView with view ID title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(newsTitle);
        // Fetch the text color from the TextView.
        titleTextView.getTextColors();
        // Get the appropriate text color based on the current section of title
        int colorOfText = getTextColor(currentNews.getPillarName());
        // Set the color on the magnitude circle
        titleTextView.setTextColor(colorOfText);

        // Find the TextView with view ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(newsDate);

        // Find the TextView with view ID article_section
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.article_section);
        sectionTextView.setText(newsSection);

        // Find the TextView with view ID date
        TextView pillarTextView = (TextView) listItemView.findViewById(R.id.article_pillar);
        pillarTextView.setText(newsPillar);

        // Find the TextView with view ID author
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(newsAuthor);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the color for the text based on the section of the news.
     *
     * @param section of the news
     */
    private int getTextColor(String section) {
        int newsColorResourceId;
        switch (section) {
            case "News":
                newsColorResourceId = R.color.news;
                break;
            case "Opinion":
                newsColorResourceId = R.color.opinion;
                break;
            case "Sport":
                newsColorResourceId = R.color.sport;
                break;
            case "Culture":
                newsColorResourceId = R.color.culture;
                break;
            case "Lifestyle":
                newsColorResourceId = R.color.lifestyle;
                break;
            default:
                newsColorResourceId = R.color.more;
                break;
        }
        return ContextCompat.getColor(getContext(), newsColorResourceId);
}
}