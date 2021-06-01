package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AddVendorActivity extends AppCompatActivity {
    ImageView imgBack;
    EditText estVendorName, edtPhonenmber, edtEmail, vendorTaxNumber;
    Button loginButton;
String strShopID="";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
         strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");

        estVendorName = findViewById(R.id.estVendorName);
        edtPhonenmber = findViewById(R.id.edtPhonenmber);
        edtEmail = findViewById(R.id.edtEmail);
        vendorTaxNumber = findViewById(R.id.vendorTaxNumber);
        loginButton = findViewById(R.id.loginButton);
        imgBack = findViewById(R.id.imgBack);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strestVendorName = estVendorName.getText().toString().trim();
                String stredtPhonenmber = edtPhonenmber.getText().toString().trim();
                String stredtEmail = edtEmail.getText().toString().trim();
                String strvendorTaxNumber = vendorTaxNumber.getText().toString().trim();

                if (strestVendorName.equals("")) {
                    Toast.makeText(AddVendorActivity.this, "please enter vendor name", Toast.LENGTH_SHORT).show();


                } else if (stredtPhonenmber.equals("")) {
                    Toast.makeText(AddVendorActivity.this, "please enter phone number name", Toast.LENGTH_SHORT).show();


                } else if (stredtEmail.equals("")) {
                    Toast.makeText(AddVendorActivity.this, "please enter email", Toast.LENGTH_SHORT).show();


                } else if (!stredtEmail.matches(emailPattern)) {
                    Toast.makeText(AddVendorActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();

                } else if (strvendorTaxNumber.equals("")) {
                    Toast.makeText(AddVendorActivity.this, "please enter txt number", Toast.LENGTH_SHORT).show();


                } else {

                    AddVendor(strestVendorName,stredtPhonenmber,stredtEmail,strvendorTaxNumber);

                    // signup(strWorkshopName, strOwnerName, strAddress, strEmail, strMobileNumber, strPasswordr);

                }


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void AddVendor(String strestVendorName, String stredtPhonenmber, String stredtEmail, String strvendorTaxNumber) {




        final ProgressDialog dialog = new ProgressDialog(AddVendorActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.addVendor)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("name", strestVendorName)
                .addBodyParameter("phone_number", stredtPhonenmber)
                .addBodyParameter("email", stredtEmail)
                .addBodyParameter("vendorTaxNumber", strvendorTaxNumber)
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
                                Toast.makeText(AddVendorActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();


                                finish();


                            } else {
                                dialog.dismiss();

                                Toast.makeText(AddVendorActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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