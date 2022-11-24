package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    SearchView searchView;
    GoogleMap map;
    Button add_place_event;
    LatLng latLngg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        searchView = findViewById(R.id.sv_location);
        add_place_event =findViewById(R.id.add_location_event);
        add_place_event.setVisibility(View.INVISIBLE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location!=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MapActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location + ", Chisinau",1);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    latLngg = new LatLng(address.getLatitude(),address.getLongitude());
                    map.clear();
                    map.addMarker(new MarkerOptions().position(latLngg).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngg,15));
                    add_place_event.setVisibility(View.VISIBLE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
        add_place_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(MapActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLngg.latitude, latLngg.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    Intent intent = new Intent(MapActivity.this, CreateEventActivity.class);
                    intent.putExtra("from_map", true);
                    intent.putExtra("old_event",false);
                    intent.putExtra("event_name", getIntent().getStringExtra("event_name"));
                    intent.putExtra("event_date", getIntent().getStringExtra("event_date"));
                    intent.putExtra("event_time", getIntent().getStringExtra("event_time"));
                    intent.putExtra("event_place",address);
                    intent.putExtra("event_category",getIntent().getStringExtra("event_category"));
                    intent.putExtra("event_number_of_people",getIntent().getStringExtra("event_number_of_people"));
//                intent.putExtra("phone_number",getIntent().getStringExtra("phone_number"));
                    intent.putExtra("event_description", getIntent().getStringExtra("event_description"));
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            map = googleMap;
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        // Position the map's camera near Sydney, Australia.


        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47, 29)));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                latLngg = latLng;
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                add_place_event.setVisibility(View.VISIBLE);
            }
        });
    }

    }

