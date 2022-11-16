package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ViewEventInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_information);

        TextView event_name, numberOfPeople,date_event,time_event,place_event,description_event;
        ImageView category_event;
        String cat;
        cat = getIntent().getStringExtra("category");
        Log.d("Result",cat);

        event_name = findViewById(R.id.event_name_eventinfo);
        numberOfPeople = findViewById(R.id.people_eventinfo);
        date_event = findViewById(R.id.date_eventinfo);
        time_event = findViewById(R.id.time_eventinfo);
        category_event = findViewById(R.id.category_eventinfo);
        place_event = findViewById(R.id.place_eventinfo);
        description_event = findViewById(R.id.description_eventinfo);

        event_name.setText(cat);
        numberOfPeople.setText(getIntent().getStringExtra("numberOfPeople"));
        date_event.setText(getIntent().getStringExtra("date"));
        time_event.setText(getIntent().getStringExtra("time"));
        place_event.setText(getIntent().getStringExtra("place"));
        description_event.setText(getIntent().getStringExtra("description"));


        if(cat.toLowerCase().equals("volleyball")){
            category_event.setImageResource(R.drawable.volleyball_imageview);
        }else if(cat.toLowerCase().equals("basketball")) {
            category_event.setImageResource(R.drawable.basketball_imageview);
        }else if(cat.toLowerCase().equals("football")){
            category_event.setImageResource(R.drawable.football_imageview);
        }else if(cat.toLowerCase().equals("soccer")) {
            category_event.setImageResource(R.drawable.soccer_imageview);
        }else if(cat.toLowerCase().equals("golf")){
            category_event.setImageResource(R.drawable.golf_imageview);
        }else if(cat.toLowerCase().equals("cycling")){
            category_event.setImageResource(R.drawable.cycling_imageview);
        }else if(cat.toLowerCase().equals("running")){
            category_event.setImageResource(R.drawable.running_imageview);
        }else if(cat.toLowerCase().equals("tennis")){
            category_event.setImageResource(R.drawable.tennis_imageview);
        }else if(cat.toLowerCase().equals("table tennis")){
            category_event.setImageResource(R.drawable.tennis_table_imageview);
        }else if(cat.toLowerCase().equals("checkers")){
            category_event.setImageResource(R.drawable.checkers_imageview);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}