package com.arnoldas.mathteacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;


import java.util.List;

public class ManageStudents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // hide the keyboard by default
        bind();
    }

    public void bind()
    {
        DatabaseHelper db = new DatabaseHelper(this);
        List<StudentDTO> students = db.GetStudentList();
        ((ListView) findViewById(R.id.studentListView)).setAdapter(new ManageStudentsAdapter(this,students));
    }

    public void addStudent(View view) {
        DatabaseHelper db = new DatabaseHelper(this);
        StudentDTO s = new StudentDTO();
        EditText newStudentText = (EditText) findViewById(R.id.newStudentText);
        s.name = newStudentText.getText().toString();
        s.testTime = 30;
        s.divisionLevel = 0;
        s.multiplicationLevel = 0;
        s.additionLevel = 1;
        s.subtractionLevel = 0;
        db.CreateStudent(s);
        newStudentText.setText("");
        bind();
    }
}
