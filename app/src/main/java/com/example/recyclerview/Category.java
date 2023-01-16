package com.example.recyclerview;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String names;
    private ArrayList<String> list;

    public Category(String names, ArrayList<String> list) {
        this.names = names;
        this.list = list;
    }
    public String getNames() {
        return names;
    }
    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
