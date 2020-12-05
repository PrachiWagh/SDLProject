package com.sdl.sdlproject.Model;

public class NavListItem {
    String mTitle;
    String mSubtitle;
    int mIcon;

    public NavListItem(String mTitle, int mIcon) {
        this.mTitle = mTitle;
        this.mIcon = mIcon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public int getmIcon() {
        return mIcon;
    }
}
