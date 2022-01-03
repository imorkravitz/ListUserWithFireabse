package com.example.class3demo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.Student;

public class StudentDetailsFragment extends Fragment {
    TextView nameTv;
    TextView idTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_details, container, false);

        String stId = StudentDetailsFragmentArgs.fromBundle(getArguments()).getStudentId();
        Model.instance.getStudentById(stId, new Model.GetStudentById() {
            @Override
            public void onComplete(Student student) {
                nameTv.setText(student.getName());
                idTv.setText(student.getId());
            }
        });

        nameTv = view.findViewById(R.id.details_name_tv);
        idTv = view.findViewById(R.id.details_id_tv);

        Button backBtn = view.findViewById(R.id.details_back_btn);
        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }
}