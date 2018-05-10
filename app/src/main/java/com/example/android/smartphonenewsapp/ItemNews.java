package com.example.android.smartphonenewsapp;

import android.graphics.Bitmap;

public class ItemNews {

    private String eUrl;

    private String eTitle;

    private String eDate;

    private String eSection;

    private String eAuthor;

    private String ePillarName;

    /**
     * Create a new Item object.
     *
     * @param url        url of new
     * @param title      title of news
     * @param date       date of news
     * @param section    of article (technology, travel)
     * @param author     of article (name)
     * @param pillarName of main section
     */
    public ItemNews(String title, String date, String section, String author, String url, String pillarName) {
        eUrl = url;
        eTitle = title;
        eDate = date;
        eSection = section;
        eAuthor = author;
        ePillarName = pillarName;
    }

    public String getDate() {
        return eDate;
    }

    public String getTitle() {
        return eTitle;
    }

    public String getUrl() {
        return eUrl;
    }

    public String getSection() {
        return eSection;
    }

    public String getAuthor() {
        return eAuthor;
    }

    public String getPillarName() {
        return ePillarName;
    }

}
