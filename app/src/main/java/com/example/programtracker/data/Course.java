package com.example.programtracker.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "course_title")
    private String title;

    @ColumnInfo(name = "course_code")
    private String code;

    @ColumnInfo(name = "course_semester")
    private int semester;

    @ColumnInfo(name = "course_description")
    private String description;

    @ColumnInfo(name = "course_restrictions")
    private String restrictions;

    @ColumnInfo(name = "course_prereq")
    private String prereq;

    @ColumnInfo(name = "course_credits")
    private String credits;

    @Ignore
    public Course(int course_id, String title, String code, int semester, String description, String restrictions, String prereq, String credits) {
        this.id = course_id;
        this.title = title;
        this.code = code;
        this.semester = semester;
        this.description = description;
        this.restrictions = restrictions;
        this.prereq = prereq;
        this.credits = credits;
    }

    public Course(String title, String code, int semester, String description, String restrictions, String prereq, String credits) {
        this.title = title;
        this.code = code;
        this.semester = semester;
        this.description = description;
        this.restrictions = restrictions;
        this.prereq = prereq;
        this.credits = credits;
    }

    @Ignore
    public Course(String title, String code, String description, String restrictions, String prereq, String credits) {
        this.title = title;
        this.code = code;
        this.description = description;
        this.restrictions = restrictions;
        this.prereq = prereq;
        this.credits = credits;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrereq() {
        return prereq;
    }

    public void setPrereq(String prereq) {
        this.prereq = prereq;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = code;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
