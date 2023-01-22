package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserEventListActivity extends AppCompatActivity implements RecyclerViewInterface{
    String urlView = "http://52.86.7.191:443/getSortedEvent";
    boolean joined_or_created = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_list);
        RequestQueue queue = Volley.newRequestQueue(this);
        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayList<EventListElement> created_eventListElements = new ArrayList<>();
        ArrayList<EventListElement> joined_eventListElements = new ArrayList<>();
        RadioButton created_ev, joined_ev;

        created_ev = findViewById(R.id.created_ev);
        joined_ev = findViewById(R.id.joined_ev);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlView,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonResponse = jsonObject.getJSONObject("response");
                            String created_events_string = jsonResponse.getString("created_events");


                            JSONArray created_events_json = new JSONArray(created_events_string);

                            for(int i = 0; i < created_events_json.length(); i++){
                                JSONObject created_event_json = created_events_json.getJSONObject(i);

                                int categoryId = created_event_json.getInt("event_filters_id");

                                String category = categories[categoryId-1];
                                String number_of_people = created_event_json.getInt("event_number_of_people")+"/"+created_event_json.getInt("event_total_number_of_people");
                                String event_name = created_event_json.getString("event_name");
                                Log.e("Debug",created_event_json.toString());
                                created_eventListElements.add(new EventListElement(category.toLowerCase(),number_of_people,event_name));

                            }
                            RecyclerView recyclerView = findViewById(R.id.container2);
                            EventListAdapter eventListAdapter = new EventListAdapter(UserEventListActivity.this, created_eventListElements,UserEventListActivity.this);
                            recyclerView.setAdapter(eventListAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(UserEventListActivity.this));

                            String joined_events_string = jsonResponse.getString("joined_events");

                            JSONArray joined_events_json = new JSONArray(joined_events_string);

                            for(int i = 0; i < joined_events_json.length(); i++){
                                JSONObject joined_event_json = joined_events_json.getJSONObject(i);
                                Log.e("Debug",joined_event_json.toString());
                                int categoryId = joined_event_json.getInt("event_filters_id");

                                String category = categories[categoryId-1];
                                String number_of_people = joined_event_json.getInt("event_number_of_people")+"/"+joined_event_json.getInt("event_total_number_of_people");
                                String event_name = joined_event_json.getString("event_name");
                                Log.e("Debug",joined_event_json.toString());
                                joined_eventListElements.add(new EventListElement(category.toLowerCase(),number_of_people,event_name));
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

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", getIntent().getStringExtra("uid"));
//                params.put("userPassword", passwordFieldValue);
                return params;
            }
        };

        queue.add(stringRequest);


        joined_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView recyclerView = findViewById(R.id.container2);
                EventListAdapter eventListAdapter = new EventListAdapter(UserEventListActivity.this, joined_eventListElements,UserEventListActivity.this);
                recyclerView.setAdapter(eventListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(UserEventListActivity.this));
                joined_or_created = true;
                Log.e("Debug", "now true");
            }
        });
        created_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView recyclerView = findViewById(R.id.container2);
                EventListAdapter eventListAdapter = new EventListAdapter(UserEventListActivity.this, created_eventListElements,UserEventListActivity.this);
                recyclerView.setAdapter(eventListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(UserEventListActivity.this));
                joined_or_created = false;
                Log.e("Debug", "now false");
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        String urlView = "http://52.86.7.191:443/getSortedEvent";
        RequestQueue queue = Volley.newRequestQueue(this);
        String[] categories = getResources().getStringArray(R.array.categories);

        Intent intent = new Intent(UserEventListActivity.this, ViewEventInformation.class);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlView,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonResponse = jsonObject.getJSONObject("response");
                            String created_events_string = jsonResponse.getString("created_events");
                            String joined_events_string = jsonResponse.getString("joined_events");
                            Log.e("Debug", String.valueOf(joined_or_created));

                            if(joined_or_created == false){
                                JSONArray created_events_json = new JSONArray(created_events_string);
                                        JSONObject created_event_json = created_events_json.getJSONObject(position);


                                    int categoryId = created_event_json.getInt("event_filters_id");

                                    String category = categories[categoryId-1];
                                    String number_of_people = created_event_json.getInt("event_number_of_people")+"/"+created_event_json.getInt("event_total_number_of_people");
                                    String event_name = created_event_json.getString("event_name");
                                    String event_date = created_event_json.getString("event_date");
                                    String event_time = created_event_json.getString("event_time");
                                    String event_place = created_event_json.getString("event_place");
                                    String description = created_event_json.getString("event_description");
                                    String phone_number_owner = created_event_json.getString("event_phone_number");
                                    String event_id = created_event_json.getString("event_id");
                                    String event_longitude = created_event_json.getString("event_longitude");
                                    String event_latitude = created_event_json.getString("event_latitude");

                                    intent.putExtra("event_name", event_name);
                                    intent.putExtra("event_date", event_date);
                                    intent.putExtra("event_time", event_time);
                                    intent.putExtra("event_place", event_place);
                                    intent.putExtra("event_filters_id", category);
                                    intent.putExtra("event_number_of_people", number_of_people);
                                    intent.putExtra("event_description", description);
                                    intent.putExtra("phone_number",phone_number_owner);
                                    intent.putExtra("event_id",event_id);
                                    intent.putExtra("latitude",event_latitude);
                                    intent.putExtra("longitude",event_longitude);
                                    intent.putExtra("uid",getIntent().getStringExtra("uid"));


                                startActivityForResult(intent,1);



                            }else{
                                JSONArray created_events_json = new JSONArray(joined_events_string);
                                JSONObject created_event_json = created_events_json.getJSONObject(position);


                                int categoryId = created_event_json.getInt("event_filters_id");

                                String category = categories[categoryId-1];
                                String number_of_people = created_event_json.getInt("event_number_of_people")+"/"+created_event_json.getInt("event_total_number_of_people");
                                String event_name = created_event_json.getString("event_name");
                                String event_date = created_event_json.getString("event_date");
                                String event_time = created_event_json.getString("event_time");
                                String event_place = created_event_json.getString("event_place");
                                String description = created_event_json.getString("event_description");
                                String phone_number_owner = created_event_json.getString("event_phone_number");
                                String event_id = created_event_json.getString("event_id");
                                String event_longitude = created_event_json.getString("event_longitude");
                                String event_latitude = created_event_json.getString("event_latitude");

                                intent.putExtra("event_name", event_name);
                                intent.putExtra("event_date", event_date);
                                intent.putExtra("event_time", event_time);
                                intent.putExtra("event_place", event_place);
                                intent.putExtra("event_filters_id", category);
                                intent.putExtra("event_number_of_people", number_of_people);
                                intent.putExtra("event_description", description);
                                intent.putExtra("phone_number",phone_number_owner);
                                intent.putExtra("event_id",event_id);
                                intent.putExtra("latitude",event_latitude);
                                intent.putExtra("longitude",event_longitude);
                                intent.putExtra("uid",getIntent().getStringExtra("uid"));


                                startActivityForResult(intent,1);


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

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", getIntent().getStringExtra("uid"));
//                params.put("userPassword", passwordFieldValue);
                return params;
            }
        };

        queue.add(stringRequest);




    }
}
