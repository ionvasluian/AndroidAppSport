package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class ViewEventActivity extends AppCompatActivity implements RecyclerViewInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Context context = getBaseContext();
        FloatingActionButton createEvent;
        ImageView createaEvent;
        ListView listView =  new ListView(this);
        RecyclerView recyclerView;


        String[] categories = getResources().getStringArray(R.array.categories);
        String url = "http://52.86.7.191:443/createEvent";
        recyclerView = findViewById(R.id.containerViewEvents);
        int minPeople = 3, maxpeople = 12;


        String urlView = "http://52.86.7.191:443/getEvents";

        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        ArrayList<EventListElement> eventListElements = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlView,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int categoryId = jsonObject.getInt("FiltersId");

                                String category = categories[categoryId];
                                String number_of_people = jsonObject.getInt("numberOfPeople")+"/"+jsonObject.getInt("totalNumberOfPeople");
                                String event_name = jsonObject.getString("name");

                                eventListElements.add(new EventListElement(category.toLowerCase(),number_of_people,event_name));
                                Log.e("Response",category+" , "+number_of_people+ " , "+event_name);
                            }
                            RecyclerView recyclerView = findViewById(R.id.containerViewEvents);
                            EventListAdapter eventListAdapter = new EventListAdapter(ViewEventActivity.this, eventListElements,ViewEventActivity.this);
                            recyclerView.setAdapter(eventListAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ViewEventActivity.this));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response","That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


//        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
//        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));
//        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
//        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));
//        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
//        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));
//        eventListElements.add(new EventListElement("volleyball","8/12","Let's go volleyball"));
//        eventListElements.add(new EventListElement("volleyball", "3/12","Hehehe"));



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

    @Override
    public void onItemClick(int position) {
        String urlView = "http://52.86.7.191:443/getEvents";
        RequestQueue queue = Volley.newRequestQueue(this);
        String[] categories = getResources().getStringArray(R.array.categories);

        Intent intent = new Intent(ViewEventActivity.this, ViewEventInformation.class);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlView,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(i == position){
                                    int categoryId = jsonObject.getInt("FiltersId");

                                    String category = categories[categoryId];
                                    String number_of_people = jsonObject.getInt("numberOfPeople") + "/" + jsonObject.getInt("totalNumberOfPeople");
                                    String event_name = jsonObject.getString("name");
                                    String event_date = jsonObject.getString("date");
                                    String event_time = jsonObject.getString("time");
                                    String event_place = jsonObject.getString("place");
                                    String description = jsonObject.getString("description");

                                    intent.putExtra("name", event_name);
                                    intent.putExtra("date", event_date);
                                    intent.putExtra("time", event_time);
                                    intent.putExtra("place", event_place);
                                    intent.putExtra("category", category);
                                    intent.putExtra("numberOfPeople", number_of_people);
                                    intent.putExtra("description", description);
                                    startActivityForResult(intent,1);
                                    Log.e("Entered", category + " , " + number_of_people + " , " + event_name);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response","That didn't work!");
            }
        });

        queue.add(stringRequest);



    }
}