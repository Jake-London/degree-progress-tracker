package com.example.programtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.programtracker.data.AppDatabase;
import com.example.programtracker.data.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CourseDetailsActivity";
    private int semester_id;
    private AppDatabase appDb;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Course Details");
        Intent intent = getIntent();
        semester_id = intent.getIntExtra("semesterId", 0);
        Log.i(TAG, "onCreate: " + semester_id);
        setContentView(R.layout.course_activity);

        appDb = AppDatabase.getInstance(this);

        TextView title = (TextView) findViewById(R.id.course_name);
        TextView credits = (TextView) findViewById(R.id.course_credits);
        TextView prereq = (TextView) findViewById(R.id.course_prereq);
        TextView restrictions = (TextView) findViewById(R.id.course_restriction);
        TextView description = (TextView) findViewById(R.id.course_description);

        List<Course> courses = appDb.courseDao().getCourse(intent.getStringExtra("courseTitle"));
        if (!courses.isEmpty()) {
            course = courses.get(0);
            description.setText(course.getDescription());
            credits.setText(course.getCredits());
            title.setText(course.getCode() + " " + course.getTitle());
            try {
                restrictions.setText(jsonArrayToReadableString(course.getRestrictions()));
                prereq.setText(jsonArrayToReadableString(course.getPrereq()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }




    }

    public String jsonArrayToReadableString(String input) throws JSONException {

        JSONArray array = new JSONArray(input);

        if (array.length() == 0) {
            return "N/A";
        }

        ArrayList<String> strList = new ArrayList<String>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            strList.add(obj.getString("code"));
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < strList.size(); i++) {
            String s = strList.get(i);
            sb.append(s);
            if (i != strList.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.i(TAG, "onBackPressed: ");
        Intent intent = new Intent();
        intent.putExtra("semesterId", semester_id);
        setResult(RESULT_OK, intent);
        finish();
    }
}