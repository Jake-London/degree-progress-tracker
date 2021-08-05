package com.example.programtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.programtracker.data.AppDatabase;
import com.example.programtracker.data.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DegreeActivity extends AppCompatActivity {
    private AppDatabase appDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Degree Details");

        appDb = AppDatabase.getInstance(this);
        List<Course> cur_courses = appDb.courseDao().getCourseList();
        List<String> needed_courses = checkList(cur_courses);
        TextView req = (TextView) findViewById(R.id.course_req);
        String reqs;
        if (needed_courses.size() == 0){
            getTheme();
            reqs = "All requirements fulfilled";
            req.setText(reqs);
        }
        else {
            reqs = String.join("\n", needed_courses);
            req.setText(reqs);
        }

    }

    public ArrayList<String> checkList(List<Course> courses) {
        ArrayList<String> req_courses = new ArrayList<>();
        Collections.addAll(req_courses, "CIS*1300", "CIS*1910", "CIS*2500", "CIS*2910", "MATH*1160", "CIS*2030", "CIS*2430", "CIS*2520", "CIS*2750", "CIS*3110", "CIS*3490", "CIS*3150", "CIS*3750", "STAT*2040", "CIS*3760", "CIS*4650");
        String temp;

        for (Course course : courses) {
            temp = course.getCode();
            if (req_courses.contains(temp)) {
                req_courses.remove(temp);
            }
        }
        return req_courses;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Resources.Theme getTheme() {
        appDb = AppDatabase.getInstance(this);
        List<Course> cur_courses = appDb.courseDao().getCourseList();
        List<String> needed_courses = checkList(cur_courses);
        Resources.Theme theme = super.getTheme();
        if (needed_courses.size() == 0) {
            theme.applyStyle(R.style.Theme_ProgramTracker2, true);
        }
        return theme;
    }
}