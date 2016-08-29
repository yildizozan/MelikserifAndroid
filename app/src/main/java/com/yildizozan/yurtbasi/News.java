package com.yildizozan.yurtbasi;

import java.io.Serializable;

/**
 * Created by Ozan on 8/19/2016.
 */

public class News implements Serializable {
    private String mDate;
    private String mTitle;
    private String mContent;
    private String mAuthor;

    public News(String pDate, String pTitle, String pContent, String pAuthor) {
        this.mDate = pDate;
        this.mTitle = pTitle;
        this.mContent = pContent;
        this.mAuthor = pAuthor;
    }

    public News(News pNews) {
        this.mDate = pNews.getmDate();
        this.mTitle = pNews.getmTitle();
        this.mContent = pNews.getmContent();
        this.mAuthor = pNews.getmAuthor();
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
}
