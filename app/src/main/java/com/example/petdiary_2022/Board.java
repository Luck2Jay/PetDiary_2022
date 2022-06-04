package com.example.petdiary_2022;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    String id; // 작성자
    String name; // 제목
    String text; // 작성글
    String date; // 작성 시간

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board(String id, String name, String text, String date) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Board() {
    }
}
