package com.example.class3demo2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.Student;

import java.util.List;

public class StudentListRvViewModel extends ViewModel {
    LiveData <List<Student>> data;

    public StudentListRvViewModel(){
        data = Model.instance.getAll();
    }

    public LiveData <List<Student>> getData() {
        return data;
    }

}
