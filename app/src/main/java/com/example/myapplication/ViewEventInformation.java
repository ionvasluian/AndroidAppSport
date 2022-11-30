package com.example.myapplication;

import static com.example.myapplication.MainActivity.PREFS_NAME;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ViewEventInformation extends AppCompatActivity {
    boolean is_owner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_information);
        String user_id = getIntent().getStringExtra("uid");
        TextView event_name, numberOfPeople,date_event,time_event,place_event,description_event,name_ev,map_view;
        ImageView category_event, edit_event;
        String cat, name_of_event;
        Button joinEvent;
        String url = "http://52.86.7.191:443/joinUserById";
        String joined_events = "http://52.86.7.191:443/getSeparateEvent";
        String j_event = "http://52.86.7.191:443/getUserInfoById?id="+user_id;
        cat = getIntent().getStringExtra("event_filters_id");
        name_of_event = getIntent().getStringExtra("event_name");
        RequestQueue queue = Volley.newRequestQueue(this);

        map_view = findViewById(R.id.map_view_event);
        name_ev = findViewById(R.id.name_of_event);
        event_name = findViewById(R.id.event_name_eventinfo);
        numberOfPeople = findViewById(R.id.people_eventinfo);
        date_event = findViewById(R.id.date_eventinfo);
        time_event = findViewById(R.id.time_eventinfo);
        category_event = findViewById(R.id.category_eventinfo);
        place_event = findViewById(R.id.place_eventinfo);
        description_event = findViewById(R.id.description_eventinfo);
        edit_event = findViewById(R.id.edit_event);
        joinEvent = findViewById(R.id.joinEvent);


//        joinEvent.setVisibility(View.INVISIBLE);
        name_ev.setText(name_of_event);
        event_name.setText(cat);
        numberOfPeople.setText(getIntent().getStringExtra("event_number_of_people"));
        date_event.setText(getIntent().getStringExtra("event_date"));
        time_event.setText(getIntent().getStringExtra("event_time"));
        place_event.setText(getIntent().getStringExtra("event_place"));
        description_event.setText(getIntent().getStringExtra("event_description"));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, j_event,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("Debugging", jsonObject.toString());
                            JSONObject jsonResponse = jsonObject.getJSONObject("response");


                            String jsonArrayString = jsonResponse.getString("user_joined_events_ids");
                            Log.e("jsonar", jsonArrayString);
                            String string[] = jsonArrayString.replaceAll(" ", "").replaceAll("\\[","").replaceAll("\\]","").split(",");
                            for(int i = 0; i < string.length; i++) {
                                Log.e("er", string[i]);
                                if (getIntent().getStringExtra("event_id").equals(string[i])) {
                                    joinEvent.setVisibility(View.GONE);
                                }
                            }






                        } catch (JSONException ex) {
                            Log.e("error", ex.toString());
                            ex.printStackTrace();
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

         stringRequest = new StringRequest(Request.Method.POST, joined_events,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("Debugging", jsonObject.toString());
                            JSONObject jsonResponse = jsonObject.getJSONObject("response");


                            String jsonArrayString = jsonResponse.getString("owned_events");

                            JSONArray jsonArray = new JSONArray(jsonArrayString);

                            for(int i = 0; i < jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                String ev_id = object.getString("event_id");
                                if(ev_id.equals(getIntent().getStringExtra("event_id"))){
                                    String event_id = object.getString("event_owner_id");
                                    Log.e("event_id", event_id);
                                    if(event_id.equals(user_id)==false){
                                        Log.e("entered", "e");

                                        edit_event.setVisibility(View.GONE);
                                    }
                                    if(event_id.equals(user_id)){
                                        is_owner = true;
                                        joinEvent.setVisibility(View.GONE);
                                    }

                                }



                            }

                            jsonArrayString = jsonResponse.getString("not_owned_events");

                            jsonArray = new JSONArray(jsonArrayString);

                            for(int i = 0; i < jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                String ev_id = object.getString("event_id");
                                if(ev_id.equals(getIntent().getStringExtra("event_id"))){
                                    String event_id = object.getString("event_owner_id");
                                    Log.e("event_id", event_id);
                                    if(event_id.equals(user_id)==false){
                                        Log.e("entered", "e");

                                        edit_event.setVisibility(View.GONE);
                                    }

                                }



                            }


                        } catch (JSONException ex) {
                            Log.e("error", ex.toString());
                            ex.printStackTrace();
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
                params.put("userId", user_id);

                return params;
            }
        };

        queue.add(stringRequest);


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

        joinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(
                                        view.getContext(),
                                        error.toString(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userId", user_id);
                        params.put("eventId", getIntent().getStringExtra("event_id"));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });

        map_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEventInformation.this, MapActivity.class);
                intent.putExtra("from_viewevent",true);
                intent.putExtra("has_marker",true);
                intent.putExtra("marker_address", place_event.getText().toString());
                intent.putExtra("latitude",getIntent().getStringExtra("latitude"));
                intent.putExtra("longitude",getIntent().getStringExtra("longitude"));
                startActivity(intent);

            }
        });

        edit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEventInformation.this, CreateEventActivity.class);
                intent.putExtra("from_map", false);
                intent.putExtra("old_event", true);
                intent.putExtra("event_name", name_of_event);
                intent.putExtra("event_date", date_event.getText().toString());
                intent.putExtra("event_time", time_event.getText().toString());
                intent.putExtra("event_place",place_event.getText().toString());
                intent.putExtra("event_category",cat);
                intent.putExtra("event_number_of_people",numberOfPeople.getText().toString());
                intent.putExtra("phone_number",getIntent().getStringExtra("phone_number"));
                intent.putExtra("event_id", getIntent().getStringExtra("event_id"));
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