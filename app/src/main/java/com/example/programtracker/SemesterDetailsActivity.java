package com.example.programtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programtracker.data.AppDatabase;
import com.example.programtracker.data.Course;
import com.example.programtracker.data.JsonHandler;
import com.example.programtracker.ui.AddedCourseAdapter;
import com.example.programtracker.ui.CourseSelectAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class SemesterDetailsActivity extends AppCompatActivity implements CourseSelectAdapter.OnCourseListener, AddedCourseAdapter.OnCourseListener {

    private static final String TAG = "SemesterDetailsActivity";
    private List<Course> allCourses;
    private List<Course> filteredCourses;
    private CourseSelectAdapter courseSelectAdapter;
    private RecyclerView searchRv;

    private List<Course> addedCourses;
    private AddedCourseAdapter addedCourseAdapter;
    private RecyclerView courseRv;

    private AppDatabase appDb;
    int semester_num;
    int semester_id;

    private EditText editText;

    private boolean editFocus = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semester_activity);
        Intent intent = getIntent();

        semester_num = intent.getIntExtra("semesterNum", 0) + 1;
        semester_id = intent.getIntExtra("semesterId", 0);

        setTitle("Semester " + (semester_num));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "onCreate: " + semester_id);

        allCourses = JSONtoCourseList();

        for (int i = 0; i < allCourses.size(); i++) {
            Course c = allCourses.get(i);
            Log.i(TAG, "Title: " + c.getTitle() + ", Code: " + c.getCode());
        }

        editText = (EditText) findViewById(R.id.editTextCourseName);
        searchRv = (RecyclerView) findViewById(R.id.list_courses);
        courseRv = (RecyclerView) findViewById(R.id.added_courses);


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchRv.setVisibility(View.VISIBLE);
                    courseRv.setVisibility(View.GONE);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            List<Course> temp = filterCourses(allCourses, editText.getText().toString());
                            filteredCourses.clear();
                            filteredCourses.addAll(temp);
                            courseSelectAdapter.notifyDataSetChanged();

                            for (int i = 0; i < filteredCourses.size(); i++) {
                                Course c = filteredCourses.get(i);
                                Log.i(TAG, i + ": " + c.getCode() + " - " + c.getTitle());
                            }
                        }
                    });
                } else {
//                    searchRv.setVisibility(View.INVISIBLE);
                }
            }
        });

        appDb = AppDatabase.getInstance(this);


        filteredCourses = new ArrayList<>();
        courseSelectAdapter = new CourseSelectAdapter(filteredCourses, this);
        searchRv.setAdapter(courseSelectAdapter);
        searchRv.setLayoutManager(new LinearLayoutManager(this));

        addedCourses = appDb.courseDao().getCoursesFromSemester(semester_id);
        addedCourseAdapter = new AddedCourseAdapter(addedCourses, this);
        courseRv.setAdapter(addedCourseAdapter);
        courseRv.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(SemesterDetailsActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                appDb.courseDao().deleteCourseFromSemester(semester_id, addedCourses.get(position).getTitle());
                addedCourses.remove(position);
                addedCourseAdapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(courseRv);

//        courseSelectAdapter = new CourseSelectAdapter()


    }



    public List<Course> filterCourses(List<Course> allCourses, String input) {
        ArrayList<Course> courses = new ArrayList<Course>();

        for (int i = 0; i < allCourses.size(); i++) {
            Course c = allCourses.get(i);
            String check = c.getCode() + " - " + c.getTitle();

            if (check.toLowerCase().contains(input.toLowerCase()) && !input.equals("")) {
                Log.i(TAG, "Matching course");
                courses.add(c);
            }

        }

        return courses;

    }

    public List<Course> JSONtoCourseList() {
        ArrayList<Course> courses = new ArrayList<Course>();
        JsonHandler handler = new JsonHandler();
        JSONObject json;
        try {
            json = new JSONObject(handler.readJSONFromAsset("courses.json", SemesterDetailsActivity.this));
            try {
                JSONArray data = json.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    String restrictions;
                    String prereq;
                    try {
                        restrictions = convertJSONArrayToString(obj, "restrictions");
                    } catch (JSONException e) {
                        restrictions = "";
                    }
                    try {
                        prereq = convertJSONArrayToString(obj, "prereq");
                    } catch (JSONException e) {
                        prereq = "";
                    }
                    Course course = new Course(obj.getString("title"), obj.getString("code"), obj.getString("description"), restrictions, prereq, obj.getString("credits"));
                    courses.add(course);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return courses;

    }

    @Override
    public void onCourseSelectClick(int position) {
        Log.i(TAG, "onCourseClick: " + position);
        searchRv.setVisibility(View.GONE);
        courseRv.setVisibility(View.VISIBLE);
//        editText.setText(null);
        Log.i(TAG, "size: " + filteredCourses.size());




        if (!isDuplicate(addedCourses, filteredCourses.get(position))) {
            Log.i(TAG, "Same item detected");
            Course c = filteredCourses.get(position);
            c.setSemester(semester_id);

            appDb.courseDao().insertCourse(c);

            addedCourses.add(c);
            addedCourseAdapter.notifyDataSetChanged();
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
                    .setTitle("Duplicate Course")
                    .setMessage("You have already selected this course")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertBuilder.show();
        }



        editText.clearFocus();
    }

    public boolean isDuplicate(List<Course> toCheck, Course c) {

        for (int i = 0; i < toCheck.size(); i++) {
            String title;
            String code;

            title = toCheck.get(i).getTitle();
            code = toCheck.get(i).getCode();

            if (title.equals(c.getTitle()) && code.equals(c.getCode())) {
                return true;
            }
        }

        return false;

    }

    public String convertJSONArrayToString(JSONObject obj, String attribute) throws JSONException {

        JSONArray array = obj.getJSONArray(attribute);
        String s = array.toString();
        return s;

    }

    @Override
    public void onCourseAddClick(int position) {
        Log.i(TAG, "onCourseAddClick: " + position);
        Intent intent = new Intent(SemesterDetailsActivity.this, CourseDetailsActivity.class);
        intent.putExtra("semesterId", semester_id);
        intent.putExtra("courseTitle", addedCourses.get(position).getTitle());
        SemesterDetailsActivity.this.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                semester_id = data.getIntExtra("semesterId", 0);
            }
        }
    }
}
