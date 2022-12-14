package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {
    private int mYear, mMonth, mDay, mHour, mMinute;
    final Calendar calendar = Calendar.getInstance();

    EditText nameCreateEvent;
    EditText dateCreateEvent;
    EditText timeCreateEvent;
    EditText placeCreateEvent;
    EditText minPeopleCreateEvent;
    EditText maxPeopleCreateEvent;
    EditText descriptionCreateEvent;
    ImageView backButton;
    Button createEventButton;
    Spinner category;

    String eventNameFieldValue;
    String eventDateFieldValue;
    String eventTimeFieldValue;
    String eventPlaceFieldValue;
    String eventCategoryFieldValue;
    String eventNumberOfPeopleFieldValue;
    String eventTotalNumberOfPeopleFieldValue;
    String eventDescriptionFieldValue;
    String eventOwnerValue = "NoName";

    String url = "http://52.86.7.191:443/createEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);
        nameCreateEvent        = findViewById(R.id.event_name_createevent);
        dateCreateEvent        = findViewById(R.id.date_createevent);
        timeCreateEvent        = findViewById(R.id.time_createevent);
        placeCreateEvent       = findViewById(R.id.place_createevent);
        minPeopleCreateEvent   = findViewById(R.id.initial_people_createevent);
        maxPeopleCreateEvent   = findViewById(R.id.max_people_createevent);
        descriptionCreateEvent = findViewById(R.id.description_createevent);
        backButton             = findViewById(R.id.back_createevent);
        category               = findViewById(R.id.category_createevent);
        createEventButton      = findViewById(R.id.createEventButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories,
                R.layout.spinner_style
        );
        adapter.setDropDownViewResource(R.layout.spinner_style);
        category.setAdapter(adapter);

        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateDateOnLabel();
            }
        };

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateEventActivity.this, ViewEventActivity.class);
                startActivity(intent);
            }
        });

        nameCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                eventNameFieldValue = nameCreateEvent.getText().toString();
            }
        });

        dateCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(
                        CreateEventActivity.this,
                        R.style.DialogTheme,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        timeCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        view.getContext(),
                        R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeCreateEvent.setText(FormatTime(String.format("   %d:%d", hourOfDay, minute)));
                                eventTimeFieldValue = timeCreateEvent.getText().toString().trim();
                            }
                        },
                        mHour,
                        mMinute,
                        false
                );
                timePickerDialog.show();
            }
        });

        placeCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                eventPlaceFieldValue = placeCreateEvent.getText().toString();
            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                eventCategoryFieldValue = adapterView.getItemAtPosition(position).toString().trim();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        minPeopleCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                eventNumberOfPeopleFieldValue = minPeopleCreateEvent.getText().toString();
            }
        });

        maxPeopleCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                eventTotalNumberOfPeopleFieldValue = maxPeopleCreateEvent.getText().toString();
            }
        });

        descriptionCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                eventDescriptionFieldValue = descriptionCreateEvent.getText().toString();
            }
        });

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this, R.style.AlertDialogTheme);
                View view1 = LayoutInflater.from(CreateEventActivity.this).inflate(R.layout.success_alert_dialog_layout, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                builder.setView(view1);
                String alertMessage = checkFields(view);
                if(alertMessage.equals("All Completed"))
                {
                    RequestQueue queue = Volley.newRequestQueue(CreateEventActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    final AlertDialog success_dialog = builder.create();
                                    success_dialog.show();
                                    if (success_dialog.getWindow() != null){
                                        success_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                    }
                                    view1.findViewById(R.id.okayButton).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            success_dialog.dismiss();
                                            Intent intent = new Intent(CreateEventActivity.this, ViewEventActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    Log.e("Works", response);
//                                Toast.makeText(
//                                        getApplicationContext(),
//                                        response,
//                                        Toast.LENGTH_SHORT
//                                ).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            ((ImageView) view1.findViewById(R.id.successImageView)).setBackground(ContextCompat.getDrawable(CreateEventActivity.this, R.drawable.error_imgview));
                            ((TextView) view1.findViewById(R.id.statusAlertDialog)).setText("Error!");
                            ((TextView) view1.findViewById(R.id.alertDialogMessage)).setText(String.valueOf(error));

                            final AlertDialog success_dialog = builder.create();
                            success_dialog.show();


                            if (success_dialog.getWindow() != null){
                                success_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }

                            view1.findViewById(R.id.okayButton).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    success_dialog.dismiss();
                                }
                            });


                            Log.e("Works", String.valueOf(error));
//                            Toast.makeText(
//                                    getApplicationContext(),
//                                    (CharSequence) error,
//                                    Toast.LENGTH_LONG
//                            ).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            eventNameFieldValue = nameCreateEvent.getText().toString().trim();
                            eventPlaceFieldValue = placeCreateEvent.getText().toString().trim();
                            eventNumberOfPeopleFieldValue = minPeopleCreateEvent.getText().toString().trim();
                            eventTotalNumberOfPeopleFieldValue = maxPeopleCreateEvent.getText().toString().trim();
                            eventDescriptionFieldValue = descriptionCreateEvent.getText().toString().trim();


                            params.put("name", eventNameFieldValue);
                            params.put("date", eventDateFieldValue);
                            params.put("time", FormatTime(eventTimeFieldValue));
                            params.put("place", eventPlaceFieldValue);
                            params.put("category", eventCategoryFieldValue);
                            params.put("numberOfPeople", eventNumberOfPeopleFieldValue);
                            params.put("totalNumberOfPeople", eventTotalNumberOfPeopleFieldValue);
                            params.put("description", eventDescriptionFieldValue);
                            params.put("owner", eventOwnerValue);

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
                else
                {
                    ((ImageView) view1.findViewById(R.id.successImageView)).setBackground(ContextCompat.getDrawable(CreateEventActivity.this, R.drawable.error_imgview));
                    ((TextView) view1.findViewById(R.id.statusAlertDialog)).setText("Error!");
                    ((TextView) view1.findViewById(R.id.alertDialogMessage)).setText(alertMessage);
                    final AlertDialog success_dialog = builder.create();
                    success_dialog.show();
                    if (success_dialog.getWindow() != null){
                        success_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    view1.findViewById(R.id.okayButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            success_dialog.dismiss();
                        }
                    });
                }


            }
        });
    }
    private String checkFields(View view){
        nameCreateEvent        = findViewById(R.id.event_name_createevent);
        dateCreateEvent        = findViewById(R.id.date_createevent);
        timeCreateEvent        = findViewById(R.id.time_createevent);
        placeCreateEvent       = findViewById(R.id.place_createevent);
        minPeopleCreateEvent   = findViewById(R.id.initial_people_createevent);
        maxPeopleCreateEvent   = findViewById(R.id.max_people_createevent);
        descriptionCreateEvent = findViewById(R.id.description_createevent);

        if(nameCreateEvent.getText().toString().equals("")){
            return "Please Complete the \n\"Event Name\" Field";
        }
        if(dateCreateEvent.getText().toString().equals("")){
            return "Please Complete the \n\"Date\" Field";
        }
        if(timeCreateEvent.getText().toString().equals("")){
            return "Please Complete the \n\"Time\" Field";
        }
        if(placeCreateEvent.getText().toString().equals("")){
            return "Please Complete the \n\"Place\" Field";
        }
        if(minPeopleCreateEvent.getText().toString().equals("")){
            return "Please Complete the \n\"No.\" Field";
        }
        if(maxPeopleCreateEvent.getText().toString().equals("")){
            return "Please Complete the \n\"Max\" Field";
        }
        if(Integer.parseInt(maxPeopleCreateEvent.getText().toString())<= Integer.parseInt(minPeopleCreateEvent.getText().toString())){
            return "No. Should be lower \nthan Max";
        }
        return "All Completed";
    };

    private void updateDateOnLabel(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateCreateEvent.setText(String.format("    %s", dateFormat.format(calendar.getTime())));
        eventDateFieldValue = dateCreateEvent.getText().toString().trim();
        Log.e("Date", eventDateFieldValue);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreateEventActivity.this, ViewEventActivity.class);
        startActivity(intent);
    }

    private String FormatTime(String time) {
        String hours = time.split(":")[0];
        String minutes = time.split(":")[1];

        if (hours.length() < 2) {
            hours = "0" + hours;
        }
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }

        return hours + ":" + minutes;
    }
}