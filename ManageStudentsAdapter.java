package com.arnoldas.mathteacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

class ManageStudentsAdapter extends BaseAdapter {

    private ManageStudents context;
    private List<StudentDTO> studentDTOs;

    private static LayoutInflater inflater=null;
    ManageStudentsAdapter(ManageStudents incomingContext, List<StudentDTO> students) {
        studentDTOs = students;
        context=incomingContext;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return studentDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setSubStudentVisibilities(View rowView, int vis) {
        rowView.findViewById(R.id.levelLabelLayout).setVisibility(vis);
        rowView.findViewById(R.id.levelLayout).setVisibility(vis);
        rowView.findViewById(R.id.timeLayout).setVisibility(vis);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rowView = inflater.inflate(R.layout.managestudententry, null);
        ((NumberPicker)rowView.findViewById(R.id.numberPicker)).setMinValue(5); // less than 5 doesn't make sense
        ((NumberPicker)rowView.findViewById(R.id.numberPicker)).setMaxValue(600); // 10 minutes ought to be enough

        ((TextView) rowView.findViewById(R.id.studentName)).setText(studentDTOs.get(position).name);
        setSubStudentVisibilities(rowView,View.GONE);
        setStudentInfoInUI(rowView, studentDTOs.get(position));
        rowView.findViewById(R.id.deleteButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.DeleteStudent(studentDTOs.get(position));
                        notifyDataSetInvalidated();
                        context.bind();
                    }
                }
        );
        rowView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newVis = rowView.findViewById(R.id.timeLayout).getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                        setSubStudentVisibilities(rowView,newVis);
                    }

        });
        SeekBar.OnSeekBarChangeListener ocl = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar view, int foo, boolean b) {
                getStudentInfoFromUI((View)(view.getParent().getParent()),studentDTOs.get(position));
            }

            @Override
            public void onStartTrackingTouch(SeekBar s) {}
            @Override
            public void onStopTrackingTouch(SeekBar s) {}
        };

        ((SeekBar)rowView.findViewById(R.id.addSeekBar)).setOnSeekBarChangeListener(ocl);
        ((SeekBar)rowView.findViewById(R.id.subSeekBar)).setOnSeekBarChangeListener(ocl);
        ((SeekBar)rowView.findViewById(R.id.multSeekBar)).setOnSeekBarChangeListener(ocl);
        ((SeekBar)rowView.findViewById(R.id.divSeekBar)).setOnSeekBarChangeListener(ocl);
        ((NumberPicker)rowView.findViewById(R.id.numberPicker)).setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                getStudentInfoFromUI((View)(numberPicker.getParent().getParent()),studentDTOs.get(position));
            }
        });

        return rowView;
    }

    private void setStudentInfoInUI(View rowView, StudentDTO dto) {
        ((SeekBar)rowView.findViewById(R.id.addSeekBar)).setProgress(dto.additionLevel);
        ((SeekBar)rowView.findViewById(R.id.subSeekBar)).setProgress(dto.subtractionLevel);
        ((SeekBar)rowView.findViewById(R.id.multSeekBar)).setProgress(dto.multiplicationLevel);
        ((SeekBar)rowView.findViewById(R.id.divSeekBar)).setProgress(dto.divisionLevel);
        ((NumberPicker)rowView.findViewById(R.id.numberPicker)).setValue(dto.testTime);
    }

    private void getStudentInfoFromUI(View rowView, StudentDTO dto) {
        dto.additionLevel = ((SeekBar)rowView.findViewById(R.id.addSeekBar)).getProgress();
        dto.subtractionLevel = ((SeekBar)rowView.findViewById(R.id.subSeekBar)).getProgress();
        dto.multiplicationLevel = ((SeekBar)rowView.findViewById(R.id.multSeekBar)).getProgress();
        dto.divisionLevel = ((SeekBar)rowView.findViewById(R.id.divSeekBar)).getProgress();
        dto.testTime = ((NumberPicker)rowView.findViewById(R.id.numberPicker)).getValue();

        DatabaseHelper db = new DatabaseHelper(context);
        db.UpdateStudent(dto);
    }
}
