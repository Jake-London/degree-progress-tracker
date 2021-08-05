package com.example.programtracker.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("Select * from course")
    List<Course> getCourseList();

    @Query("Select * from course where course_semester = :sem")
    List<Course> getCoursesFromSemester(int sem);

    @Query("Delete from course where course_semester = :sem_id")
    void deleteCoursesFromSemester(int sem_id);

    @Query("Delete from course where course_semester = :sem_id and course_title = :course_title")
    void deleteCourseFromSemester(int sem_id, String course_title);

    @Query("Select * from course where course_title = :course_title limit 1")
    List<Course> getCourse(String course_title);

    @Insert
    void insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

}
