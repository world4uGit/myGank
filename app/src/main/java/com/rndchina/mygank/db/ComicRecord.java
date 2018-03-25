package com.rndchina.mygank.db;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PC on 2018/3/21.
 */

public class ComicRecord extends RealmObject implements Serializable {

    @PrimaryKey
    private int _id;
    private String name;
    private int index;
    private int position;
    private int page;


    public ComicRecord(int _id, String name, int index, int position, int page) {
        this._id = _id;
        this.name = name;
        this.index = index;
        this.position = position;
        this.page = page;
    }

    public ComicRecord() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
