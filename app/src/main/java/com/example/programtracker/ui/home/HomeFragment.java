package com.example.programtracker.ui.home;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programtracker.R;
import com.example.programtracker.SemesterDetailsActivity;
import com.example.programtracker.data.AppDatabase;
import com.example.programtracker.data.Course;
import com.example.programtracker.data.Semester;
import com.example.programtracker.data.SemesterInfo;
import com.example.programtracker.ui.SemesterAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SemesterAdapter.OnSemesterListener {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    List<SemesterInfo> data = new ArrayList<>();
    SemesterAdapter adapter;
    AppDatabase appDb;

    RecyclerView rv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);



        rv = (RecyclerView) root.findViewById(R.id.course_rv);

        appDb = AppDatabase.getInstance(getActivity());
        List<Semester> semList = appDb.semesterDao().getSemesterList();
        int num_sem;
        if (!semList.isEmpty()) {
            num_sem = semList.size();
        } else {
            num_sem = 0;
        }

        data.addAll(getDataForAdapter(appDb, num_sem));

        adapter = new SemesterAdapter(data, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        Button btn = (Button) root.findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                appDb = AppDatabase.getInstance(getActivity());
                List<Semester> semList = appDb.semesterDao().getSemesterList();

                int num_sem;
                if (!semList.isEmpty()) {
                    num_sem = semList.get(semList.size() - 1).getId() + 1;
                } else {
                    num_sem = 1;
                }

                Semester semester = new Semester(num_sem, "Semester" + num_sem);
                appDb.semesterDao().insertSemester(semester);
                Log.i("HomeFragment", "Number of semesters: " + num_sem);

//                Course course = new Course("Introduction to Programming", "CIS*1500", 1);
//                appDb.courseDao().insertCourse(course);
//                course = new Course("Intermediate Programming", "CIS*2500", 1);
//                appDb.courseDao().insertCourse(course);
//                course = new Course("Data Structures", "CIS*2520", 1);
//                appDb.courseDao().insertCourse(course);
//                course = new Course("Operating Systems I", "CIS*3110", 1);
//                appDb.courseDao().insertCourse(course);
//                course = new Course("Compilers", "CIS*4650", 1);
//                appDb.courseDao().insertCourse(course);
//                course = new Course("Compilers", "CIS*4650", 2);
//                appDb.courseDao().insertCourse(course);
//                course = new Course("Compilers", "CIS*4650", 3);
//                appDb.courseDao().insertCourse(course);
//                course = new Course("Compilers", "CIS*4650", 4);
//                appDb.courseDao().insertCourse(course);


                data.clear();
                List<SemesterInfo> info = getDataForAdapter(appDb, semList.size() + 1);
                data.addAll(info);
                adapter.notifyDataSetChanged();

                rv.scrollToPosition(data.size() - 1);

//                appDb.courseDao().insertCourse(course);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                Toast.makeText(getActivity(), "Item Removed", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                data.remove(position);
                appDb.courseDao().deleteCoursesFromSemester(appDb.semesterDao().getSemesterList().get(position).getId());
                appDb.semesterDao().deleteSemester(appDb.semesterDao().getSemesterList().get(position));

//                data.clear();
//                List<SemesterInfo> info = getDataForAdapter(appDb, appDb.semesterDao().getSemesterList().size());
//                data.addAll(info);
                adapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public List<SemesterInfo> getDataForAdapter(AppDatabase appDb, int list_size) {
        List<SemesterInfo> semesterInfoList = new ArrayList<>();

        for (int i = 0; i < list_size; i++) {
            List<Course> courseList = appDb.courseDao().getCoursesFromSemester(appDb.semesterDao().getSemesterList().get(i).getId());

            SemesterInfo info = new SemesterInfo();

            if (!courseList.isEmpty()) {
                info.setCourseList(courseList);
            }
            info.setSemester(appDb.semesterDao().getSemesterList().get(i).getId());
            semesterInfoList.add(info);
        }
        return semesterInfoList;
    }

    @Override
    public void onSemesterClick(int position) {
        Log.i(TAG, "onSemesterClick: " + position);
        Intent intent = new Intent(getActivity(), SemesterDetailsActivity.class);
        intent.putExtra("semesterNum", position);
        intent.putExtra("semesterId", data.get(position).getSemester());
        startActivity(intent);
    }
}