package com.simonne.medicinereminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Medicine extends AppCompatActivity {

    EditText name, date, frequency, type, dosage, time;
    Button insert;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    AlarmManager alarmManager;
    DatabaseHelper databaseHelper;
    boolean status;
    int day, month, year, hour, minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        name = (EditText)findViewById(R.id.pillName);
        date = (EditText)findViewById(R.id.date);
        frequency = (EditText)findViewById(R.id.freq);
        type = (EditText)findViewById(R.id.type);
        dosage = (EditText)findViewById(R.id.dosage);
        time = (EditText)findViewById(R.id.time);
        insert = (Button) findViewById(R.id.insert);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        databaseHelper = new DatabaseHelper(this);

        insertData();
        addDate();
        addTime();
    }

    private void addTime() {
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c2 = Calendar.getInstance();
                int chour = c2.get(Calendar.HOUR_OF_DAY);
                int cmin = c2.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(Medicine.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                        hour = hourOfDay;
                        minutes = minute;
                    }
                }, chour, cmin, false);
                timePickerDialog.show();
            }
        });
    }

    private void addDate() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int cyear = calendar.get(Calendar.YEAR);
                int cmonth = calendar.get(Calendar.MONTH);
                int cday = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Medicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int lyear, int lmonth, int dayOfMonth) {
                        year = lyear;
                        month = lmonth;
                        year = dayOfMonth;
                        date.setText(dayOfMonth + "/" + (month) + "/" + year);
                    }
                }, cyear, cmonth, cday);
                datePickerDialog.show();
            }
        });
    }

    private void insertData() {
        insert.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String lname, ldate, ltime, lfreq, ltype, ldosage;

                lname = name.getText().toString();
                ldate = date.getText().toString();
                ltime = time.getText().toString();
                lfreq = time.getText().toString();
                ltype = time.getText().toString();
                ldosage = time.getText().toString();

                //databaseHelper.delete();
                status = databaseHelper.insertData(lname, ldate, ltime, lfreq, ltype, ldosage);

                if(status == true){
                    Toast toast = Toast.makeText(Medicine.this, Html.fromHtml("<font color='#883A55'><strong>"
                            + "Record is added" + "</strong></font>"), Toast.LENGTH_LONG);
                    View view_toast = toast.getView();
                    view_toast.setBackgroundResource(R.drawable.custom_background);
                    toast.setGravity(Gravity.BOTTOM, 0, 80);
                    toast.show();

                    name.setText("");
                    time.setText("");
                    date.setText("");
                    dosage.setText("");
                    frequency.setText("");
                    type.setText("");

                    addAlarm();
                }
                else{
                    Toast.makeText(Medicine.this, "Error. Record not added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addAlarm(){
        Toast toast = Toast.makeText(Medicine.this, Html.fromHtml("<font color='#883A55'><strong>"
                + "Alarm has been set" + "</strong></font>"), Toast.LENGTH_SHORT);
        View view_toast = toast.getView();
        view_toast.setBackgroundResource(R.drawable.custom_background);
        toast.show();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minutes);
        Intent intent = new Intent(Medicine.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);


        for (int i=0; i < 10; i++)
        {
            Toast toast_message = Toast.makeText(getApplicationContext(), "Time for medicine", Toast.LENGTH_LONG);
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.screenshot);
            toast.setGravity(Gravity.TOP, 0, 800);
            toast.setView(view);
            toast.show();
        }
    }

    public void deleteRecords(View view) {
        databaseHelper.delete();
    }
}