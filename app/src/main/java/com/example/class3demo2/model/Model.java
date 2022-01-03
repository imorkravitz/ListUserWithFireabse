package com.example.class3demo2.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public enum StudentListLoadingState{
        loading,
        loaded
    }
    MutableLiveData<StudentListLoadingState> studentListLoadingState = new MutableLiveData<StudentListLoadingState>();

    public LiveData<StudentListLoadingState> getStudentListLoadingState() {
        return studentListLoadingState;
    }



    ModelFirebase modelFirebase = new ModelFirebase();

    private Model(){
        studentListLoadingState.setValue(StudentListLoadingState.loaded);
    }

    MutableLiveData<List<Student>> studentsList = new MutableLiveData<List<Student>>();
    public LiveData<List<Student>> getAll(){
        if (studentsList.getValue() == null) { refreshStudentList(); };
        return  studentsList;
    }

    public void refreshStudentList(){
        studentListLoadingState.setValue(StudentListLoadingState.loading);

        // get last local update date



        // firebase get all updates since last local update date

        // add all records to the local db

        // remove

        // update last local update date

        // return all data to caller


        modelFirebase.getAllStudents(list -> {
            studentListLoadingState.setValue(StudentListLoadingState.loaded);
            studentsList.setValue(list);
        });
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
