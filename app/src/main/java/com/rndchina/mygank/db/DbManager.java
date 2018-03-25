package com.rndchina.mygank.db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by PC on 2018/1/23.
 */

/**
 * 数据库操作
 *
 * @author Leon
 * @date
 */

public class DbManager<T extends RealmObject> {
    private static DbManager dbManager;
    private Realm realm;

    private DbManager() {
    }

    public static DbManager getInstence() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }

    /**
     * 保存信息到数据库
     * 插入数据
     */
    public void save(Object object) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        if (object instanceof RealmObject) {
            realm.copyToRealm((RealmModel) object);
        }
        realm.commitTransaction();
    }

    /**
     * 查询所有数据
     *
     * @param clazz
     */
    public List<T> queryAll(Class<T> clazz) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<T> realmResults = realm.where(clazz).findAll();
        realm.commitTransaction();
        return realmResults;
    }

    /**
     * 删除数据
     */
    public void cancelSave(Class<T> clazz, String _id) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        T result = realm.where(clazz).equalTo("_id", _id).findFirst();
        result.deleteFromRealm();
        realm.commitTransaction();
    }

    /**
     * 查询单个数据
     * @param clazz
     * @param _id
     * @return
     */
    public T queryModel(Class<T> clazz,String _id){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        T result = realm.where(clazz).equalTo("_id", _id).findFirst();
        realm.commitTransaction();
        return result;
    }


    public T notifyModel(Class<T> clazz,String _id){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        T result = realm.where(clazz).equalTo("_id", _id).findFirst();
        realm.commitTransaction();
        return result;
    }
    /**
     * 查询单个数据
     * @param clazz
     * @param _id
     * @return
     */
    public T queryModel(Class<T> clazz,int _id){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        T result = realm.where(clazz).equalTo("_id", _id).findFirst();
        realm.commitTransaction();
        return result;
    }

}

