package com.nabih.wikaya;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity  implements AsyncResponse{

    Button btn_create_place, btn_check_in, btn_check_out, btn_my_health_status, btn_edit_place, btn_edit_profile;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    ImageButton ib_exit;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    InternalFunction internalFunction = new InternalFunction();
    String string_user_id, string_health_status, string_android_version, string_android_link, string_message_p_1, string_message_p_2,
            string_healthy_message, string_infected_message;
    String string_place_added = "0";
    Animation animShake ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        /*
        1- check version (get link)
                a. go to update
                b. initialize
        2- check if user is logged in
                    a- go to login page
                    b- get health condition

         */


        get_link();



    }





    public void initialize(){
        ib_exit = (ImageButton)findViewById(R.id.ib_main_exit);

        btn_create_place = (Button) findViewById(R.id.btn_create_place);
        btn_check_in = (Button) findViewById(R.id.btn_check_in);
        btn_check_out = (Button) findViewById(R.id.btn_check_out);
        btn_my_health_status = (Button) findViewById(R.id.btn_my_health_status);
        btn_edit_place = (Button) findViewById(R.id.btn_edit_place);
        btn_edit_profile = (Button) findViewById(R.id.btn_edit_profile);
        if(sharedpreferences.contains("key_user_id")) {

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            string_user_id = sharedpreferences.getString("key_user_id","");

            get_health_status();


            btn_edit_profile.setVisibility(View.VISIBLE);

        }else{

            btn_edit_profile.setVisibility(View.GONE);
            ib_exit.setVisibility(View.GONE);
            btn_edit_place.setVisibility(View.GONE);
        }



        animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);






        ib_exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle(R.string.exit);
                builder1.setMessage(R.string.are_you_sure);
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                sharedpreferences.edit().clear().apply();
                                finishAffinity(); // Close all activites
                                System.exit(0);  // c



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






        btn_create_place.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                             btn_create_place.setBackgroundResource(R.drawable.add_place_pressed);

                            if(sharedpreferences.contains("key_user_id")) {

                                checkLocationPermission();

                            }else{
                                //go to login

                                Toast.makeText(MainActivity.this, getResources().getString(R.string.login_hint_report), Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(MainActivity.this, Login.class);
                                startActivity(i);
                            }


                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        btn_create_place.setBackgroundResource(R.drawable.add_place);

                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });



        btn_check_in.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {



                            if(sharedpreferences.contains("key_user_id")) {

                                btn_check_in.setBackgroundResource(R.drawable.check_in_pressed);

                                if(string_health_status.equals("hs_clean")){

                                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                                        Intent i = new Intent(MainActivity.this, CheckIn.class);
                                        startActivity(i);
                                    } else {
                                        ActivityCompat.requestPermissions(MainActivity.this, new
                                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                                    }

                                }else{

                                    //toast here and shake
                                    Toast.makeText(MainActivity.this, getResources().getString(R.string.hs_not_clean), Toast.LENGTH_SHORT).show();
                                    btn_my_health_status.startAnimation(animShake);

                                }


                            }else{
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.login_hint_report), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this, Login.class);
                                startActivity(i);

                            }

                            v.invalidate();
                            break;



                    }
                    case MotionEvent.ACTION_UP: {
                        btn_check_in.setBackgroundResource(R.drawable.check_in);

                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });




        btn_check_out.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                                    if(sharedpreferences.contains("key_user_id")) {
                                        //go to check out
                                        Intent i = new Intent(MainActivity.this, CheckOut.class);
                                        startActivity(i);
                                        btn_check_out.setBackgroundResource(R.drawable.check_out_pressed);



                                    }else{
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.login_hint_report), Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(MainActivity.this, Login.class);
                                        startActivity(i);

                                    }


                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                                    btn_check_out.setBackgroundResource(R.drawable.check_out);

                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        btn_my_health_status.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {



                        if(sharedpreferences.contains("key_user_id")) {
                            //go to check out
                            btn_my_health_status.setBackgroundResource(R.drawable.my_health_status_pressed);
                            Intent i = new Intent(MainActivity.this, MyHealthStatus.class);
                            startActivity(i);




                        }else{
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.login_hint_report), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, Login.class);
                            startActivity(i);

                        }


                        v.invalidate();
                        break;

                    }
                    case MotionEvent.ACTION_UP: {
                        btn_my_health_status.setBackgroundResource(R.drawable.my_health_status);

                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        btn_edit_place.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btn_edit_place.setBackgroundResource(R.drawable.edit_place_pressed);

                        Intent i = new Intent(MainActivity.this, MyPlaces.class);
                         startActivity(i);

                        v.invalidate();
                        break;



                    }
                    case MotionEvent.ACTION_UP: {
                        btn_edit_place.setBackgroundResource(R.drawable.edit_place);

                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        btn_edit_profile.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btn_edit_profile.setBackgroundResource(R.drawable.profile_pressed);

                        Intent i = new Intent(MainActivity.this, Profile.class);
                        startActivity(i);

                        v.invalidate();
                        break;



                    }
                    case MotionEvent.ACTION_UP: {
                        btn_edit_profile.setBackgroundResource(R.drawable.profile);

                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });



    }


    private void get_link() {
        if(internalFunction.isDeviceConnected(this)){


            String method_link_android = "method_get_link";
            BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
            backgroundTask.delegate = MainActivity.this;
            backgroundTask.execute(new String[]{method_link_android});

        }else{
            View view = findViewById(R.id.layout_main);
            showSnackbar(view);
        }




    }
    public void get_health_status(){
        if(internalFunction.isDeviceConnected(MainActivity.this)){

            String validate_user_method = "method_get_health_status_for_main_activity";
            BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
            backgroundTask.delegate = MainActivity.this;
            backgroundTask.execute(new String[]{validate_user_method, string_user_id });

        }else{
            View view = findViewById(R.id.layout_main);
            showSnackbar(view);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
            return false;
        } else {

            //permission is granted to go add a place activity
            Intent i = new Intent(MainActivity.this, AddPlace.class);
            startActivity(i);
            return true;

        }
    }


    @Override
    public void processFinish(String str) {


        if(str.contains("android_version_start")){
            string_android_version = internalFunction.getSubString(str, "android_version_end", "android_version_start");
            string_android_link = internalFunction.getSubString(str, "android_link_end", "android_link_start");
            string_message_p_1 = internalFunction.getSubString(str, "message_p_1_end", "message_p_1_start");
            string_message_p_2 = internalFunction.getSubString(str, "message_p_2_end", "message_p_2_start");
            string_healthy_message = internalFunction.getSubString(str, "healthy_message_end", "healthy_message_start");
            string_infected_message = internalFunction.getSubString(str, "infected_message_end", "infected_message_start");


            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_android_link", string_android_link);
            editor.putString("key_msg_p_1", string_message_p_1);
            editor.putString("key_msg_p_2", string_message_p_2);
            editor.putString("key_healthy_message", string_healthy_message);
            editor.putString("key_infected_message", string_infected_message);

             editor.apply();

            PackageInfo pInfo = null;

            try {
                pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                String version_name = pInfo.versionName;
                double current_version = Double.valueOf(version_name);
                double server_version = Double.valueOf(string_android_version);

                if(server_version > current_version){

                    Intent i = new Intent(MainActivity.this, UpdateApp.class);
                    startActivity(i);
                }else{
                    initialize();
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }






        }else if(str.contains("health_status_end")){



            string_health_status = internalFunction.getSubString(str, "health_status_end", "health_status_start");
            string_place_added = internalFunction.getSubString(str, "place_added_end", "place_added_start");

            if(string_place_added.equals("0")){
                btn_edit_place.setVisibility(View.GONE);
            }else{
                btn_edit_place.setVisibility(View.VISIBLE);

            }
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


    @Override
    protected void onResume() {
        super.onResume();

        get_link();
    }
}
