package com.example.catchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Course {

    private String courseID;
    private String coutseInfo;
    private String StudentID;

    public Course(String courseID, String coutseInfo, String studentID) {
        this.courseID = courseID;
        this.coutseInfo = coutseInfo;
        StudentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCoutseInfo() {
        return coutseInfo;
    }

    public void setCoutseInfo(String coutseInfo) {
        this.coutseInfo = coutseInfo;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }
}


