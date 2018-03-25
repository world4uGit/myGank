package com.rndchina.mygank.music.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by PC on 2018/3/10.
 */

public class Util {
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    public static long getCurrentSeconds(){
        long ls = System.currentTimeMillis()/1000;
        return ls;
    }
}
