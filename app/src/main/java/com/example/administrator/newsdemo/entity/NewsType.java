package com.example.administrator.newsdemo.entity;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */

public class NewsType implements Serializable {
    public ArrayList<SubName> tList;

    public NewsType(ArrayList<SubName> tList){

        this.tList=tList;
    }

    public static class SubName  implements Serializable{
        public String tid,tname;

        public SubName(String tid, String tname) {
            this.tid = tid;
            this.tname = tname;
        }
    }
}
