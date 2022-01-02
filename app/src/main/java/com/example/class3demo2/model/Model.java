package com.example.class3demo2.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    MutableLiveData<List<Student>> studentsList = new MutableLiveData<List<Student>>();
    public LiveData<List<Student>> getAll(){
        if (studentsList.getValue() == null) { refreshStudentList(); };
        return  studentsList;
    }

    public void refreshStudentList(){
        modelFirebase.getAllStudents(list -> studentsList.setValue(list));
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
