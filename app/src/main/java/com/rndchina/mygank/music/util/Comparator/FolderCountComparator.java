package com.rndchina.mygank.music.util.Comparator;


import com.rndchina.mygank.music.model.FolderInfo;

import java.util.Comparator;

public class FolderCountComparator implements Comparator<FolderInfo> {

    @Override
    public int compare(FolderInfo a1, FolderInfo a2) {
        Integer py1 = a1.folder_count;
        Integer py2 = a2.folder_count;
        return py2.compareTo(py1);
    }

    private boolean isEmpty(String str) {
        return "".equals(str.trim());
    }
}  