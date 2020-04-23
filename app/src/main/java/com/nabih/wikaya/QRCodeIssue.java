package com.nabih.wikaya;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeIssue extends AppCompatActivity implements AsyncResponse{
    String TAG = "GenerateQRCode";
    ImageView iv_qr;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    Button btn_qr_send, btn_qr_delete, btn_qr_done;

    String string_information_entrance, string_place_id, string_email, string_place_name;
    Bitmap bitmap_entrance ;
    QRGEncoder grgEncoder_entrance;
    InternalFunction internalFunction = new InternalFunction();

    TextView tv_qr_place_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_issue);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        iv_qr = (ImageView) findViewById(R.id.iv_qr);
        btn_qr_send = (Button) findViewById(R.id.btn_qr_send);
        btn_qr_done = (Button) findViewById(R.id.btn_qr_done);
        btn_qr_delete = (Button) findViewById(R.id.btn_qr_delete);
         tv_qr_place_name = (TextView) findViewById(R.id.tv_qr_place_name);

        string_place_id = sharedpreferences.getString("key_place_id","");
        string_email = sharedpreferences.getString("key_email","");
        string_place_name = sharedpreferences.getString("key_place_name","");

        tv_qr_place_name.setText(string_place_name);
        string_information_entrance = "<place_id_start>" + string_place_id + "<place_id_end>";


        if (string_information_entrance.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            grgEncoder_entrance = new QRGEncoder(
                    string_information_entrance, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap_entrance = grgEncoder_entrance.encodeAsBitmap();
                iv_qr.setImageBitmap(bitmap_entrance);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }




        } else {

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("key_coming_from","QR code issue" );
            editor.putString("key_subject","Qr code Issuance" );
            editor.putString("key_message","could not be issued" );
            editor.apply();

            Intent i = new Intent(QRCodeIssue.this, ErrorPage.class);
            startActivity(i);
        }

        btn_qr_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(isWriteStoragePermissionGranted()){
                    String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap_entrance,"entrance", null);
                    Uri bitmapUri = Uri.parse(bitmapPath);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/png");

                    intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ string_email});
                    intent.putExtra(Intent.EXTRA_SUBJECT, string_place_name);


                    startActivity(Intent.createChooser(intent, "Share"));

                }


            }

        });

        btn_qr_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(QRCodeIssue.this);
                alert.setTitle(getResources().getString(R.string.delete_place));
                alert.setMessage(getResources().getString(R.string.canot_be_undone));

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        finish();
                        startActivity(getIntent());

                    }


                });

                alert.setNeutralButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {



                        if(internalFunction.isDeviceConnected(QRCodeIssue.this)){


                            String method_add_like_list = "method_delete";
                            BackgroundTask backgroundTask = new BackgroundTask(QRCodeIssue.this);
                            backgroundTask.delegate = QRCodeIssue.this;
                            backgroundTask.execute(new String[]{method_add_like_list,string_place_id});


                        }else{
                            View view = findViewById(R.id.layout_my_places);
                            showSnackbar(view);
                        }




                    }



                });

                alert.show();



            }

        });



        btn_qr_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("key_coming_from");
                editor.remove("key_subject");
                editor.remove("key_message");
                editor.remove("key_place_name");
                editor.remove("key_place_address");
                editor.remove("key_longitude");
                editor.remove("key_latitude");
                editor.apply();








                Intent i = new Intent(QRCodeIssue.this, MainActivity.class);
                startActivity(i);



            }

        });





    }





    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                Toast.makeText(QRCodeIssue.this, getResources().getString(R.string.enable_storage), Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent i = new Intent(QRCodeIssue.this, MainActivity.class);
        startActivity(i);
        return;
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
          if(str.contains("place_deleted")){


              Toast.makeText(QRCodeIssue.this, getResources().getString(R.string.place_deleted), Toast.LENGTH_SHORT).show();
              SharedPreferences.Editor editor = sharedpreferences.edit();
              editor.remove("key_place_id");

              editor.apply();

              Intent i = new Intent(QRCodeIssue.this, MainActivity.class);
              startActivity(i);

        }else if(str.contains("error deleting place")){
              SharedPreferences.Editor editor = sharedpreferences.edit();

              editor.putString("key_coming_from","QR code issue" );  // Saving string
              editor.putString("key_subject","error deleting place" );  // Saving string
              editor.putString("key_message",str );  // Saving string

              editor.apply();

              Intent i = new Intent(QRCodeIssue.this, ErrorPage.class);
              startActivity(i);
          }
    }
}

