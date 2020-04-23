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

public class ResetPassword extends AppCompatActivity implements AsyncResponse{

    EditText et_token , et_password, et_verify_password;
    String string_token_transfered,string_token, string_password, string_verify_password, string_email;
    Button btn_change_password;
    InternalFunction internalFunction = new InternalFunction();

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initialize();
    }


    private void initialize() {
        final Animation animShake = AnimationUtils.loadAnimation(ResetPassword.this, R.anim.shake);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        string_token_transfered = sharedpreferences.getString("key_token","9999");
        string_email = sharedpreferences.getString("key_email","9999");

        et_token = (EditText)findViewById(R.id.et_reset_token);
        et_password = (EditText)findViewById(R.id.et_reset_new_password);
        et_verify_password = (EditText)findViewById(R.id.et_reset_verify_password);
        btn_change_password = (Button)findViewById(R.id.btn_reset_update_password);

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                string_token = et_token.getText().toString();
                string_password = et_password.getText().toString();
                string_verify_password = et_verify_password.getText().toString();

                if(!string_token.equals(string_token_transfered)){

                    et_token.startAnimation(animShake);
                    Toast.makeText(ResetPassword.this, getResources().getString(R.string.incorrect_token), Toast.LENGTH_SHORT).show();

                }else if(!string_password.equals(string_verify_password)){
                    Toast.makeText(ResetPassword.this, getResources().getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
                    et_password.startAnimation(animShake);
                    et_verify_password.startAnimation(animShake);

                }else if(string_password.equals("")){
                    Toast.makeText(ResetPassword.this, getResources().getString(R.string.provide_valid_password), Toast.LENGTH_SHORT).show();
                    et_password.startAnimation(animShake);

                }else if(string_verify_password.equals("")){
                    Toast.makeText(ResetPassword.this, getResources().getString(R.string.verify_password), Toast.LENGTH_SHORT).show();
                    et_verify_password.startAnimation(animShake);

                }else if(string_password.length()<6 || string_password.length()>12){
                    Toast.makeText(ResetPassword.this, getResources().getString(R.string.password_length), Toast.LENGTH_SHORT).show();
                    et_password.startAnimation(animShake);
                }else{


                    if(internalFunction.isDeviceConnected(ResetPassword.this)){

                        String validate_user_method = "method_update_password";
                        BackgroundTask backgroundTask = new BackgroundTask(ResetPassword.this);
                        backgroundTask.delegate = ResetPassword.this;
                        backgroundTask.execute(new String[]{validate_user_method, string_email, string_password  });

                    }else{
                        View view = findViewById(R.id.layout_reset_password);
                        showSnackbar(view);
                    }
                }



            }
        });

        findViewById(R.id.layout_reset_password).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }


        });




    }

    @Override
    public void processFinish(String str) {

        if(str.contains("password is changed")){
            Toast.makeText(ResetPassword.this, getResources().getString(R.string.password_changed), Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.remove("key_token");
            editor.apply();


            Intent i = new Intent(ResetPassword.this, Login.class);
            startActivity(i);


        }else if(str.contains("error update passwowrd")){
             SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","reset password" );  // Saving string
            editor.putString("key_subject","null" );  // Saving string
            editor.putString("key_message",str );  // Saving string

            editor.apply();

            Intent i = new Intent(ResetPassword.this, ErrorPage.class);
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
