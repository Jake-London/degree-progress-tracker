package com.example.programtracker.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programtracker.data.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private static final String TAG = "CourseAdapter";
    private List<Course> localDataSet;
    private OnCourseListener mOnCourseListener;

    public CourseAdapter(List<Course> dataSet, OnCourseListener onCourseListener) {
        this.mOnCourseListener = onCourseListener;
        this.localDataSet = dataSet;
    }


    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnCourseListener onCourseListener;

        public ViewHolder(View view, OnCourseListener onCourseListener) {
            super(view);
            this.onCourseListener = onCourseListener;
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }

    public interface OnCourseListener {
        void onCourseClick(int position);
    }

}
