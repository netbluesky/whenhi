package com.whenhi.hi.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王雷 on 2017/1/18.
 */

public class UserScoreData implements Serializable {
    private String extras;
    private int currentPageNo;
    private int currentPageSize;
    private List<UserScore> list;

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public List<UserScore> getList() {
        return list;
    }

    public void setList(List<UserScore> list) {
        this.list = list;
    }
}
