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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Register extends AppCompatActivity implements AsyncResponse {
    EditText et_first_name, et_last_name, et_email, et_phone_number, et_password, et_verify_password;
    String string_first_name, string_last_name, string_email, string_phone_number, string_password, string_verify_password, string_user_id;

    Button btn_register;
    InternalFunction internalFunction = new InternalFunction();
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();

     }


    public void initialize(){

        et_first_name = (EditText)findViewById(R.id.et_register_first_name);
        et_last_name = (EditText)findViewById(R.id.et_register_last_name);
        et_email = (EditText)findViewById(R.id.et_register_email);
        et_phone_number = (EditText)findViewById(R.id.et_register_phone_number);
        et_password = (EditText)findViewById(R.id.et_register_password);
        et_verify_password = (EditText)findViewById(R.id.et_register_verify_password);
        btn_register = (Button)findViewById(R.id.btn_register);

        final Animation animShake = AnimationUtils.loadAnimation(Register.this, R.anim.shake);

        findViewById(R.id.layout_register).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }

        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                string_first_name = et_first_name.getText().toString();
                string_last_name = et_last_name.getText().toString();
                string_email = et_email.getText().toString();
                string_phone_number = et_phone_number.getText().toString();
                string_password = et_password.getText().toString();
                string_verify_password = et_verify_password.getText().toString();

                if(string_first_name.equals("")) {
                    Toast.makeText(Register.this, getResources().getString(R.string.provide_first_name), Toast.LENGTH_SHORT).show();
                    et_first_name.startAnimation(animShake);

                }else if(string_last_name.equals("")){
                    Toast.makeText(Register.this, getResources().getString(R.string.provide_last_name), Toast.LENGTH_SHORT).show();
                    et_last_name.startAnimation(animShake);

                }else  if(!string_email.contains("@") || string_email.equals("")){
                    Toast.makeText(Register.this, getResources().getString(R.string.provide_email_valid), Toast.LENGTH_SHORT).show();
                    et_email.startAnimation(animShake);

                } else if(string_phone_number.equals("")){
                    Toast.makeText(Register.this, getResources().getString(R.string.provide_phone_number), Toast.LENGTH_SHORT).show();
                    et_phone_number.startAnimation(animShake);

                }else if(string_password.equals("")){
                    Toast.makeText(Register.this, getResources().getString(R.string.provide_valid_password), Toast.LENGTH_SHORT).show();
                    et_password.startAnimation(animShake);

                } else if(!string_password.equals(string_verify_password)){
                    Toast.makeText(Register.this, getResources().getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
                    et_password.startAnimation(animShake);
                    et_verify_password.startAnimation(animShake);

                }else if(string_password.length()<6 || string_password.length()>12){
                    Toast.makeText(Register.this, getResources().getString(R.string.password_length), Toast.LENGTH_SHORT).show();
                    et_password.startAnimation(animShake);
                }else{






                    if(internalFunction.isDeviceConnected(Register.this)){

                        String validate_user_method = "method_register";
                        BackgroundTask backgroundTask = new BackgroundTask(Register.this);
                        backgroundTask.delegate = Register.this;
                        backgroundTask.execute(new String[]{validate_user_method, string_first_name, string_last_name,
                                string_email, string_phone_number, string_password   });



                    }else{
                        View view = findViewById(R.id.layout_register);
                        showSnackbar(view);
                    }



                }
            }
        });


    }

    @Override
    public void processFinish(String str) {
        final Animation animShake = AnimationUtils.loadAnimation(Register.this, R.anim.shake);

        if (str == null){



            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","manual registration" );  // Saving string
            editor.putString("key_subject","null" );  // Saving string
            editor.putString("key_message",str );  // Saving string

            editor.apply();

            Intent i = new Intent(Register.this, ErrorPage.class);
            startActivity(i);



        }else if(str.contains("email exist")){

            Toast.makeText(Register.this,
                    getResources().getString(R.string.email_registered), Toast.LENGTH_LONG).show();


            et_email.startAnimation(animShake);


        }else if(str.contains("user_id_start")){

            //get user id

            string_user_id = internalFunction.getSubString(str, "user_id_end", "user_id_start");

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("key_user_id",string_user_id );  // Saving string
            editor.putString("key_first_name",string_first_name );  // Saving string
            editor.putString("key_last_name",string_last_name );  // Saving string
            editor.putString("key_email",string_email );  // Saving string
            editor.putString("key_phone_number",string_phone_number );  // Saving string
            editor.putString("key_password",string_password );  // Saving string

            editor.apply();

            Toast.makeText(Register.this,
                    getResources().getString(R.string.registerd), Toast.LENGTH_LONG).show();


            Intent i = new Intent(Register.this, MainActivity.class);
            startActivity(i);




        }else if(str.contains("register error")){

            //get user id

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","manual registration" );  // Saving string
            editor.putString("key_subject","registration error" );  // Saving string
            editor.putString("key_message",str );  // Saving string

            editor.apply();

            Intent i = new Intent(Register.this, ErrorPage.class);
            startActivity(i);




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

}
