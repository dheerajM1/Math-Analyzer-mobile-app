package com.arnoldas.mathteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectRole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
    }
    public void goToStudent(View view)
    {
        Intent studentPage = new Intent(SelectRole.this, MainActivity.class);
        startActivity(studentPage);

    }
    public void goToTeacher(View view)
    {
        Intent teacherPage = new Intent(SelectRole.this, TeacherPage.class);
        startActivity(teacherPage);
    }
    public void goToParent(View view)
    {
        Intent parentPage = new Intent(SelectRole.this, ParentPage.class);
        startActivity(parentPage);

    }
}
