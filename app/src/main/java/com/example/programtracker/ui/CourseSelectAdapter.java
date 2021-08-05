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

import org.w3c.dom.Text;

import java.util.List;

public class CourseSelectAdapter extends RecyclerView.Adapter<CourseSelectAdapter.ViewHolder> {

    private static final String TAG = "CourseSelectAdapter";
    private List<Course> localDataSet;
    private OnCourseListener mOnCourseListener;

    public CourseSelectAdapter(List<Course> dataSet, OnCourseListener onCourseListener) {
        this.mOnCourseListener = onCourseListener;
        this.localDataSet = dataSet;
    }


    @NonNull
    @Override
    public CourseSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new ViewHolder(view, mOnCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseSelectAdapter.ViewHolder holder, int position) {
        Course course;
        course = localDataSet.get(position);

        holder.getTextView().setText( course.getCode() + " - " + course.getTitle());
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

        OnCourseListener onCourseListener;

        public ViewHolder(View view, OnCourseListener onCourseListener) {
            super(view);

            textView = (TextView) view.findViewById(R.id.main_semester);

            this.onCourseListener = onCourseListener;
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: here");
            onCourseListener.onCourseSelectClick(getAdapterPosition());
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public interface OnCourseListener {
        void onCourseSelectClick(int position);
    }

}
