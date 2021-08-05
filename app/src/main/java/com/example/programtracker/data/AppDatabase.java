package com.example.programtracker.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class, Semester.class}, exportSchema = false, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "app.db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract CourseDao courseDao();
    public abstract SemesterDao semesterDao();

}
