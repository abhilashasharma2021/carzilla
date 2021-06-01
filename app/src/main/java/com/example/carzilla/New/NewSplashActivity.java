package com.example.carzilla.New;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.carzilla.MainActivity;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.R;
import com.example.carzilla.SplashActivity;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class NewSplashActivity extends AppCompatActivity {
String strShopUserId="";
    private static int TIME_OUT = 3000;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_CALL_PHONE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
         strShopUserId = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");


      /*  new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void run() {
                //  Intent i1 = new Intent(SplashActivity.this, HomeActivity .class);

                startActivity(new Intent(NewSplashActivity.this,LoginNewActivity.class));
            }
        }, 1000);*/


        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void run() {
                //  Intent i1 = new Intent(SplashActivity.this, HomeActivity .class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]
                                    {Manifest.permission.INTERNET,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE},
                            CAMERA);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    if (strShopUserId.equals("")) {

                        Intent i = new Intent(NewSplashActivity.this, LoginNewActivity.class);
                        startActivity(i);
                        finish();
                    }

                 else {
                        Intent i = new Intent(NewSplashActivity.this,NavigationActivity .class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.INTERNET,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE
                            },
                            CAMERA);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    if (strShopUserId.equals("")) {

                        Intent i = new Intent(NewSplashActivity.this, LoginNewActivity.class);
                        startActivity(i);
                        finish();
                    }

                    else {
                        Intent i = new Intent(NewSplashActivity.this,NavigationActivity .class);
                        startActivity(i);
                        finish();
                    }

                }
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

                if (strShopUserId.equals("")) {

                    Intent i = new Intent(NewSplashActivity.this, LoginNewActivity.class);
                    startActivity(i);
                    finish();
                }

                else {
                    Intent i = new Intent(NewSplashActivity.this,NavigationActivity .class);
                    startActivity(i);
                    finish();
                }

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }




    }




}