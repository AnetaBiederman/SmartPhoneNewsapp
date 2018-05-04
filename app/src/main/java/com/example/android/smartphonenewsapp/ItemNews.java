package com.example.android.smartphonenewsapp;

public class ItemNews {

    private String eUrl;

    private String eTitle;

    private String eDate;

    private String eSection;

    private String eAuthor;

    /**
     * Create a new Item object.
     *
     * @param url     url of new
     * @param title   title of news
     * @param date    date of news
     * @param section of article (technology, travel)
     * @param author   of article (name)
     */
    public ItemNews(String title, String date, String section, String author, String url) {
        eUrl = url;
        eTitle = title;
        eDate = date;
        eSection = section;
        eAuthor = author;
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
}
