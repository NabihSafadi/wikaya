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

import java.util.Random;

public class ForgotPassword extends AppCompatActivity implements AsyncResponse{

    EditText et_email;
    Button btn_send_code;

    String string_email, string_token;
    Animation animShake ;
    InternalFunction internalFunction = new InternalFunction();
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialize();
    }

    public void initialize(){
        animShake = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.shake);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        et_email = (EditText)findViewById(R.id.et_forgot_password_email);
        btn_send_code = (Button)findViewById(R.id.btn_forgot_password_send_code);


        findViewById(R.id.layout_forgot_password).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }

        });


        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                string_email = et_email.getText().toString();

               if(!string_email.contains("@") || string_email.equals("")){
                Toast.makeText(ForgotPassword.this, getResources().getString(R.string.provide_email_valid), Toast.LENGTH_SHORT).show();
                et_email.startAnimation(animShake);

            }else{



                   if(internalFunction.isDeviceConnected(ForgotPassword.this)){

                       String validate_user_method = "method_check_email_exist";
                       BackgroundTask backgroundTask = new BackgroundTask(ForgotPassword.this);
                       backgroundTask.delegate = ForgotPassword.this;
                       backgroundTask.execute(new String[]{validate_user_method, string_email  });



                   }else{
                        view = findViewById(R.id.layout_forgot_password);
                       showSnackbar(view);
                   }

               }





            }
        });
    }

    @Override
    public void processFinish(String str) {

      if(str.contains("email does not exist")){

            Toast.makeText(ForgotPassword.this,
                    getResources().getString(R.string.email_not_exist), Toast.LENGTH_LONG).show();
          et_email.startAnimation(animShake);


      }else if(str.contains("email exist")){


            Random random = new Random();

            string_token = String.format("%04d", random.nextInt(10000));

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("key_token",string_token );
            editor.putString("key_email",string_email );

            editor.apply();


            if(internalFunction.isDeviceConnected(ForgotPassword.this)){

                String validate_user_method = "method_send_token";
                BackgroundTask backgroundTask = new BackgroundTask(ForgotPassword.this);
                backgroundTask.delegate = ForgotPassword.this;
                backgroundTask.execute(new String[]{validate_user_method, string_email , string_token });



            }else{
                View view = findViewById(R.id.layout_forgot_password);
                showSnackbar(view);
            }

            Toast.makeText(ForgotPassword.this,
                    getResources().getString(R.string.reset_sent), Toast.LENGTH_LONG).show();



        }else if(str.contains("token is sent")){
          Intent i = new Intent(ForgotPassword.this, ResetPassword.class);
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
