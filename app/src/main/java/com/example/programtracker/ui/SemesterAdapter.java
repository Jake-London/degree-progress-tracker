package com.example.programtracker.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programtracker.R;
import com.example.programtracker.data.Course;
import com.example.programtracker.data.Semester;
import com.example.programtracker.data.SemesterInfo;

import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.ViewHolder> {

    private static final String TAG = "SemesterAdapter";
    private List<SemesterInfo> localDataSet;
    private OnSemesterListener mOnSemesterListener;

    public SemesterAdapter(List<SemesterInfo> dataset, OnSemesterListener onSemesterListener) {
        this.mOnSemesterListener = onSemesterListener;
        this.localDataSet = dataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new ViewHolder(view, mOnSemesterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SemesterInfo semester;
        semester = localDataSet.get(position);

        List<Course> courseList = semester.getCourseList();

//        holder.setIsRecyclable(false);
        holder.getTextView(R.id.main_semester).setText("Semester " + (position + 1));

                try {
                    Log.i(TAG, "onBindViewHolder: Success at position: " + position);
                    holder.getTextView(R.id.main_course_1).setText(semester.getCourseList().get(0).getTitle());
                    holder.getTextView(R.id.main_course_1).setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.i(TAG, "onBindViewHolder: Exception at position: " + position);
                    holder.getTextView(R.id.main_course_1).setVisibility(View.GONE);
                }

                try {
                    holder.getTextView(R.id.main_course_2).setText(semester.getCourseList().get(1).getTitle());
                    holder.getTextView(R.id.main_course_2).setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    holder.getTextView(R.id.main_course_2).setVisibility(View.GONE);
                }

                try {
                    holder.getTextView(R.id.main_course_3).setText(semester.getCourseList().get(2).getTitle());
                    holder.getTextView(R.id.main_course_3).setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    holder.getTextView(R.id.main_course_3).setVisibility(View.GONE);
                }

                try {
                    holder.getTextView(R.id.main_course_4).setText(semester.getCourseList().get(3).getTitle());
                    holder.getTextView(R.id.main_course_4).setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    holder.getTextView(R.id.main_course_4).setVisibility(View.GONE);
                }

                try {
                    holder.getTextView(R.id.main_course_5).setText(semester.getCourseList().get(4).getTitle());
                    holder.getTextView(R.id.main_course_5).setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    holder.getTextView(R.id.main_course_5).setVisibility(View.GONE);
                }



    }

    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private final TextView course1;
        private final TextView course2;
        private final TextView course3;
        private final TextView course4;
        private final TextView course5;
        OnSemesterListener onSemesterListener;

        public ViewHolder(View view, OnSemesterListener onSemesterListener) {
            super(view);

            textView = (TextView) view.findViewById(R.id.main_semester);
            course1 = (TextView) view.findViewById(R.id.main_course_1);
            course2 = (TextView) view.findViewById(R.id.main_course_2);
            course3 = (TextView) view.findViewById(R.id.main_course_3);
            course4 = (TextView) view.findViewById(R.id.main_course_4);
            course5 = (TextView) view.findViewById(R.id.main_course_5);
            this.onSemesterListener = onSemesterListener;

            view.setOnClickListener(this);
        }

        public TextView getTextView(int id) {
            switch(id) {
                case R.id.main_semester:
                    return textView;
                case R.id.main_course_1:
                    return course1;
                case R.id.main_course_2:
                    return course2;
                case R.id.main_course_3:
                    return course3;
                case R.id.main_course_4:
                    return course4;
                case R.id.main_course_5:
                    return course5;
            }
            return textView;
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: here");
            onSemesterListener.onSemesterClick(getAdapterPosition());
        }
    }

    public interface OnSemesterListener {
        void onSemesterClick(int position);
    }

}
