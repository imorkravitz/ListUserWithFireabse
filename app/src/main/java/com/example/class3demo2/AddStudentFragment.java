package com.example.class3demo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.Student;

public class AddStudentFragment extends Fragment {
    EditText nameEt;
    EditText idEt;
    CheckBox cb;
    Button saveBtn;
    Button cancelBtn;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_student,container, false);
        nameEt = view.findViewById(R.id.main_name_et);
        idEt = view.findViewById(R.id.main_id_et);
        cb = view.findViewById(R.id.main_cb);
        saveBtn = view.findViewById(R.id.main_save_btn);
        cancelBtn = view.findViewById(R.id.main_cancel_btn);
        progressBar = view.findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.GONE);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        return view;
    }


    private void save() {
        progressBar.setVisibility(View.VISIBLE);
        saveBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        String name = nameEt.getText().toString();
        String id = idEt.getText().toString();
        boolean flag = cb.isChecked();
        Log.d("TAG","saved name:" + name + " id:" + id + " flag:" + flag);
        Student student = new Student(name,id,flag);
        Model.instance.addStudent(student,()->{
        });
        Navigation.findNavController(nameEt).navigateUp();

    }
}