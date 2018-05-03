package com.example.android.smartphonenewsapp;

public class ItemNews {

    private String eUrl;

    private String eTitle;

    private String eDate;

    private String eSection;

    private String eTopic;

    /**
     * Create a new Item object.
     *
     * @param url     url of new
     * @param title   title of news
     * @param date    date of news
     * @param section of article (technology, travel)
     * @param topic   of article (news, lifestyle)
     */
    public ItemNews(String title, String date, String section, String topic, String url) {
        eUrl = url;
        eTitle = title;
        eDate = date;
        eSection = section;
        eTopic = topic;
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

    public String getTopic() {
        return eTopic;
    }
}
