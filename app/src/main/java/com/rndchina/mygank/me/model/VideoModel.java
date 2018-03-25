package com.rndchina.mygank.me.model;

/**
 * @author Leon
 * @date
 */

public class VideoModel {
    private int resourceId;
    private String url;

    public VideoModel(int resourceId, String url) {
        this.resourceId = resourceId;
        this.url = url;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
