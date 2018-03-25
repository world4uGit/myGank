package com.rndchina.mygank.main.model;

/**
 * Created by PC on 2018/1/12.
 */

public class DrawerModel {
    private int iconID;
    private String title;

    public DrawerModel(int iconID, String title) {
        this.iconID = iconID;
        this.title = title;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
