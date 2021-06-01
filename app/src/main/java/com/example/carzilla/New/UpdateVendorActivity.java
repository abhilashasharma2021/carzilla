package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.carzilla.New.adapter.ShowVendorAdapter;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdateVendorActivity extends AppCompatActivity {
String strShopID="",strVendorIDd="";


    ImageView imgBack;
    EditText estVendorName, edtPhonenmber, edtEmail, vendorTaxNumber;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vendor);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");
       strVendorIDd = AppsContants.sharedpreferences.getString(AppsContants.VendorIDd, "");


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
                    Toast.makeText(UpdateVendorActivity.this, "please enter vendor name", Toast.LENGTH_SHORT).show();


                } else if (stredtPhonenmber.equals("")) {
                    Toast.makeText(UpdateVendorActivity.this, "please enter phone number name", Toast.LENGTH_SHORT).show();


                } else if (stredtEmail.equals("")) {
                    Toast.makeText(UpdateVendorActivity.this, "please enter email", Toast.LENGTH_SHORT).show();


                } else if (strvendorTaxNumber.equals("")) {
                    Toast.makeText(UpdateVendorActivity.this, "please enter txt number", Toast.LENGTH_SHORT).show();


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
        show_vendor();
    }


    public void show_vendor() {
        final ProgressDialog dialog = new ProgressDialog(UpdateVendorActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.showVendor)
                .addBodyParameter("shop_id",strShopID)
                .addBodyParameter("vendor_id",strVendorIDd)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            dialog.dismiss();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);




                                String vendor_name = jsonObject.getString("vendor_name");
                                String email_id = jsonObject.getString("email_id");
                                String phone_number = jsonObject.getString("phone_number");
                                String vandor_tax_number = jsonObject.getString("vandor_tax_number");
                                   estVendorName.setText(vendor_name);
                                edtPhonenmber.setText(phone_number);
                                edtEmail.setText(email_id);
                                vendorTaxNumber.setText(vandor_tax_number);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();


                    }
                });


    }


    public void AddVendor(String strestVendorName, String stredtPhonenmber, String stredtEmail, String strvendorTaxNumber) {




        final ProgressDialog dialog = new ProgressDialog(UpdateVendorActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.updateVendor)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("vendor_id", strVendorIDd)
                .addBodyParameter("vendor_name", strestVendorName)
                .addBodyParameter("phone_number", stredtPhonenmber)
                .addBodyParameter("email_id", stredtEmail)
                .addBodyParameter("vandor_tax_number", strvendorTaxNumber)
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
                                Toast.makeText(UpdateVendorActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();


                                finish();


                            } else {
                                dialog.dismiss();

                                Toast.makeText(UpdateVendorActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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