package com.example.android.newsapp;

import android.graphics.Bitmap;

public class Article {

    private String mTitle;
    private String mWebUrl;
    private String mSectionName;
    private String mDate;
    private String mAuthor=null;
    private String mTrailText=null;
    private Bitmap mThumbnail=null;

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

    public Bitmap getThumbnailBitmap() {return mThumbnail;}

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public void setTrailText(String trailText) {
        mTrailText = trailText;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        mThumbnail = thumbnailBitmap;
    }
}
