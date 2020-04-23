package com.nabih.wikaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPlaces extends AppCompatActivity implements AsyncResponse{
    InternalFunction internalFunction = new InternalFunction();
    String string_user_id;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    JSONObject jsonObject;
    JSONArray jsonArray;
    PlacesAdapter placesAdapter;
    ListView lv_places;
    int count = 0;
    TextView tv_list_title;

    String JSON_STRING, string_place_id, string_place_name, string_place_address, string_location_type, string_residential_type, string_section,
            string_property_number;






    ArrayList<String> arrayList_place_id = new ArrayList<String>();
    ArrayList<String> arrayList_place_name = new ArrayList<String>();
    ArrayList<String> arrayList_place_address = new ArrayList<String>();
    ArrayList<String> arrayList_location_type = new ArrayList<String>();
    ArrayList<String> arrayList_residential_type = new ArrayList<String>();
    ArrayList<String> arrayList_section = new ArrayList<String>();
    ArrayList<String> arrayList_property_number = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        string_user_id = sharedpreferences.getString("key_user_id","1");

        placesAdapter = new PlacesAdapter(this, R.layout.places_row_layout);
        lv_places = (ListView)findViewById(R.id.lv_my_places);
        tv_list_title = (TextView) findViewById(R.id.tv_list_title);
        lv_places.setAdapter(placesAdapter);




        get_my_registered_places();

        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(lv_places),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {






                            }
                        });

        lv_places.setOnTouchListener(touchListener);
        lv_places.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        lv_places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {




                    string_place_id =  arrayList_place_id.get(position);
                    string_place_name =  arrayList_place_name.get(position);

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("key_place_id",string_place_id );  // Saving string
                    editor.putString("key_place_name",string_place_name );  // Saving string

                    editor.apply();

                    Intent i = new Intent(MyPlaces.this, QRCodeIssue.class);
                    startActivity(i);

                 }
            }
        });




    }


    private void get_my_registered_places() {

        if(internalFunction.isDeviceConnected(this)){

            String method_add_like_list = "method_get_registered_places";
            BackgroundTask backgroundTask = new BackgroundTask(MyPlaces.this);
            backgroundTask.delegate = MyPlaces.this;
            backgroundTask.execute(new String[]{method_add_like_list,string_user_id});

        }else{
            View view = findViewById(R.id.layout_my_places);
            showSnackbar(view);
        }


    }



    public void showSnackbar(View view) {

        Snackbar snackbar = Snackbar
                .make(view,getResources().getString(R.string.no_internet) , Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        startActivity(getIntent());
                    }
                });

        snackbar.show();
    }

    public void populateListView(String output) {


        this.JSON_STRING = output;



        try {



            this.jsonObject = new JSONObject(this.JSON_STRING);

            this.jsonArray = this.jsonObject.getJSONArray("server_response");


             while(count < this.jsonArray.length()){
                JSONObject JO = this.jsonArray.getJSONObject(count);
                string_place_id = JO.getString("place_id");
                 string_place_name = JO.getString("place_name");
                string_place_address = JO.getString("place_address");
                string_location_type = JO.getString("location_type");
                string_residential_type = JO.getString("residential_type");
                string_section = JO.getString("section");
                string_property_number = JO.getString("property_number");

                arrayList_place_id.add(JO.getString("place_id"));
                arrayList_place_name.add(JO.getString("place_name"));


                Places places = new Places(string_place_id, string_place_name, string_place_address, string_location_type, string_residential_type,
                        string_section, string_property_number);

                this.placesAdapter.add(places);

                count++;


            }


        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void processFinish(String str) {


          if (str.contains("\"server_response\":[{\"place_id\":\"")){

            populateListView(str);



        } else if(str.contains("{\"server_response\":[]}")){

             tv_list_title.setText(getString(R.string.no_places));


         }
    }
}