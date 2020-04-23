package com.nabih.wikaya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class ValidateCheckIn extends AppCompatActivity implements AsyncResponse {

    //declare TextView
    TextView tv_vc_place_name, tv_vc_property_type, tv_vc_property_numebr, tv_vc_place_address, tv_vc_current_ppl_inside, tv_vc_ppl_allowed_inside, tv_vc_result,
            tv_vc_see_updates, tv_vc_ppl_allowed_inside_title, tv_vc_current_ppl_inside_title ;

    //declare Buttons
    Button btn_vc_confirm, btn_vc_cancel, btn_vc_refresh, btn_done;

    //declare Image
    ImageView iv_result_image;

    //declare radio group and radio button
    RadioGroup rg_vc_purpose_visit;
    RadioButton rb_visiting_someone, rb_lobby_only;

    //declare Edit text
    EditText et_vc_apartment_number;


    //declare strings and other stuff
    String string_place_id, string_user_id, string_place_details, string_place_name, string_place_address, string_ppl_allowed_inside,
            string_current_ppl_inside, string_location_type, string_residential_type, string_property_number , string_health_status, string_section,
            string_purpose_visit = "";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    InternalFunction internalFunction = new InternalFunction();
    Animation animShake;
    InputMethodManager imm;
    View focusedView;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_check_in);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        string_place_details =    sharedpreferences.getString("key_intent_data","");
        string_place_id = internalFunction.getSubString(string_place_details, "<place_id_end>", "<place_id_start>");
        string_user_id = sharedpreferences.getString("key_user_id","");
        animShake = AnimationUtils.loadAnimation(ValidateCheckIn.this, R.anim.shake);

        findViewById(R.id.sv_validate_checkin).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                focusedView = ValidateCheckIn.this.getCurrentFocus();
                if (focusedView != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                }

                return false;
            }

        });


        tv_vc_place_name = findViewById(R.id.tv_vc_place_name);
        tv_vc_property_type = findViewById(R.id.tv_vc_property_type);
        tv_vc_property_numebr = findViewById(R.id.tv_vc_property_numebr);
        tv_vc_place_address = findViewById(R.id.tv_vc_place_address);
        tv_vc_current_ppl_inside = findViewById(R.id.tv_vc_current_ppl_inside);
        tv_vc_ppl_allowed_inside = findViewById(R.id.tv_vc_ppl_allowed_inside);
        tv_vc_see_updates = findViewById(R.id.tv_vc_see_updates);
        tv_vc_result = findViewById(R.id.tv_vc_result);
        iv_result_image = findViewById(R.id.iv_result_image);
        btn_vc_confirm = findViewById(R.id.btn_vc_confirm);
        btn_vc_cancel = findViewById(R.id.btn_vc_cancel);
        btn_vc_refresh = findViewById(R.id.btn_vc_refresh);
        btn_done = findViewById(R.id.btn_done);
        btn_done.setVisibility(View.GONE);
        rg_vc_purpose_visit = findViewById(R.id.rg_vc_purpose_visit);
        rb_visiting_someone = findViewById(R.id.rb_meeting_someone);
        rb_lobby_only = findViewById(R.id.rb_lobby_only);
        et_vc_apartment_number = findViewById(R.id.et_vc_apartment_number);

        tv_vc_ppl_allowed_inside_title = findViewById(R.id.tv_vc_ppl_allowed_inside_title);
        tv_vc_current_ppl_inside_title = findViewById(R.id.tv_vc_current_ppl_inside_title);






        btn_vc_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                string_property_number = et_vc_apartment_number.getText().toString();

              if(string_purpose_visit.equals("") && string_property_number.equals("")){
                    Toast.makeText(ValidateCheckIn.this, getResources().getString(R.string.purpose_visit), Toast.LENGTH_SHORT).show();
                    rg_vc_purpose_visit.startAnimation(animShake);


              }else if(string_location_type.equals(getString(R.string.residential)) && string_residential_type.equals(getString(R.string.building_main_entrance))
                        && string_purpose_visit.equals(getString(R.string.meeting_someone))){
                    if(string_property_number.equals("")){
                        Toast.makeText(ValidateCheckIn.this, getResources().getString(R.string.which_apartment), Toast.LENGTH_SHORT).show();
                        et_vc_apartment_number.startAnimation(animShake);
                    }else{

                        string_purpose_visit = string_property_number;



                        check_in();
                    }
                }else{
                    check_in();
                }



            }
        });



        btn_vc_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(ValidateCheckIn.this);
                builder1.setTitle(R.string.cancel_check_in);
                builder1.setMessage(R.string.are_you_sure);
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(internalFunction.isDeviceConnected(ValidateCheckIn.this)){


                                    String validate_user_method = "method_cancel_check_in";
                                    BackgroundTask backgroundTask = new BackgroundTask(ValidateCheckIn.this);
                                    backgroundTask.delegate = ValidateCheckIn.this;
                                    backgroundTask.execute(new String[]{validate_user_method,string_user_id, string_place_id});

                                }else{
                                    View view = findViewById(R.id.layout_validate_check_in);
                                    showSnackbar(view);
                                }


                            }
                        });

                builder1.setPositiveButton(
                        R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();




            }
        });



        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("key_intent_data");

                editor.apply();

                Intent i = new Intent(ValidateCheckIn.this, MainActivity.class);
                startActivity(i);

            }
        });



        btn_vc_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
                startActivity(getIntent());


            }
        });




        rg_vc_purpose_visit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb_location_details = (RadioButton) group.findViewById(checkedId);


                string_purpose_visit = (String) rb_location_details.getText();


                if (string_purpose_visit.equals(getString(R.string.meeting_someone))) {



                    et_vc_apartment_number.setVisibility(View.VISIBLE);




                    tv_vc_result.setText(R.string.write_apt_num);
                    iv_result_image.setImageResource(R.drawable.apartment_num);
                    tv_vc_result.setTextColor(Color.parseColor("#808080"));

                    btn_vc_confirm.setVisibility(View.VISIBLE);
                    btn_vc_cancel.setVisibility(View.VISIBLE);

                    tv_vc_see_updates.setVisibility(View.GONE);





                } else {

                    you_can_enter();

                    et_vc_apartment_number.setVisibility(View.GONE);

                    findViewById(R.id.rb_lobby_only).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            focusedView = ValidateCheckIn.this.getCurrentFocus();
                            if (focusedView != null) {
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                            }

                            return false;
                        }

                    });




                }


            }
        });

         get_health_status();


    }

    public void you_can_enter(){
        tv_vc_result.setText(R.string.can_enter);
        iv_result_image.setImageResource(R.drawable.pass);
        tv_vc_result.setTextColor(Color.parseColor("#4ed28e"));
    }

    public void get_health_status(){
        if(internalFunction.isDeviceConnected(ValidateCheckIn.this)){


            String validate_user_method = "method_get_health_status_and_place_info";
            BackgroundTask backgroundTask = new BackgroundTask(ValidateCheckIn.this);
            backgroundTask.delegate = ValidateCheckIn.this;
            backgroundTask.execute(new String[]{validate_user_method,string_user_id, string_place_id});

        }else{
            View view = findViewById(R.id.layout_validate_check_in);
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

    public void check_in(){
        if(internalFunction.isDeviceConnected(ValidateCheckIn.this)){


            String validate_user_method = "method_check_in";
            BackgroundTask backgroundTask = new BackgroundTask(ValidateCheckIn.this);
            backgroundTask.delegate = ValidateCheckIn.this;
            backgroundTask.execute(new String[]{validate_user_method,string_user_id, string_place_id, string_purpose_visit, string_place_name
            });

        }else{
            View view = findViewById(R.id.layout_validate_check_in);
            showSnackbar(view);
        }
    }



    @Override
    public void processFinish(String str) {



        if(str.contains("place_does_not_exist")){

            Intent i = new Intent(ValidateCheckIn.this, PlaceNoExist.class);
            startActivity(i);


        } else if(str.contains("place_name_start")){
            string_place_name = internalFunction.getSubString(str, "place_name_end", "place_name_start");
            string_place_address = internalFunction.getSubString(str, "place_address_end", "place_address_start");
            string_location_type = internalFunction.getSubString(str, "location_type_end", "location_type_start");
            string_residential_type = internalFunction.getSubString(str, "residential_type_end", "residential_type_start");
            string_section = internalFunction.getSubString(str, "section_end", "section_start");
            string_property_number = internalFunction.getSubString(str, "property_number_end", "property_number_start");
            string_ppl_allowed_inside = internalFunction.getSubString(str, "number_ppl_allowed_end", "number_ppl_allowed_start");
            string_current_ppl_inside = internalFunction.getSubString(str, "current_ppl_inside_end", "current_ppl_inside_start");
            string_health_status = internalFunction.getSubString(str, "health_status_end", "health_status_start");


            if(string_section.equals("")){

                tv_vc_place_name.setText(string_place_name);

            }else{
                tv_vc_place_name.setText(string_place_name +" - Section: " + string_section  );

            }



            //residential - apartment/studio
            if(string_location_type.equals(getString(R.string.residential)) && string_residential_type.equals(getString(R.string.apartment_studio))){
                tv_vc_property_type.setText(getString(R.string.apartment_studio));
                string_purpose_visit = string_property_number;

                tv_vc_property_numebr.setText(string_property_number);
                rg_vc_purpose_visit.setVisibility(View.GONE);
                et_vc_apartment_number.setVisibility(View.GONE);

                tv_vc_ppl_allowed_inside_title.setVisibility(View.GONE);
                tv_vc_ppl_allowed_inside.setVisibility(View.GONE);
                tv_vc_current_ppl_inside_title.setVisibility(View.GONE);
                tv_vc_current_ppl_inside.setVisibility(View.GONE);

                btn_vc_refresh.setVisibility(View.GONE);
                tv_vc_see_updates.setVisibility(View.GONE);

                tv_vc_result.setText(R.string.can_enter);
                iv_result_image.setImageResource(R.drawable.pass);
                tv_vc_result.setTextColor(Color.parseColor("#808080"));

                btn_vc_confirm.setVisibility(View.VISIBLE);


            }else if(string_location_type.equals(getString(R.string.residential)) && string_residential_type.equals(getString(R.string.town_house))){
                tv_vc_property_type.setText(R.string.town_house);
                tv_vc_property_numebr.setText(string_property_number);

                string_purpose_visit = string_property_number;


                rg_vc_purpose_visit.setVisibility(View.GONE);
                et_vc_apartment_number.setVisibility(View.GONE);

                tv_vc_ppl_allowed_inside_title.setVisibility(View.GONE);
                tv_vc_ppl_allowed_inside.setVisibility(View.GONE);
                tv_vc_current_ppl_inside_title.setVisibility(View.GONE);
                tv_vc_current_ppl_inside.setVisibility(View.GONE);

                btn_vc_refresh.setVisibility(View.GONE);
                tv_vc_see_updates.setVisibility(View.GONE);

                tv_vc_result.setText(R.string.can_enter);
                iv_result_image.setImageResource(R.drawable.pass);
                tv_vc_result.setTextColor(Color.parseColor("#808080"));

                btn_vc_confirm.setVisibility(View.VISIBLE);


                //residential - villa

            }else if(string_location_type.equals(getString(R.string.residential)) && string_residential_type.equals(getString(R.string.villa))){
                tv_vc_property_type.setText(R.string.villa);
                tv_vc_property_numebr.setText(string_property_number);

                //residential - main entrance

                string_purpose_visit = string_property_number;


                rg_vc_purpose_visit.setVisibility(View.GONE);
                et_vc_apartment_number.setVisibility(View.GONE);

                tv_vc_ppl_allowed_inside_title.setVisibility(View.GONE);
                tv_vc_ppl_allowed_inside.setVisibility(View.GONE);
                tv_vc_current_ppl_inside_title.setVisibility(View.GONE);
                tv_vc_current_ppl_inside.setVisibility(View.GONE);

                btn_vc_refresh.setVisibility(View.GONE);
                tv_vc_see_updates.setVisibility(View.GONE);

                tv_vc_result.setText(R.string.can_enter);
                iv_result_image.setImageResource(R.drawable.pass);
                tv_vc_result.setTextColor(Color.parseColor("#808080"));

                btn_vc_confirm.setVisibility(View.VISIBLE);


            }else if(string_location_type.equals(getString(R.string.residential)) && string_residential_type.equals(getString(R.string.building_main_entrance))){

                tv_vc_property_type.setText(R.string.building_main_entrance);

                string_purpose_visit = "Meeting someone";
                tv_vc_ppl_allowed_inside_title.setVisibility(View.GONE);
                tv_vc_ppl_allowed_inside.setVisibility(View.GONE);
                tv_vc_current_ppl_inside_title.setVisibility(View.GONE);
                tv_vc_current_ppl_inside.setVisibility(View.GONE);
                tv_vc_property_numebr.setVisibility(View.GONE);

                btn_vc_refresh.setVisibility(View.GONE);
                tv_vc_see_updates.setVisibility(View.GONE);


                tv_vc_result.setText(R.string.write_apt_num);
                iv_result_image.setImageResource(R.drawable.apartment_num);
                tv_vc_result.setTextColor(Color.parseColor("#808080"));




            }else if(string_location_type.equals(getString(R.string.other))){

                string_purpose_visit = string_property_number;

                tv_vc_property_type.setVisibility(View.GONE);
                tv_vc_property_numebr.setVisibility(View.GONE);

                rg_vc_purpose_visit.setVisibility(View.GONE);
                et_vc_apartment_number.setVisibility(View.GONE);

                tv_vc_ppl_allowed_inside.setText(string_ppl_allowed_inside);
                tv_vc_current_ppl_inside.setText(string_current_ppl_inside);




                btn_vc_refresh.setVisibility(View.VISIBLE);
                tv_vc_see_updates.setVisibility(View.VISIBLE);

                int current_ppl_inside = Integer.parseInt(string_current_ppl_inside);
                int total_ppl_inside = Integer.parseInt(string_ppl_allowed_inside);

                if(current_ppl_inside >= total_ppl_inside ){

                    tv_vc_result.setText( R.string.can_not_enter);

                    iv_result_image.setImageResource(R.drawable.stop);
                    tv_vc_result.setTextColor(Color.parseColor("#FF0000"));

                    btn_vc_confirm.setVisibility(View.GONE);
                    btn_vc_cancel.setVisibility(View.GONE);



                } else {



                    tv_vc_result.setText(R.string.can_enter);
                    iv_result_image.setImageResource(R.drawable.pass);
                    tv_vc_result.setTextColor(Color.parseColor("#808080"));

                    btn_vc_confirm.setVisibility(View.VISIBLE);
                    btn_vc_cancel.setVisibility(View.VISIBLE);


                }


            }


            tv_vc_place_address.setText(string_place_address);

        }else if(str.contains("checked_in")){
            tv_vc_result.setText(R.string.checked_in);

            iv_result_image.setImageResource(R.drawable.tick);
            tv_vc_result.setTextColor(Color.parseColor("#4ed28e"));

            btn_vc_confirm.setVisibility(View.GONE);
            btn_vc_cancel.setVisibility(View.VISIBLE);
            btn_done.setVisibility(View.VISIBLE);

            tv_vc_see_updates.setVisibility(View.GONE);
            btn_vc_refresh.setVisibility(View.GONE);

            int current_people_inside = Integer.parseInt(string_current_ppl_inside) + 1;

            tv_vc_current_ppl_inside.setText(""+current_people_inside );

        }else if(str.contains("same_place")){
            tv_vc_result.setText(R.string.already_checked_in);

            iv_result_image.setImageResource(R.drawable.tick);
            tv_vc_result.setTextColor(Color.parseColor("#4ed28e"));

            btn_vc_confirm.setVisibility(View.GONE);
            btn_vc_cancel.setVisibility(View.VISIBLE);
            btn_done.setVisibility(View.VISIBLE);

            tv_vc_see_updates.setVisibility(View.GONE);
            btn_vc_refresh.setVisibility(View.GONE);



        }else if(str.contains("full_inside")){
            finish();
            startActivity(getIntent());

        }else if(str.contains("cancel_not_checkedin")){
            sharedpreferences.edit().remove("key_intent_data").apply();
            Intent i = new Intent(ValidateCheckIn.this, MainActivity.class);
            startActivity(i);

        }else if(str.contains("checkin_deleted")){
            Toast.makeText(ValidateCheckIn.this, getResources().getString(R.string.check_in_deleted), Toast.LENGTH_SHORT).show();

            sharedpreferences.edit().remove("key_intent_data").apply();
            Intent i = new Intent(ValidateCheckIn.this, MainActivity.class);
            startActivity(i);

        }else if(str.contains("error")){

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","validate checkin" );  // Saving string
            editor.putString("key_subject","error deleting checkin" );  // Saving string
            editor.putString("key_message",str );  // Saving string

            editor.apply();

            Intent i = new Intent(ValidateCheckIn.this, ErrorPage.class);
            startActivity(i);


        }


    }
}
