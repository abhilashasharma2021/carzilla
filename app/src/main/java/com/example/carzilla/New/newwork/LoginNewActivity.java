package com.example.carzilla.New.newwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.New.other.Keys;
import com.example.carzilla.New.ui.WebViewSheet;
import com.example.carzilla.R;
import com.jaqa.helpers.Craft;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginNewActivity extends AppCompatActivity {
    Button loginButton;
    LinearLayout linearSIgnup;
    EditText edtCode, edtMobileNumber, edtPassword;
    CountryCodePicker ccp;
    TextView txtPrivacyPolicy, txtTermsConditions;
    String strCountryCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        loginButton = findViewById(R.id.loginButton);
        linearSIgnup = findViewById(R.id.linearSIgnup);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtPassword = findViewById(R.id.edtPassword);
        RelativeLayout flagIcon = findViewById(R.id.flagIcon);
        ccp = findViewById(R.id.ccp);
        txtPrivacyPolicy = findViewById(R.id.txtPrivacyPolicy);
        txtTermsConditions = findViewById(R.id.txtTermsConditions);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strCountryCode = AppsContants.sharedpreferences.getString(AppsContants.CountryCode, "");
        Log.e("eriuydgfjkhbmnvx", strCountryCode);

//        edtCode.setText(strCountryCode);

        txtPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView theWebPage = new WebView(LoginNewActivity.this);
                /*theWebPage.getSettings().setJavaScriptEnabled(true);
                theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
                setContentView(theWebPage);
                theWebPage.loadUrl("http://maestrosinfotech.org/car_zilla/appservices/privacy_policy_1.php");*/

                WebViewSheet webViewSheet = new WebViewSheet();
                Bundle bundle = new Bundle();
                bundle.putString("link" , "http://maestrosinfotech.org/car_zilla/appservices/privacy_policy_1.php");
                bundle.putString("title" , "Privacy Poilicy");
                webViewSheet.setArguments(bundle);
                webViewSheet.show(getSupportFragmentManager(),"tag");

            }
        });
        txtTermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsconditions();
            }
        });


        //    ccp.hideNameCode(Boolean.parseBoolean("0"));
        if (!strCountryCode.isEmpty()) {
            ccp.setCountryForPhoneCode(Integer.parseInt(strCountryCode));
        }

        ccp.setOnCountryChangeListener(selectedCountry -> {
            //edtCode.setText("+" + selectedCountry.getPhoneCode());
            Log.e("uiydfsjhnbcvxrew", selectedCountry.getIso() + "");
            Log.e("uiydfsjhnbcvxrew", selectedCountry.getPhoneCode() + "");
            strCountryCode = selectedCountry.getPhoneCode();
        });

        linearSIgnup.setOnClickListener(v -> {
            startActivity(new Intent(LoginNewActivity.this, RegisterNewActivity.class));

        } );

        loginButton.setOnClickListener(v -> {
            boolean aBoolean = Craft.INSTANCE.isValidate(edtMobileNumber, "Required", true, 10).isValidate(edtPassword, "Required", false, 20).getValidatedFields();
            if (aBoolean) {
                login(edtMobileNumber.getText().toString().trim(), edtPassword.getText().toString().trim());
            }

        });


    }


    public void termsconditions() {

        final ProgressDialog dialog = new ProgressDialog(LoginNewActivity.this);
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
/*

                                WebView theWebPage = new WebView(LoginNewActivity.this);
                                theWebPage.getSettings().setJavaScriptEnabled(true);
                                theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
                                setContentView(theWebPage);
                                theWebPage.loadUrl(StrData);*/


                                WebViewSheet webViewSheet = new WebViewSheet();
                                Bundle bundle = new Bundle();
                                bundle.putString("link" , StrData);
                                bundle.putString("title" , "Terms Of Services");
                                webViewSheet.setArguments(bundle);
                                webViewSheet.show(getSupportFragmentManager(),"tag");



                            } else {
                                dialog.dismiss();

                                Toast.makeText(LoginNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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


    public void login(String strMobileNumber, String strPasswordr) {


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        String strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopID, "");

        final ProgressDialog dialog = new ProgressDialog(LoginNewActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.login)
                .addBodyParameter("mobile", strMobileNumber)
                .addBodyParameter("password", strPasswordr)
                .setPriority(Priority.HIGH)
                .setTag("Please wait...")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("djfvlkdjvkl", response.toString());
                        try {
                            dialog.dismiss();
                            if (response.getString("message").equals("login successfully")) {
                                dialog.dismiss();
                                Toast.makeText(LoginNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(LoginNewActivity.this, MobileVerificationACtivity.class));


                                finish();
                                String str = response.getString("data");
                                JSONArray jsonArray = new JSONArray(str);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strid = jsonObject.getString("garage_shop_id");

                                    Craft.INSTANCE.putKey(LoginNewActivity.this, Keys.ShopUserId,strid);
                                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                    editor.putString(AppsContants.ShopUserId, strid);
                                    editor.putString(AppsContants.CountryCode, strCountryCode);
                                    editor.apply();


                                }

                                startActivity(new Intent(LoginNewActivity.this, HomeActivity.class));


                                finish();

                            } else {
                                dialog.dismiss();

                                Toast.makeText(LoginNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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