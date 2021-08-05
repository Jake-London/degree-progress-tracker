package com.example.programtracker.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Semester {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "semester_num")
    private int num;

    @ColumnInfo(name = "semester_name")
    private String name;

    @Ignore
    public Semester(int id, int num, String name) {
        this.id = id;
        this.num = num;
        this.name = name;
    }

    public Semester(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
