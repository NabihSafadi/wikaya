package com.nabih.wikaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity   implements AsyncResponse{

    String string_email, string_password, string_user_id, string_first_name, string_last_name, string_phone_number;
    EditText et_email, et_password;
    Button btn_forgot_password, btn_login, btn_register;
    Animation animShake ;
    InternalFunction internalFunction = new InternalFunction();
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    ImageButton ib_show_hide_password;
    boolean is_password_visible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    public void initialize(){
        et_email = (EditText) findViewById(R.id.et_login_email);
        et_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login_login);
        btn_forgot_password = (Button) findViewById(R.id.btn_login_forgot_passwrod);
        btn_register = (Button) findViewById(R.id.btn_login_register);
        ib_show_hide_password = (ImageButton)findViewById(R.id.ib_show_hide_password);

        animShake = AnimationUtils.loadAnimation(Login.this, R.anim.shake);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        findViewById(R.id.layout_login).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }

        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                string_email = et_email.getText().toString();
                string_password = et_password.getText().toString();
                if(!string_email.contains("@") || string_email.equals("")){
                    Toast.makeText(Login.this, getResources().getString(R.string.provide_email_valid), Toast.LENGTH_SHORT).show();
                    et_email.startAnimation(animShake);

                }else if(string_password.equals("")){
                    Toast.makeText(Login.this, getResources().getString(R.string.provide_valid_password), Toast.LENGTH_SHORT).show();
                    et_password.startAnimation(animShake);

                }else{

                    if(internalFunction.isDeviceConnected(Login.this)){

                        String validate_user_method = "method_login";
                        BackgroundTask backgroundTask = new BackgroundTask(Login.this);
                        backgroundTask.delegate = Login.this;
                        backgroundTask.execute(new String[]{validate_user_method, string_email, string_password });

                    }else{
                        view = findViewById(R.id.layout_login);
                        showSnackbar(view);
                    }

                }


            }
        });


        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);


            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);


            }
        });


        ib_show_hide_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(is_password_visible){
                    et_password.setTransformationMethod(null);

                    is_password_visible = false;

                }else{

                    et_password.setTransformationMethod(new PasswordTransformationMethod());

                    is_password_visible = true;

                }

                et_password.setSelection(et_password.getText().length());
            }
        });


    }



    @Override
    public void processFinish(String output) {

         SharedPreferences.Editor editor = sharedpreferences.edit();


        if (output.contains("email does not exist")) {

            Toast.makeText(Login.this,
                    getResources().getString(R.string.email_not_exist), Toast.LENGTH_LONG).show();

            et_email.startAnimation(animShake);


        } else if (output.contains("incorrect password")) {

            Toast.makeText(Login.this,
                    getResources().getString(R.string.incorrect_password), Toast.LENGTH_LONG).show();
            et_password.startAnimation(animShake);

        }else if (output.contains("user_id_start")) {


            string_user_id = internalFunction.getSubString(output, "user_id_end", "user_id_start");
            string_first_name = internalFunction.getSubString(output, "first_name_end", "first_name_start");
            string_last_name = internalFunction.getSubString(output, "last_name_end", "last_name_start");
            string_phone_number = internalFunction.getSubString(output, "phone_number_end", "phone_number_start");


            editor.putString("key_user_id", string_user_id);
            editor.putString("key_email", string_email);
            editor.putString("key_first_name", string_first_name);
            editor.putString("key_last_name", string_last_name);
            editor.putString("key_phone_number", string_phone_number);



            // Saving string
            editor.apply(); // commit changes

            Intent i = new Intent(Login.this, MainActivity.class);
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
