package com.nabih.wikaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyHealthStatus extends AppCompatActivity implements AsyncResponse {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    String string_user_id, string_health_status, string_time_potential, string_formated_date, string_msg_p_1, string_msg_p_2,
            string_time_now, string_time_now_formated, string_days, string_hours, string_minutes, string_seconds, string_infected_message,
            string_healthy_message;
    InternalFunction internalFunction = new InternalFunction();

    TextView tv_mhs_days_left, tv_mhs_message, tv_mhs_count_down;
    ImageView iv_mhs_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_health_status);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tv_mhs_days_left = (TextView) findViewById(R.id.tv_mhs_days_left);
        tv_mhs_message = (TextView) findViewById(R.id.tv_mhs_message);
        tv_mhs_count_down = (TextView) findViewById(R.id.tv_mhs_count_down);
        iv_mhs_icon = (ImageView) findViewById(R.id.iv_mhs_icon);

        string_user_id = sharedpreferences.getString("key_user_id", "");
        string_msg_p_1 = sharedpreferences.getString("key_msg_p_1", "");
        string_msg_p_2 = sharedpreferences.getString("key_msg_p_2", "");
        string_healthy_message = sharedpreferences.getString("key_healthy_message", "");
        string_infected_message = sharedpreferences.getString("key_infected_message", "");

        get_health_status();
    }

    public void get_health_status() {
        if (internalFunction.isDeviceConnected(MyHealthStatus.this)) {

            String validate_user_method = "method_get_health_status_for_main_activity";
            BackgroundTask backgroundTask = new BackgroundTask(MyHealthStatus.this);
            backgroundTask.delegate = MyHealthStatus.this;
            backgroundTask.execute(new String[]{validate_user_method, string_user_id});

        } else {
            View view = findViewById(R.id.layout_my_health_status);
            showSnackbar(view);
        }
    }

    public void get_update_to_hs_clean() {
        if (internalFunction.isDeviceConnected(MyHealthStatus.this)) {

            String validate_user_method = "method_update_to_hs_clean";
            BackgroundTask backgroundTask = new BackgroundTask(MyHealthStatus.this);
            backgroundTask.delegate = MyHealthStatus.this;
            backgroundTask.execute(new String[]{validate_user_method, string_user_id});

        } else {
            View view = findViewById(R.id.layout_my_health_status);
            showSnackbar(view);
        }
    }


    @Override
    public void processFinish(String str) {




        if(str.contains("health_status_start")){
            string_health_status = internalFunction.getSubString(str, "health_status_end", "health_status_start");
            string_time_potential = internalFunction.getSubString(str, "time_health_status_end", "time_health_status_start");
            string_formated_date = internalFunction.formatDate("yyyy-MM-dd HH:mm:ss", "HH:mm:ss dd/MMMM/yyyy ", string_time_potential);
            string_time_now = internalFunction.getSubString(str, "time_now_end", "time_now_start");
            string_time_now_formated = internalFunction.formatDate("yyyy/MM/dd HH:mm:ss", "HH:mm:ss dd/MMMM/yyyy ", string_time_now);

            if (string_health_status.equals("hs_clean")) {

                iv_mhs_icon.setImageResource(R.drawable.healthy);
                string_healthy_message = string_healthy_message.replace("<new line>", "\n");
                tv_mhs_message.setText(string_healthy_message);

                tv_mhs_count_down.setVisibility(View.GONE);

            } else if (string_health_status.equals("hs_potential")) {


                iv_mhs_icon.setImageResource(R.drawable.stayhomestaysafe);


                String string_formated_message = string_msg_p_1 + string_formated_date + string_msg_p_2;
                string_formated_message = string_formated_message.replace("<new line>", "\n");

                tv_mhs_message.setText(string_formated_message);


                try {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MMMM/yyyy ");
                    Calendar c = Calendar.getInstance();
                    c.setTime(format.parse(string_formated_date));
                    c.add(Calendar.DATE, 14);
                    format = new SimpleDateFormat("HH:mm:ss dd/MMMM/yyyy ");
                    Date resultdate = new Date(c.getTimeInMillis());
                    String string_new_date = format.format(resultdate);


                    Date date_start = format.parse(string_time_now_formated);
                    Date date_after_14_days = format.parse(string_new_date);

                    final long diffInMs = date_after_14_days.getTime() - date_start.getTime();
                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
                    if (diffInSec <= 0) {

                        //update to hs_clean

                        get_update_to_hs_clean();

                    } else {
                        new CountDownTimer(diffInMs, 1000) {


                            public void onTick(long millisUntilFinished) {

                                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);



                                if (days != 1) {
                                    string_days = " Days";
                                } else{
                                    string_days = " Day";

                                }


                                if (hours != 1) {
                                    string_hours = " Hours";
                                } else {
                                    string_hours = " Hour";

                                }

                                if (minutes != 1) {
                                    string_minutes = " Minutes";
                                } else {
                                    string_minutes = " Minute";

                                }

                                if (seconds != 1) {
                                    string_seconds = " Seconds";
                                } else {
                                    string_seconds = " Second";

                                }

                                String string_display_text = "Self quarantine count down: \n"
                                        + days + string_days + "\n"
                                        + hours + string_hours + "\n"
                                        + minutes + string_minutes + " \n"
                                        + seconds + string_seconds;




                                tv_mhs_count_down.setText(string_display_text);




                            }

                            public void onFinish() {

                                get_update_to_hs_clean();



                            }

                        }.start();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            } else if (string_health_status.equals("hs_infected")) {

                iv_mhs_icon.setImageResource(R.drawable.infected);
                string_infected_message = string_infected_message.replace("<new line>", "\n");
                tv_mhs_message.setText(string_infected_message);

                tv_mhs_count_down.setVisibility(View.GONE);

            }
        }else if(str.contains("health_status_is_updated")){

            finish();
            startActivity(getIntent());


        }else if(str.contains("error update health status")){

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","health status" );
            editor.putString("key_subject","couldnt update health status to clean" );
            editor.putString("key_message",str );
            editor.apply();

            Intent i = new Intent(MyHealthStatus.this, ErrorPage.class);
            startActivity(i);

        }


    }


    public void showSnackbar(View view) {

        Snackbar snackbar = Snackbar
                .make(view, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(MyHealthStatus.this, MainActivity.class);
        startActivity(i);
    }
}
