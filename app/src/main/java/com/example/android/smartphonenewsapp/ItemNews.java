package com.example.android.smartphonenewsapp;

public class ItemNews {

    private String eUrl;

    private String eTitle;

    private String eDate;

    /**
     * Create a new Item object.
     *
     * @param url url of new
     * @param title title of news
     * @param date date of news
     */
    public ItemNews (String title, String date, String url) {
        eUrl = url;
        eTitle = title;
        eDate = date;
    }

    public String getdate() {
        return eDate;
    }

    public String getTitle() {
        return eTitle;
    }

    public String getUrl() {
        return eUrl;
    }

}
