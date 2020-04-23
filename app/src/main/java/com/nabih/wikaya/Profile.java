package com.nabih.wikaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Profile extends AppCompatActivity implements AsyncResponse {
    EditText et_first_name, et_last_name, et_phone_number;
    String string_first_name, string_last_name, string_phone_number, string_user_id;
    Button btn_update;
    InternalFunction internalFunction = new InternalFunction();
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialize();

    }

    public void initialize() {

        et_first_name = (EditText) findViewById(R.id.et_profile_first_name);
        et_last_name = (EditText) findViewById(R.id.et_profile_last_name);
        et_phone_number = (EditText) findViewById(R.id.et_profile_phone_number);
        btn_update = (Button) findViewById(R.id.btn_profile_update);

        final Animation animShake = AnimationUtils.loadAnimation(Profile.this, R.anim.shake);

        findViewById(R.id.layout_profile).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }

        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        string_user_id = sharedpreferences.getString("key_user_id","");
        string_first_name = sharedpreferences.getString("key_first_name","");
        string_last_name = sharedpreferences.getString("key_last_name","");
        string_phone_number = sharedpreferences.getString("key_phone_number","");


        Log.d("us-uid", string_user_id);
        Log.d("us-fn", string_first_name);
        Log.d("us-ln", string_last_name);
        Log.d("us-pn", string_phone_number);

         et_first_name.setText(string_first_name);
        et_last_name.setText(string_last_name);
        et_phone_number.setText(string_phone_number);




        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                string_first_name = et_first_name.getText().toString();
                string_last_name = et_last_name.getText().toString();
                string_phone_number = et_phone_number.getText().toString();

                if (string_first_name.equals("")) {
                    Toast.makeText(Profile.this, getResources().getString(R.string.provide_first_name), Toast.LENGTH_SHORT).show();
                    et_first_name.startAnimation(animShake);

                } else if (string_last_name.equals("")) {
                    Toast.makeText(Profile.this, getResources().getString(R.string.provide_last_name), Toast.LENGTH_SHORT).show();
                    et_last_name.startAnimation(animShake);

                } else if (string_phone_number.equals("")) {
                    Toast.makeText(Profile.this, getResources().getString(R.string.provide_phone_number), Toast.LENGTH_SHORT).show();
                    et_phone_number.startAnimation(animShake);

                } else {


                    if (internalFunction.isDeviceConnected(Profile.this)) {

                        String validate_user_method = "method_update_profile";
                        BackgroundTask backgroundTask = new BackgroundTask(Profile.this);
                        backgroundTask.delegate = Profile.this;
                        backgroundTask.execute(new String[]{validate_user_method, string_user_id,string_first_name, string_last_name, string_phone_number});


                    } else {
                        View view = findViewById(R.id.layout_register);
                        showSnackbar(view);
                    }


                }
            }
        });

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

        Log.d("result",str);

        if(str.equals("profile_updated")){

            SharedPreferences.Editor editor = sharedpreferences.edit();
             editor.putString("key_first_name",string_first_name );  // Saving string
            editor.putString("key_last_name",string_last_name );  // Saving string
             editor.putString("key_phone_number",string_phone_number );  // Saving string

            editor.apply();

            Toast.makeText(Profile.this,
                    getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();


            Intent i = new Intent(Profile.this, MainActivity.class);
            startActivity(i);
        }else if(str.contains("profile_error")){
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","Profile" );  // Saving string
            editor.putString("key_subject","error updating profile" );  // Saving string
            editor.putString("key_message",str );  // Saving string

            editor.apply();

            Intent i = new Intent(Profile.this, ErrorPage.class);
            startActivity(i);

        }
    }
}
