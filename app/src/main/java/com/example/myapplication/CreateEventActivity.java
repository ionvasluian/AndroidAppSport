package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText, timeCreateEvent, nameCreateEvent, placeCreateEvent, minPeopleCreateEvent, maxPeopleCreateEvent, descriptionCreateEvent;
    String txtTime = null;
    ImageView backButton;
    Button createEventButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner category;
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://52.86.7.191:443/createEvent";
        setContentView(R.layout.activity_create_event);
        editText = findViewById(R.id.date_createevent);
        timeCreateEvent = findViewById(R.id.time_createevent);
        nameCreateEvent = findViewById(R.id.event_name_createevent);
        placeCreateEvent = findViewById(R.id.place_createevent);
        minPeopleCreateEvent = findViewById(R.id.initial_people_createevent);
        maxPeopleCreateEvent = findViewById(R.id.max_people_createevent);
        descriptionCreateEvent = findViewById(R.id.description_createevent);
        backButton = findViewById(R.id.back_createevent);
        category = findViewById(R.id.category_createevent);
        createEventButton = findViewById(R.id.createEventButton);

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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateEventActivity.this, ViewEventActivity.class);
                startActivity(intent);
            }
        });

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

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Works","Entered");
                RequestQueue queue = Volley.newRequestQueue(CreateEventActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.e("Works", response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            Log.e("Works", "Not Works");
                        }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // below line we are creating a map for
                        // storing our values in key and value pair.
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", "volleyball go");
                        params.put("date", "2022-09-30");
                        params.put("time", "21:00");
                        params.put("place", "Undeva departe");
                        params.put("category", "Volleyball");
                        params.put("numberOfPeople","4");
                        params.put("totalNumberOfPeople","12");
                        params.put("description", "test");
                        params.put("owner","tester");
//                        // on below line we are passing our key
//                        // and value pair to our parameters.
//                        params.put("name", name);
//                        params.put("job", job);
//                        // at last we are
//                        // returning our params.
                        return params;
                    }
                };
                // below line is to make
                // a json object request.
                queue.add(stringRequest);
            }


        });
    }
    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText("    "+dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreateEventActivity.this, ViewEventActivity.class);
        startActivity(intent);
    }
}