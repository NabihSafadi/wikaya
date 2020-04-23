package com.nabih.wikaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class AddPlace extends AppCompatActivity implements OnMapReadyCallback{

    TextView tv_title;

    private static final String TAG = "";
    PlacesClient placesClient;
    Button btn_continue;

    String string_place_name, string_place_address;
    Double double_longitude, double_latitude;
    private GoogleMap mMap;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        btn_continue = (Button) findViewById(R.id.btn_add_place_continue);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        tv_title = (TextView)findViewById(R.id.tv_add_place_title);
        tv_title.setText(R.string.search_place);

        btn_continue.setVisibility(View.GONE);


        String apiKey = getString(R.string.api_key);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);

        }
        placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList( Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                string_place_name = place.getName();
                string_place_address = place.getAddress();
                double_longitude = place.getLatLng().longitude;
                double_latitude = place.getLatLng().latitude;


                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(AddPlace.this);

                tv_title.setText(R.string.located);

                btn_continue.setVisibility(View.VISIBLE);




            }

            @Override
            public void onError(Status status) {

                Toast.makeText(AddPlace.this,
                        getResources().getString(R.string.error_place), Toast.LENGTH_LONG).show();
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("key_place_name",string_place_name );  // Saving string
                editor.putString("key_place_address",string_place_address );  // Saving string
                editor.putString("key_longitude", String.valueOf(double_longitude));  // Saving string
                editor.putString("key_latitude", String.valueOf(double_latitude));  // Saving string

                editor.apply();

                Intent i = new Intent(AddPlace.this, LocationDetails.class);
                startActivity(i);

            }

        });




// Create a new Places client instance.
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        LatLng location = new LatLng(double_latitude, double_longitude);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title(string_place_name));


         float zoomLevel = 17.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
    }




}


