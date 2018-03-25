package com.rndchina.mygank.music.model;

/**
 * Created by PC on 2018/3/9.
 */

public class VideoCategoryInfo {

    /**
     * id : 24
     * name : 时尚
     * alias : null
     * description : 优雅地行走在潮流尖端
     * bgPicture : http://img.kaiyanapp.com/22192a40de238fe853b992ed57f1f098.jpeg
     * bgColor :
     * headerImage : http://img.kaiyanapp.com/c9b19c2f0a2a40f4c45564dd8ea766d3.png
     * defaultAuthorId : null
     */

    private int id;
    private String name;
    private Object alias;
    private String description;
    private String bgPicture;
    private String bgColor;
    private String headerImage;
    private Object defaultAuthorId;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(Object alias) {
        this.alias = alias;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBgPicture(String bgPicture) {
        this.bgPicture = bgPicture;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public void setDefaultAuthorId(Object defaultAuthorId) {
        this.defaultAuthorId = defaultAuthorId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getAlias() {
        return alias;
    }

    public String getDescription() {
        return description;
    }

    public String getBgPicture() {
        return bgPicture;
    }

    public String getBgColor() {
        return bgColor;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public Object getDefaultAuthorId() {
        return defaultAuthorId;
    }
}
