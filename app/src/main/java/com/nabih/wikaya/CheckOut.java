package com.nabih.wikaya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class CheckOut extends AppCompatActivity  implements AsyncResponse{

    TextView tv_co_title, tv_co_place_name, tv_co_property_type, tv_co_property_number, tv_co_place_address, tv_co_check_in_time;
    Button btn_co_check_out;
    LinearLayout ll_co;
    ImageView iv_co_shss;


    String string_user_id;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    InternalFunction internalFunction = new InternalFunction();

    String string_place_name, string_place_address, string_location_type, string_residential_type, string_section, string_property_number,
            string_check_in_time, string_place_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        tv_co_place_name = (TextView) findViewById(R.id.tv_co_place_name);
        tv_co_property_type = (TextView) findViewById(R.id.tv_co_property_type);
        tv_co_property_number = (TextView) findViewById(R.id.tv_co_property_number);
        tv_co_place_address = (TextView) findViewById(R.id.tv_co_place_address);
        tv_co_title = (TextView) findViewById(R.id.tv_co_title);
        tv_co_check_in_time = (TextView) findViewById(R.id.tv_co_check_in_time);
        btn_co_check_out = (Button) findViewById(R.id.btn_co_check_out);
        iv_co_shss = (ImageView) findViewById(R.id.iv_co_shss);
        ll_co = (LinearLayout) findViewById(R.id.ll_co);

        iv_co_shss.setVisibility(View.GONE);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        string_user_id = sharedpreferences.getString("key_user_id","");

        get_checked_in_data();

        btn_co_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckOut.this);
                builder1.setTitle(R.string.check_out);
                builder1.setMessage(R.string.are_you_sure);
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(internalFunction.isDeviceConnected(CheckOut.this)){

                                    String validate_user_method = "method_check_out";
                                    BackgroundTask backgroundTask = new BackgroundTask(CheckOut.this);
                                    backgroundTask.delegate = CheckOut.this;
                                    backgroundTask.execute(new String[]{validate_user_method, string_user_id, string_place_id });

                                }else{
                                    View view = findViewById(R.id.layout_login);
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

    }

    public void get_checked_in_data(){

        if(internalFunction.isDeviceConnected(CheckOut.this)){

            String validate_user_method = "method_get_checked_in_data";
            BackgroundTask backgroundTask = new BackgroundTask(CheckOut.this);
            backgroundTask.delegate = CheckOut.this;
            backgroundTask.execute(new String[]{validate_user_method, string_user_id });

        }else{
           View view = findViewById(R.id.layout_login);
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

    @Override
    public void processFinish(String str) {

        if(str.contains("does_not_exist")){

            tv_co_title.setText(R.string.not_checked_in);
            ll_co.setVisibility(View.GONE);
            iv_co_shss.setVisibility(View.VISIBLE);




        }else if(str.contains("place_name_start")){


            ll_co.setVisibility(View.VISIBLE);

            string_place_name = internalFunction.getSubString(str, "place_name_end", "place_name_start");
            string_place_address = internalFunction.getSubString(str, "place_address_end", "place_address_start");
            string_location_type = internalFunction.getSubString(str, "location_type_end", "location_type_start");
            string_residential_type = internalFunction.getSubString(str, "residential_type_end", "residential_type_start");
            string_section = internalFunction.getSubString(str, "section_end", "section_start");
            string_property_number = internalFunction.getSubString(str, "property_number_end", "property_number_start");
            string_check_in_time = internalFunction.getSubString(str, "check_in_time_end", "check_in_time_start");
            string_place_id = internalFunction.getSubString(str, "place_id_end", "place_id_start");


            if(string_section.equals("")){
                tv_co_place_name.setText(string_place_name);
            }else{
                tv_co_place_name.setText(string_place_name + " - Section: " + string_section);
            }


            
            if(string_location_type.equals("Other")){
                tv_co_property_type.setVisibility(View.GONE);
                tv_co_property_number.setVisibility(View.GONE);

            }else {
                tv_co_property_type.setVisibility(View.VISIBLE);
                tv_co_property_type.setText(string_residential_type);
                tv_co_property_number.setVisibility(View.VISIBLE);
                tv_co_property_number.setText(string_property_number);


            }

            tv_co_place_address.setText(string_place_address);
           tv_co_check_in_time.setText(internalFunction.formatDate("yyyy-MM-dd HH:mm:ss", "HH:mm:ss dd/MMMM/yyyy ", string_check_in_time));


        }else if(str.contains("checked_out")){


            Toast.makeText(CheckOut.this, getResources().getString(R.string.checked_out)+ " " + string_place_name, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(CheckOut.this, MainActivity.class);
            startActivity(i);



        }

    }


}
