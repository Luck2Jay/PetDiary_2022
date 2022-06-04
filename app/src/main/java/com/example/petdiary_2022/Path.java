package com.example.petdiary_2022;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Path implements Serializable {

    String id,name;
    List<String> list = new ArrayList<String>();

    public Path() {
    }

    public Path(String id, String name, List<String> list) {
        this.id = id;
        this.name = name;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
