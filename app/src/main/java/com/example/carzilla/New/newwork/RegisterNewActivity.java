package com.example.carzilla.New.newwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.New.ui.WebViewSheet;
import com.example.carzilla.R;
import com.jaqa.helpers.Craft;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterNewActivity extends AppCompatActivity {
    Button registerButton;

    CountryCodePicker ccp;
    EditText edtMobileNumber, edtPassword;
    EditText edtWorkshopName, edtOwnerName, edtAddress, edtEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    TextView txtPrivacyPolicy, txtTermsConditions;
    String selectedCountryCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);


        ccp = findViewById(R.id.ccp);
        //     edtCode = findViewById(R.id.edtCode);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtWorkshopName = findViewById(R.id.edtWorkshopName);
        edtOwnerName = findViewById(R.id.edtOwnerName);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);
        txtPrivacyPolicy = findViewById(R.id.txtPrivacyPolicy);
        txtTermsConditions = findViewById(R.id.txtTermsConditions);
        TextView textViewLogin = findViewById(R.id.textViewLogin);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterNewActivity.this, LoginNewActivity.class));
                finishAffinity();
            }
        });

        txtPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                WebViewSheet webViewSheet = new WebViewSheet();
                Bundle bundle = new Bundle();
                bundle.putString("link", "http://maestrosinfotech.org/car_zilla/appservices/privacy_policy_1.php");
                bundle.putString("title", "Privacy And Policy");
                webViewSheet.setArguments(bundle);
                webViewSheet.show(getSupportFragmentManager(), "tag");
            }
        });
        txtTermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsconditions();
            }
        });


        ccp.hideNameCode(Boolean.parseBoolean("0"));
        selectedCountryCode = "+" + ccp.getSelectedCountryCode();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                Toast.makeText(RegisterNewActivity.this, "Updated " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();
                selectedCountryCode = "+" + selectedCountry.getPhoneCode();
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.CountryCode, "+" + selectedCountry.getPhoneCode());
                editor.commit();

            }
        });

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                String strCountryCode = AppsContants.sharedpreferences.getString(AppsContants.CountryCode, "");
                boolean isValidateFields = Craft.INSTANCE.isValidate(edtWorkshopName, "Required", false, 0)
                        .isValidate(edtOwnerName, "Required", false, 0)
                        .isValidate(edtAddress, "Required", false, 0)
                        .isValidate(edtEmail, "Required", false, 0)
                        .isValidate(edtMobileNumber, "Required", true, 10)
                        .isValidate(edtPassword, "Required", false, 20)
                        .getValidatedFields();

                if (isValidateFields) {
                    signup(edtWorkshopName.getText().toString().trim(),
                            edtOwnerName.getText().toString().trim(),
                            edtAddress.getText().toString().trim(),
                            edtEmail.getText().toString().trim(),
                            edtMobileNumber.getText().toString().trim(),
                            edtPassword.getText().toString().trim());

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

                                String StrData = response.getString("data");

                                WebViewSheet webViewSheet = new WebViewSheet();
                                Bundle bundle = new Bundle();
                                bundle.putString("link", StrData);
                                bundle.putString("title", "Terms And Conditions");
                                webViewSheet.setArguments(bundle);
                                webViewSheet.show(getSupportFragmentManager(), "tag");
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

                                if (strMobileNumber.equals("")) {
                                    Toast.makeText(RegisterNewActivity.this, "please enter mobile number", Toast.LENGTH_SHORT).show();
                                } else if (strPasswordr.equals("")) {
                                    Toast.makeText(RegisterNewActivity.this, "please enter password", Toast.LENGTH_SHORT).show();

                                } else {
                                    Sendotp(strMobileNumber, strPasswordr);
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
                                    Craft.INSTANCE.putKey(RegisterNewActivity.this, "userMobileNumber", selectedCountryCode + edtMobileNumber.getText().toString());

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
