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
        FloatingActionButton createEvent, listEvents;
        ImageView createaEvent, mapClick;
        ListView listView =  new ListView(this);
        RecyclerView recyclerView;


        String[] categories = getResources().getStringArray(R.array.categories);
        String url = "http://52.86.7.191:443/createEvent";
        recyclerView = findViewById(R.id.containerViewEvents);
        int minPeople = 3, maxpeople = 12;


        String urlView = "http://52.86.7.191:443/getEvent";

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
                                JSONObject jsonResponse = jsonObject.getJSONObject("response");

                                int categoryId = jsonResponse.getInt("event_filters_id");

                                String category = categories[categoryId-1];
                                String number_of_people = jsonResponse.getInt("event_number_of_people")+"/"+jsonResponse.getInt("event_total_number_of_people");
                                String event_name = jsonResponse.getString("event_name");

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
        listEvents = findViewById(R.id.list_events_user);
        mapClick = findViewById(R.id.map_press);

        mapClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEventActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        listEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEventActivity.this, UserEventListActivity.class);
                startActivity(intent);
            }
        });

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
        String urlView = "http://52.86.7.191:443/getEvent";
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
                                JSONObject jsonResponse = jsonObject.getJSONObject("response");
                                if(i == position){
                                    int categoryId = jsonResponse.getInt("event_filters_id");
                                    Log.e("HUINEA", jsonResponse.getString("event_name"));
                                    String category = categories[categoryId-1];
                                    String number_of_people = jsonResponse.getInt("event_number_of_people") + "/" + jsonResponse.getInt("event_total_number_of_people");
                                    String event_name = jsonResponse.getString("event_name");
                                    String event_date = jsonResponse.getString("event_date");
                                    String event_time = jsonResponse.getString("event_time");
                                    String event_place = jsonResponse.getString("event_place");
                                    String description = jsonResponse.getString("event_description");
//                                    String phone_number= jsonObject.getString("phone_number");

                                    intent.putExtra("event_name", event_name);
                                    intent.putExtra("event_date", event_date);
                                    intent.putExtra("event_time", event_time);
                                    intent.putExtra("event_place", event_place);
                                    intent.putExtra("event_filters_id", category);
                                    intent.putExtra("event_number_of_people", number_of_people);
                                    intent.putExtra("event_description", description);
//                                    intent.putExtra("phone_number",phone_number);
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