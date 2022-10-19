package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        FloatingActionButton createEvent;
        ImageView createaEvent;
        ListView listView =  new ListView(this);

        String[] categories = {"volleyball"};
        String name = "Let's gooooo!";
        int minPeople = 3, maxpeople = 12;
        ArrayList<EventListElement> eventListElements = new ArrayList<>();

        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));
        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));
        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));
        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));

        RecyclerView recyclerView = findViewById(R.id.containerViewEvents);
        EventListAdapter eventListAdapter = new EventListAdapter(this, eventListElements);
        recyclerView.setAdapter(eventListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ArrayAdapter<ViewEventElementActivity> viewEventElements = new ArrayAdapter<ViewEventElementActivity>(this, R.layout.activity_view_event_element,R.id.containerViewEvents);
//        setContentView(listView);
//        listView.setAdapter(viewEventElements);




        createEvent = findViewById(R.id.add_fab);
        createaEvent = findViewById(R.id.back_event_list);

        createaEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEventActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEventActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewEventActivity.this, MainActivity.class);
        startActivity(intent);
    }
}