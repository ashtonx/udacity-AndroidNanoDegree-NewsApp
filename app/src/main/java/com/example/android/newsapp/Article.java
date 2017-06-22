package com.example.android.newsapp;

public class Article {

    private String mTitle;
    private String mWebUrl;
    private String mSectionName;
    private String mDate;
    private String mAuthor=null;
    private String mTrailText=null;
    private String mThumbnailUrl=null;

    Article(String title, String webUrl, String sectionName, String date) {
        mTitle = title;
        mWebUrl = webUrl;
        mSectionName = sectionName;
        mDate=date;
    }

    public String getTite() {
        return mTitle;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getDate() {
        return mDate;
    }

    public String getTrailText() {
        return mTrailText;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getThumbnailUrl() {return mThumbnailUrl;}

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public void setTrailText(String trailText) {
        mTrailText = trailText;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }
}
