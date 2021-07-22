package com.example.carzilla.New;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.BuildConfig;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        //  NavigationView navigationView = findViewById(R.id.nav_view);
        //View headerView = navigationView.getHeaderView(0);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        MenuItem menu_privacy = navigationView.getMenu().findItem(R.id.menu_privacy);
        MenuItem logout = navigationView.getMenu().findItem(R.id.menu_logout);
        MenuItem menu_terms = navigationView.getMenu().findItem(R.id.menu_terms);
        MenuItem menu_share = navigationView.getMenu().findItem(R.id.menu_share);
        menu_share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            }
        });
        menu_terms.setOnMenuItemClickListener(item -> {

            termsconditions();

            return true;
        });
        menu_privacy.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                WebView theWebPage = new WebView(NavigationActivity.this);
                theWebPage.getSettings().setJavaScriptEnabled(true);
                theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
                setContentView(theWebPage);
                theWebPage.loadUrl("http://maestrosinfotech.org/car_zilla/appservices/privacy_policy_1.php");
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(NavigationActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(NavigationActivity.this);
                }
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Logging you out.......")
                        /*  builder.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int arg1) {

                              }
                          });*/

                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(final DialogInterface dialog, int which) {

                                final ProgressDialog progressDialog = new ProgressDialog(NavigationActivity.this);
                                progressDialog.setMessage(getString(R.string.logging_you_out));
                                progressDialog.show();
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void run() {

                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }


                                        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                        editor.putString(AppsContants.ShopID, "");
                                        editor.putString(AppsContants.ShopUserId, "");
                                        editor.commit();

                                        Intent i = new Intent(NavigationActivity.this, NewSplashActivity.class);
                                        startActivity(i);
                                        finish();

                                    }
                                }, 2000);
                                dialog.dismiss();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setIcon(R.drawable.logout);
                // .show();
                AlertDialog alert = builder.create();
                alert.show();
                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.BLACK);
                nbutton.setBackgroundColor(Color.WHITE);

                Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);

                // pbutton.setTextColor(Color.TRANSPARENT);

                pbutton.setTextColor(Color.parseColor("#38AECC"));

                pbutton.setBackgroundColor(Color.WHITE);


                return true;
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void termsconditions() {


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        String strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");

        final ProgressDialog dialog = new ProgressDialog(NavigationActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.termsAndConditions)
                .setPriority(Priority.HIGH)
                .setTag("Please wait...")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("djfvlkdjvkl", response.toString());
                        try {
                            dialog.dismiss();
                            if (response.getString("message").equals("successful")) {
                                dialog.dismiss();

                                String StrData = response.getString("data");


                                WebView theWebPage = new WebView(NavigationActivity.this);
                                theWebPage.getSettings().setJavaScriptEnabled(true);
                                theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
                                setContentView(theWebPage);
                                theWebPage.loadUrl(StrData);


                                Toast.makeText(NavigationActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();


                            } else {
                                dialog.dismiss();

                                Toast.makeText(NavigationActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            dialog.dismiss();

                            Log.e("djfvlkdjvkl", e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();

                        Log.e("djfvlkdjvkl", anError.getMessage());
                    }

                });
    }

}