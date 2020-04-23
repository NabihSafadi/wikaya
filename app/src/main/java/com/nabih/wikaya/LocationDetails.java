package com.nabih.wikaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LocationDetails extends AppCompatActivity implements AsyncResponse {


    RadioGroup rg_location_type, rg_residential_type;
    EditText et_location_details_property_number, et_numebr_of_people_allowed, et_building_section;
    View view_residential_sector, view_title_deed;
    TextView tv_residential_type_title, tv_refer, tv_location_details_adddress, tv_optional;
    Button btn_create;




    String string_place_name,string_address, string_longitude, string_latitude, string_location_type, string_residential_type, string_number_ppl_allowed,
            string_property_number, string_user_id, string_place_id, string_building_section;

    RadioButton rb_residential, rb_other, rb_apartment, rb_villa, rb_town_house, rb_whole_building, rb_unselect;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    Animation animShake;
    InternalFunction internalFunction = new InternalFunction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        animShake = AnimationUtils.loadAnimation(LocationDetails.this, R.anim.shake);


        findViewById(R.id.sv_location_details).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }

                return false;
            }

        });





        tv_location_details_adddress = (TextView)findViewById(R.id.tv_location_details_adddress);
        string_place_name = sharedpreferences.getString("key_place_name","");
        string_address = sharedpreferences.getString("key_place_address","");
        string_latitude = sharedpreferences.getString("key_latitude","");
        string_longitude = sharedpreferences.getString("key_longitude","");
        string_user_id = sharedpreferences.getString("key_user_id","");
        tv_location_details_adddress.setText(string_place_name);


        rg_location_type = (RadioGroup) findViewById(R.id.rg_location_type);
        rg_residential_type = (RadioGroup) findViewById(R.id.rg_residential_type);
        btn_create = (Button) findViewById(R.id.btn_create);

        rb_residential = (RadioButton) findViewById(R.id.rb_residential);
        rb_other = (RadioButton) findViewById(R.id.rb_other);
        rb_unselect = (RadioButton) findViewById(R.id.rb_unselect);

        view_residential_sector = (View) findViewById(R.id.view_residential_sector);
        tv_residential_type_title = (TextView) findViewById(R.id.tv_residential_type_title);
        rb_apartment = (RadioButton) findViewById(R.id.rb_apartment);
        rb_villa = (RadioButton) findViewById(R.id.rb_villa);
        rb_town_house = (RadioButton) findViewById(R.id.rb_town_house);
        rb_whole_building = (RadioButton) findViewById(R.id.rb_whole_building);

        view_title_deed = (View) findViewById(R.id.view_title_deed);
        tv_refer = (TextView) findViewById(R.id.tv_refer_to_title_deed);
        et_location_details_property_number = (EditText) findViewById(R.id.et_location_details_property_number);
        et_numebr_of_people_allowed = (EditText) findViewById(R.id.et_numebr_of_people_allowed);
        et_building_section = (EditText) findViewById(R.id.et_building_section);
        tv_optional = (TextView) findViewById(R.id.tv_optional);


        rg_residential_type.setVisibility(View.GONE);
        view_residential_sector.setVisibility((View.GONE));
        tv_residential_type_title.setVisibility((View.GONE));
        view_title_deed.setVisibility((View.GONE));
        tv_refer.setVisibility((View.GONE));
        et_numebr_of_people_allowed.setVisibility(View.GONE);
        et_location_details_property_number.setVisibility(View.GONE);
        et_building_section.setVisibility(View.GONE);
        tv_optional.setVisibility(View.GONE);

        rb_unselect.setVisibility(View.GONE);

        btn_create.setVisibility(View.GONE);


        radioGroup();


    }


    private void radioGroup() {


        rg_location_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb_location_details = (RadioButton) group.findViewById(checkedId);

                string_location_type = (String) rb_location_details.getText();


                if (string_location_type.equals(getString(R.string.residential))) {

                    show_residential_sector();
                    hide_title_deed_section();

                    et_numebr_of_people_allowed.setVisibility(View.GONE);


                    if (rb_apartment.isChecked()) {

                        rb_apartment.setChecked(false);
                        rb_unselect.setChecked(true);
                        rb_unselect.setChecked(false);


                        hide_title_deed_section();

                    } else if (rb_town_house.isChecked()) {

                        rb_town_house.setChecked(false);
                        rb_unselect.setChecked(true);
                        rb_unselect.setChecked(false);

                         hide_title_deed_section();


                    } else if (rb_villa.isChecked()) {
                        rb_villa.setChecked(false);
                        rb_unselect.setChecked(true);
                        rb_unselect.setChecked(false);
                        hide_title_deed_section();


                    } else if (rb_whole_building.isChecked()) {

                        rb_whole_building.setChecked(false);
                        rb_unselect.setChecked(true);
                        rb_unselect.setChecked(false);
                        //show which section
                        hide_title_deed_section();



                    }




                } else {


                    hide_residential_sector();
                    show_title_deed_section();
                    string_residential_type = "";
                    et_numebr_of_people_allowed.setVisibility(View.VISIBLE);


                }


            }
        });

        rg_residential_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb_residential_type = (RadioButton) group.findViewById(checkedId);

                string_residential_type = (String) rb_residential_type.getText();
                if(string_residential_type.equals(getString(R.string.building_main_entrance))){


                    btn_create.setVisibility(View.VISIBLE);
                    tv_optional.setVisibility(View.VISIBLE);
                    et_building_section.setVisibility(View.VISIBLE);

                    string_number_ppl_allowed = "";
                    tv_refer.setVisibility(View.GONE);
                    et_location_details_property_number.setVisibility(View.GONE);
                    et_numebr_of_people_allowed.setVisibility(View.GONE);







                }else if(string_residential_type.equals(getString(R.string.apartment_studio))){

                    show_title_deed_section();

                    et_building_section.setVisibility(View.VISIBLE);
                    tv_optional.setVisibility(View.VISIBLE);
                    et_numebr_of_people_allowed.setVisibility(View.GONE);


                }else if(string_residential_type.equals(getString(R.string.villa))){

                    show_title_deed_section();

                    et_building_section.setVisibility(View.VISIBLE);
                    tv_optional.setVisibility(View.VISIBLE);
                    et_numebr_of_people_allowed.setVisibility(View.GONE);


                }else if(string_residential_type.equals(getString(R.string.town_house))){

                    show_title_deed_section();

                    et_building_section.setVisibility(View.VISIBLE);
                    tv_optional.setVisibility(View.VISIBLE);
                    et_numebr_of_people_allowed.setVisibility(View.GONE);


                }else{
                    show_title_deed_section();
                    et_building_section.setVisibility(View.GONE);
                    tv_optional.setVisibility(View.GONE);
                    et_numebr_of_people_allowed.setVisibility(View.GONE);

                }




            }
        });



        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                string_property_number = et_location_details_property_number.getText().toString();
                string_number_ppl_allowed = et_numebr_of_people_allowed.getText().toString();
                string_building_section = et_building_section.getText().toString();

                if(string_number_ppl_allowed.equals("") && string_location_type.equals(getString(R.string.other))){
                    Toast.makeText(LocationDetails.this, getResources().getString(R.string.provide_number_ppl_allowed), Toast.LENGTH_SHORT).show();
                    et_numebr_of_people_allowed.startAnimation(animShake);

                }else if(string_property_number.equals("") && !string_residential_type.equals(getString(R.string.building_main_entrance))){
                    Toast.makeText(LocationDetails.this, getResources().getString(R.string.provide_property_numebr), Toast.LENGTH_SHORT).show();
                    et_location_details_property_number.startAnimation(animShake);

                }else{





                    if(internalFunction.isDeviceConnected(LocationDetails.this)){


                        String validate_user_method = "method_create_place";
                        BackgroundTask backgroundTask = new BackgroundTask(LocationDetails.this);
                        backgroundTask.delegate = LocationDetails.this;
                        backgroundTask.execute(new String[]{validate_user_method,string_user_id, string_place_name, string_address, string_latitude,
                        string_longitude, string_location_type, string_residential_type, string_building_section, string_number_ppl_allowed, string_property_number
                                });

                    }else{
                        view = findViewById(R.id.layout_location_details);
                        showSnackbar(view);
                    }

                }


            }
        });




    }

    public void show_residential_sector() {

        view_residential_sector.setVisibility((View.VISIBLE));
        tv_residential_type_title.setVisibility(View.VISIBLE);
        rg_residential_type.setVisibility(View.VISIBLE);

    }

    public void hide_residential_sector() {

        view_residential_sector.setVisibility((View.GONE));
        tv_residential_type_title.setVisibility(View.GONE);
        rg_residential_type.setVisibility(View.GONE);

    }

    public void show_title_deed_section() {
        view_title_deed.setVisibility((View.VISIBLE));
        tv_refer.setVisibility((View.VISIBLE));
        et_location_details_property_number.setVisibility(View.VISIBLE);
        btn_create.setVisibility(View.VISIBLE);
    }

    public void hide_title_deed_section() {
        view_title_deed.setVisibility((View.GONE));
        tv_refer.setVisibility((View.GONE));
        et_location_details_property_number.setVisibility(View.GONE);
        btn_create.setVisibility(View.GONE);
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

    @Override
    public void processFinish(String str) {

        if(str.contains("place_id_start")){



            Toast.makeText(LocationDetails.this, getResources().getString(R.string.place_uploaded), Toast.LENGTH_SHORT).show();
             string_place_id = internalFunction.getSubString(str, "place_id_end", "place_id_start");

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("key_place_id",string_place_id );
            editor.apply();





            Intent i = new Intent(LocationDetails.this, QRCodeIssue.class);
            startActivity(i);
            //go to QR code
        }else if(str.contains("error place is not added")){
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","location details" );
            editor.putString("key_subject","null" );
            editor.putString("key_message",str );
            editor.apply();

            Intent i = new Intent(LocationDetails.this, ErrorPage.class);
            startActivity(i);

        }

    }
}
