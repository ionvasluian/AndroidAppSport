package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText, timeCreateEvent, nameCreateEvent, placeCreateEvent, minPeopleCreateEvent, maxPeopleCreateEvent, descriptionCreateEvent;
    String txtTime = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner category;

        setContentView(R.layout.activity_create_event);
        editText = findViewById(R.id.date_createevent);
        timeCreateEvent = findViewById(R.id.time_createevent);
        nameCreateEvent = findViewById(R.id.event_name_createevent);
        placeCreateEvent = findViewById(R.id.place_createevent);
        minPeopleCreateEvent = findViewById(R.id.initial_people_createevent);
        maxPeopleCreateEvent = findViewById(R.id.max_people_createevent);
        descriptionCreateEvent = findViewById(R.id.description_createevent);
        category = findViewById(R.id.category_createevent);

//        nameCreateEvent.setTextColor(Color.argb(100,114,114,114));
//        placeCreateEvent.setTextColor(Color.argb(100,114,114,114));
//        minPeopleCreateEvent.setTextColor(Color.argb(100,114,114,114));
//        maxPeopleCreateEvent.setTextColor(Color.argb(100,114,114,114));
//        descriptionCreateEvent.setTextColor(Color.argb(100,114,114,114));
//        editText.setTextColor(Color.argb(100,114,114,114));
//        timeCreateEvent.setTextColor(Color.argb(100,114,114,114));



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, R.layout.spinner_style);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_style);
        //android.R.layout.simple_spinner_dropdown_item
        category.setAdapter(adapter);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay, mHour, mMinute;
                new DatePickerDialog(CreateEventActivity.this,R.style.DialogTheme,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);


            }
        });
        timeCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeCreateEvent.setText("   "+hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText("    "+dateFormat.format(myCalendar.getTime()));
    }
}