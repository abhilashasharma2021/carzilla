package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegisterNewActivity extends AppCompatActivity {
    Button registerButton;

    CountryCodePicker ccp;
    EditText edtCode, edtMobileNumber, edtPassword;
    EditText edtWorkshopName, edtOwnerName, edtAddress, edtEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    TextView txtPrivacyPolicy,txtTermsConditions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);


        ccp = findViewById(R.id.ccp);
        edtCode = findViewById(R.id.edtCode);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtWorkshopName = findViewById(R.id.edtWorkshopName);
        edtOwnerName = findViewById(R.id.edtOwnerName);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);
        txtPrivacyPolicy = findViewById(R.id.txtPrivacyPolicy);
        txtTermsConditions = findViewById(R.id.txtTermsConditions);

        txtPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView theWebPage = new WebView(RegisterNewActivity.this);
                theWebPage.getSettings().setJavaScriptEnabled(true);
                theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
                setContentView(theWebPage);
                theWebPage.loadUrl("http://maestrosinfotech.org/car_zilla/appservices/privacy_policy_1.php");
            }
        });
        txtTermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsconditions();
            }
        });
        //  ccp.registerPhoneNumberTextView(edtCode);

        ccp.hideNameCode(Boolean.parseBoolean("0"));

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                Toast.makeText(RegisterNewActivity.this, "Updated " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.CountryCode, "+"+selectedCountry.getPhoneCode());
                editor.commit();
                edtCode.setText("+" + selectedCountry.getPhoneCode());
                Log.e("uiydfsjhnbcvxrew", selectedCountry.getIso() + "");
                Log.e("uiydfsjhnbcvxrew", selectedCountry.getPhoneCode() + "");
            }
        });

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                String strCountryCode = AppsContants.sharedpreferences.getString(AppsContants.CountryCode, "");

                Log.e("eriuydgfjkhbmnvx",strCountryCode);
              //  startActivity(new Intent(RegisterNewActivity.this, LoginNewActivity.class));


                String strMobileNumber = edtMobileNumber.getText().toString().trim();
                String strPasswordr = edtPassword.getText().toString().trim();
                String strWorkshopName = edtWorkshopName.getText().toString().trim();
                String strOwnerName = edtOwnerName.getText().toString().trim();
                String strAddress = edtAddress.getText().toString().trim();
                String strEmail = edtEmail.getText().toString().trim();
                if (strWorkshopName.equals("")) {
                    Toast.makeText(RegisterNewActivity.this, "please enter workshop name", Toast.LENGTH_SHORT).show();


                } else if (strOwnerName.equals("")) {
                    Toast.makeText(RegisterNewActivity.this, "please enter owner name", Toast.LENGTH_SHORT).show();


                } else if (strAddress.equals("")) {
                    Toast.makeText(RegisterNewActivity.this, "please enter address", Toast.LENGTH_SHORT).show();


                } else if (strEmail.equals("")) {
                    Toast.makeText(RegisterNewActivity.this, "please enter email", Toast.LENGTH_SHORT).show();


                }   else if (!strEmail.matches(emailPattern)) {
                    Toast.makeText(RegisterNewActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();


                }else {

                    signup(strWorkshopName, strOwnerName, strAddress, strEmail, strMobileNumber, strPasswordr);

                }


            }
        });

    }
    public void termsconditions() {


        final ProgressDialog dialog = new ProgressDialog(RegisterNewActivity.this);
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

                                String  StrData = response.getString("data");


                                WebView theWebPage = new WebView(RegisterNewActivity.this);
                                theWebPage.getSettings().setJavaScriptEnabled(true);
                                theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
                                setContentView(theWebPage);
                                theWebPage.loadUrl(StrData);



                                Toast.makeText(RegisterNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();





                            } else {
                                dialog.dismiss();

                                Toast.makeText(RegisterNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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



    public void signup(String strWorkshopName, String strOwnerName, String strAddress, String strEmail, String strMobileNumber, String strPasswordr) {

        final ProgressDialog dialog = new ProgressDialog(RegisterNewActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.signup)
                .addBodyParameter("shop_name", strWorkshopName)
                .addBodyParameter("owner", strOwnerName)
                .addBodyParameter("shop_address", strAddress)
                .addBodyParameter("email", strEmail)

                .setPriority(Priority.HIGH)
                .setTag("Please wait...")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("djfvlkdjvkl", response.toString());
                        try {
                            dialog.dismiss();
                            if (response.getString("message").equals("signup successfully")) {
                                dialog.dismiss();
                                Toast.makeText(RegisterNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();


                                String str = response.getString("data");
                                JSONArray jsonArray = new JSONArray(str);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strid = jsonObject.getString("garage_shop_id");

                                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                    editor.putString(AppsContants.ShopID, strid);
                                    editor.commit();


                                }

                                if (strMobileNumber.equals("")){
                                    Toast.makeText(RegisterNewActivity.this, "please enter mobile number", Toast.LENGTH_SHORT).show();
                                }else if(strPasswordr.equals("")){
                                    Toast.makeText(RegisterNewActivity.this, "please enter password", Toast.LENGTH_SHORT).show();

                                }else{
                                    Sendotp(strMobileNumber,strPasswordr);
                                }





                            } else {
                                dialog.dismiss();
                                Toast.makeText(RegisterNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();


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

    public void Sendotp(String strMobileNumber, String strPasswordr) {


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        String strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopID, "");

        final ProgressDialog dialog = new ProgressDialog(RegisterNewActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.send_otp)
                .addBodyParameter("shop_id", strShopID)
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
                            if (response.getString("message").equals("successfully sent")) {
                                dialog.dismiss();
                                Toast.makeText(RegisterNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegisterNewActivity.this, MobileVerificationACtivity.class));


                                finish();
                                String str = response.getString("data");
                                JSONArray jsonArray = new JSONArray(str);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strid = jsonObject.getString("garage_shop_id");

                                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                    editor.putString(AppsContants.ShopID, strid);
                                    editor.commit();


                                }

                                startActivity(new Intent(RegisterNewActivity.this, MobileVerificationACtivity.class));


                                finish();

                            } else {
                                dialog.dismiss();

                                Toast.makeText(RegisterNewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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
