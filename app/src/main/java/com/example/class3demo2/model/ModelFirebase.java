package com.example.class3demo2.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // a singleton

    public ModelFirebase(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public interface GetAllStudentsListener{
        void onComplete(List<Student> list);
    }
    //TODO: fix..
    public void getAllStudents(Long lastUpdateDate, GetAllStudentsListener listener){
        // Create a new user with a first and last name
        db.collection(Student.COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdateDate,0))// adding filter
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Student> list = new LinkedList<Student>();
                        if (task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                            for (QueryDocumentSnapshot doc : querySnapshot){
                                Map<String, Object> json = doc.getData();
                                Student student = Student.create(json);
                                if (student!=null){
                                    list.add(student);
                                }
                            }
                        }
                        listener.onComplete(list);
                    }
                });
    }

    public void addStudent(Student student, Model.AddStudentListener listener) {
        Map<String, Object> json = student.toJson();

// Add a new document with a generated ID
        db.collection(Student.COLLECTION_NAME)
                .document(student.getId())
                .set(json)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void getStudentById(String studentId, Model.GetStudentById listener) {
        db.collection(Student.COLLECTION_NAME)
                .document(studentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Student student = null;
                        if (task.isSuccessful() & task.getResult()!=null){
                            student = Student.create(task.getResult().getData());
                        }
                        listener.onComplete(student);
                    }
                });
    }
}