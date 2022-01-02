package com.example.class3demo2.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFirebase modelFirebase = new ModelFirebase();

    private Model(){

    }

    public interface GetAllStudentsListener{
        void onComplete(List<Student> list);
    }
    public void getAllStudents(GetAllStudentsListener listener){
      modelFirebase.getAllStudents(listener);
    }

    public interface AddStudentListener{
        void onComplete();
    }
    public void addStudent(Student student, AddStudentListener listener){
       modelFirebase.addStudent( student,  listener);
    }
    public interface GetStudentById{
        void onComplete(Student student);
    }
    public Student getStudentById(String studentId, GetStudentById listener) {
        modelFirebase.getStudentById(studentId, listener);
        return null;
    }
}
