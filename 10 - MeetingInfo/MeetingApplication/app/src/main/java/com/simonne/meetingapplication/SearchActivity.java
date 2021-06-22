package com.simonne.meetingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {
    TextView textv;
    EditText key;
    DatePickerDialog datePickerDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        key=(EditText)findViewById(R.id.key);
        textv=(TextView)findViewById(R.id.textv2);
    }
    public void retrieve() {
        // Retrieve meeting records
        String URL = "content://com.simonne.meetingapplication.MyContentProvider";

        Uri meetinguri = Uri.parse(URL);
        String searchQuery = "date like '%" + key.getText().toString() + "%' ";


        Cursor c = getContentResolver().query(meetinguri, null, searchQuery, null, "time DESC");


        if (c.moveToFirst()) {
            textv.setText("MEETING DATA FOR THE DAY\n");
            do{

                textv.setText(textv.getText()+"\n"+"ID: "+c.getString(c.getColumnIndex(MyContentProvider.ID)) +
                        ", " +  "DATE: "+c.getString(c.getColumnIndex( MyContentProvider.DATE)) +
                        ", " +  "TIME: "+c.getString(c.getColumnIndex(MyContentProvider.TIME)) +
                        ", " + "AGENDA:" +c.getString(c.getColumnIndex( MyContentProvider.AGENDA)));
            } while (c.moveToNext());
        }
        else {
            Toast.makeText(this, "No events for the day!!", Toast.LENGTH_SHORT).show();
            textv.setText("No events for the day!!");
        }
    }

    public void findDate(View v1)
    {
        final Calendar c1=Calendar.getInstance();
        int cyear=c1.get(Calendar.YEAR);
        int cmonth=c1.get(Calendar.MONTH);
        int cday=c1.get(Calendar.DAY_OF_MONTH);
        datePickerDialog1=new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                key.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                retrieve();
            }
        },cyear,cmonth,cday);
        datePickerDialog1.show();
    }

}