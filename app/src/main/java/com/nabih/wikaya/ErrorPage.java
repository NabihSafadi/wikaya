package com.nabih.wikaya;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorPage extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String string_message, string_subject, string_coming_from = "";
    TextView textView_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_page);

        initialize();
    }

    public void initialize(){
          textView_error = (TextView) findViewById(R.id.error_textView);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        string_coming_from = sharedpreferences.getString("key_coming_from","default");
        string_message = sharedpreferences.getString("key_message","default_message");


        textView_error.setText(string_coming_from + "\n" + string_message);



    }




}
