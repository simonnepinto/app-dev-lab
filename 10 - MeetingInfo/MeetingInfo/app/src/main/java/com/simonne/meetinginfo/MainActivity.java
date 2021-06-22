package com.simonne.meetinginfo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button deleteButton;
    TextView textv;
    EditText key;
    DatePickerDialog datePickerDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteButton = (Button)findViewById(R.id.deleteButton);
        key = (EditText)findViewById(R.id.key);
        textv = (TextView)findViewById(R.id.textv2);
    }

    public void findDate(View view){
        final Calendar calendar=Calendar.getInstance();
        int cal_year=calendar.get(Calendar.YEAR);
        int cal_month=calendar.get(Calendar.MONTH);
        int cal_day=calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog1=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                key.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                retrieve();
            }
        },cal_year,cal_month,cal_day);
        datePickerDialog1.show();
    }

    @SuppressLint("SetTextI18n")
    public void retrieve() {
        deleteButton.setVisibility(View.VISIBLE);
        // Retrieve meeting records
        String URL = "content://com.simonne.meetingapplication.MyContentProvider";

        Uri meetinguri = Uri.parse(URL);
        String searchQuery = "date like '%" + key.getText().toString() + "%' ";

        Cursor c = getContentResolver().query(meetinguri, null, searchQuery, null, "time DESC");

        if (c.moveToFirst()) {
            textv.setText("MEETING DATA FOR THE DAY\n");
            do{
                textv.setText(textv.getText()+"\n\n"+"MEETING ID : "+c.getString(c.getColumnIndex(MyContentProvider.ID)) +
                        "\n" +  "DATE OF THE MEETING : "+c.getString(c.getColumnIndex( MyContentProvider.DATE)) +
                        "\n" +  "TIME OF THE MEETING : "+c.getString(c.getColumnIndex(MyContentProvider.TIME)) +
                        "\n" + "AGENDA OF THE MEETING :" +c.getString(c.getColumnIndex( MyContentProvider.AGENDA)));
            } while (c.moveToNext());
        }
        else {
            deleteButton.setVisibility(View.GONE);
            textv.setText("");
            Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#000000'><strong>"
                    + "No meetings scheduled for the day!!" + "</strong></font>"), Toast.LENGTH_LONG);
            View view_toast = toast.getView();
            view_toast.setBackgroundResource(R.drawable.custom_background);
            toast.setGravity(Gravity.BOTTOM, 0, 80);
            toast.show();
        }
    }

    public void deleteMeetingDetails(View view) {
        // Retrieve meeting records
        String URL = "content://com.simonne.meetingapplication.MyContentProvider";

        Uri meetinguri = Uri.parse(URL);
        String searchQuery = "date like '%" + key.getText().toString() + "%' ";

        int current = getContentResolver().delete(meetinguri, searchQuery, null);

        Toast toast;
        if(current > 0){
            toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#ffffff'><strong>"
                    + "Deleted the meeting records" + "</strong></font>"), Toast.LENGTH_LONG);
            View view_toast = toast.getView();
            view_toast.setBackgroundResource(R.drawable.custom_background);
        }
        else {
            toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#ffffff'><strong>"
                    + "Could not delete the records" + "</strong></font>"), Toast.LENGTH_LONG);
            View view_toast = toast.getView();
            view_toast.setBackgroundResource(R.drawable.custom_background);
        }
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        toast.show();
    }
}