package com.simonne.meetingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText agenda, date, time;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agenda=(EditText)findViewById(R.id.agenda);
        date=(EditText)findViewById(R.id.date);
        time=(EditText)findViewById(R.id.time);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calender=Calendar.getInstance();
                int cal_year=calender.get(Calendar.YEAR);
                int cal_month=calender.get(Calendar.MONTH);
                int cal_day=calender.get(Calendar.DAY_OF_MONTH);

                datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },cal_year,cal_month,cal_day);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });
    }
    public void onClickAdd(View v){
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.DATE,date.getText().toString());
        values.put(MyContentProvider.TIME,time.getText().toString());
        values.put(MyContentProvider.AGENDA,agenda.getText().toString());

        Uri uri= getContentResolver().insert(MyContentProvider.CONTENT_URI,values);
        agenda.setText("");
        date.setText("");
        time.setText("");

        Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#ffffff'><strong>"
                + "Meeting Details Added" + "</strong></font>"), Toast.LENGTH_LONG);
        View view_toast = toast.getView();
        view_toast.setBackgroundResource(R.drawable.custom_background);
        toast.setGravity(Gravity.BOTTOM, 0, 30);
        toast.show();

    }

}