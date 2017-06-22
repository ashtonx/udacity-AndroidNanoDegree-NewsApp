package com.example.android.newsapp;

public class Article {

    private String mTitle;
    private String mWebUrl;
    private String mSectionName;
    private String mDate = null;
    private String mTrailText = null;
    private String mAuthor = null;
    private String mThumbnailUrl=null;
    Article(String title, String webUrl, String sectionName) {
        mTitle = title;
        mWebUrl = webUrl;
        mSectionName = sectionName;
    }

    public String getTite() {
        return mTitle;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getSectionTitle() {
        return mSectionName;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    public void setTrailText(String trailText) {
        mTrailText = trailText;
    }

    public String getTrailText() {
        return mTrailText;
    }

    public void setAuhtor(String author) {
        mAuthor = author;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setThumbnailUrl(String thumbnailUrl){mThumbnailUrl=thumbnailUrl;}
    public String getThumbnailUrl() {return mThumbnailUrl;}
}
