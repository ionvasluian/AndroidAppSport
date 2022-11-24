package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ViewEventInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_information);

        TextView event_name, numberOfPeople,date_event,time_event,place_event,description_event;
        ImageView category_event, edit_event;
        String cat, name_of_event;
        cat = getIntent().getStringExtra("event_filters_id");
        name_of_event = getIntent().getStringExtra("event_name");
        Log.d("Result",cat);

        event_name = findViewById(R.id.event_name_eventinfo);
        numberOfPeople = findViewById(R.id.people_eventinfo);
        date_event = findViewById(R.id.date_eventinfo);
        time_event = findViewById(R.id.time_eventinfo);
        category_event = findViewById(R.id.category_eventinfo);
        place_event = findViewById(R.id.place_eventinfo);
        description_event = findViewById(R.id.description_eventinfo);
        edit_event = findViewById(R.id.edit_event);

        event_name.setText(cat);
        numberOfPeople.setText(getIntent().getStringExtra("event_number_of_people"));
        date_event.setText(getIntent().getStringExtra("event_date"));
        time_event.setText(getIntent().getStringExtra("event_time"));
        place_event.setText(getIntent().getStringExtra("event_place"));
        description_event.setText(getIntent().getStringExtra("event_description"));


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

        edit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEventInformation.this, CreateEventActivity.class);
                intent.putExtra("old_event", true);
                intent.putExtra("event_name", name_of_event);
                intent.putExtra("event_date", date_event.getText().toString());
                intent.putExtra("event_time", time_event.getText().toString());
                intent.putExtra("event_place",place_event.getText().toString());
                intent.putExtra("event_category",cat);
                intent.putExtra("event_number_of_people",numberOfPeople.getText().toString());
//                intent.putExtra("phone_number",getIntent().getStringExtra("phone_number"));
                intent.putExtra("event_description", description_event.getText().toString());
                startActivity(intent);

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}