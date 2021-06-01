package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MobileVerificationACtivity extends AppCompatActivity {
Button verifyButton;
EditText edtOtp;
    TextView txtPrivacyPolicy,txtTermsConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification_a_ctivity);


        verifyButton = findViewById(R.id.verifyButton);
        edtOtp = findViewById(R.id.edtOtp);
        txtPrivacyPolicy = findViewById(R.id.txtPrivacyPolicy);
        txtTermsConditions = findViewById(R.id.txtTermsConditions);
        txtPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView theWebPage = new WebView(MobileVerificationACtivity.this);
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


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String strOtp = edtOtp.getText().toString().trim();

                if (strOtp.equals("")){
                    Toast.makeText(MobileVerificationACtivity.this, "please enter otp", Toast.LENGTH_SHORT).show();
                }else{
                     verifyotp(strOtp);

                    }
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

                                String  StrData = response.getString("data");


                                WebView theWebPage = new WebView(MobileVerificationACtivity.this);
                                theWebPage.getSettings().setJavaScriptEnabled(true);
                                theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
                                setContentView(theWebPage);
                                theWebPage.loadUrl(StrData);



                                Toast.makeText(MobileVerificationACtivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();





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
        dialog.setMessage("please wait..");
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
                                    editor.commit();


                                }

                                startActivity(new Intent(MobileVerificationACtivity.this,NavigationActivity.class));


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