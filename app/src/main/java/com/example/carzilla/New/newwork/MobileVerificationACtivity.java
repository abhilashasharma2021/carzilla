package com.example.carzilla.New.newwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.NavigationActivity;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.New.other.Keys;
import com.example.carzilla.New.ui.WebViewSheet;
import com.example.carzilla.R;
import com.goodiebag.pinview.Pinview;
import com.jaqa.helpers.Craft;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MobileVerificationACtivity extends AppCompatActivity {
    Button verifyButton;
    EditText edtOtp;
    TextView txtPrivacyPolicy, txtTermsConditions;
    Pinview pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification_a_ctivity);
        pin = findViewById(R.id.pinview);
        verifyButton = findViewById(R.id.verifyButton);
        edtOtp = findViewById(R.id.edtOtp);
        txtPrivacyPolicy = findViewById(R.id.txtPrivacyPolicy);
        txtTermsConditions = findViewById(R.id.txtTermsConditions);
        TextView textViewTimer = findViewById(R.id.textViewTimer);
        TextView textViewShowMobileNumber = findViewById(R.id.textViewShowMobileNumber);


        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                textViewTimer.setEnabled(false);
                textViewTimer.setText("Resend Otp In: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                textViewTimer.setEnabled(true);
                textViewTimer.setText("Resend Otp ?");
            }

        }.start();


        textViewShowMobileNumber.setText(Craft.INSTANCE.getKey(MobileVerificationACtivity.this, Keys.userMobileNumber));
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Make api calls here or what not
                // Toast.makeText(MobileVerificationACtivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        txtPrivacyPolicy.setOnClickListener(v -> {
            WebViewSheet webViewSheet = new WebViewSheet();
            Bundle bundle = new Bundle();
            bundle.putString("link", "http://maestrosinfotech.org/car_zilla/appservices/privacy_policy_1.php");
            bundle.putString("title", "Privacy And Policy");
            webViewSheet.setArguments(bundle);
            webViewSheet.show(getSupportFragmentManager(), "tag");


        });
        txtTermsConditions.setOnClickListener(v -> termsconditions());
        verifyButton.setOnClickListener(v -> {


            String strOtp = edtOtp.getText().toString().trim();

            if (pin.getValue().length() == 4) {
                verifyotp(pin.getValue());

            } else {
                Toast.makeText(MobileVerificationACtivity.this, "Please enter 4  digit otp", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void termsconditions() {


        final ProgressDialog dialog = new ProgressDialog(MobileVerificationACtivity.this);
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
                                bundle.putString("title", "Terms And Condition");
                                webViewSheet.setArguments(bundle);
                                webViewSheet.show(getSupportFragmentManager(), "tag");


                            } else {
                                dialog.dismiss();

                                Toast.makeText(MobileVerificationACtivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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

    public void verifyotp(String strOtp) {
        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        String strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopID, "");
        final ProgressDialog dialog = new ProgressDialog(MobileVerificationACtivity.this);
        dialog.setMessage("please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.otp_verify)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("otp", strOtp)
                .setPriority(Priority.HIGH)
                .setTag("Please wait...")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("djfvlkdjvkl", response.toString());
                        try {
                            dialog.dismiss();
                            if (response.getString("message").equals("OTP verified")) {
                                dialog.dismiss();
                                Toast.makeText(MobileVerificationACtivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                                String str = response.getString("data");
                                JSONArray jsonArray = new JSONArray(str);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strid = jsonObject.getString("garage_shop_id");
                                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                    editor.putString(AppsContants.ShopUserId, strid);
                                    editor.apply();

                                }
                                startActivity(new Intent(MobileVerificationACtivity.this, NavigationActivity.class));
                                finish();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(MobileVerificationACtivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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