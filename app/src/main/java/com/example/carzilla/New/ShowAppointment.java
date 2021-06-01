package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.adapter.ShowCreateeAdapter;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowAppointment extends AppCompatActivity {
    ImageView imgBack,imgAdd;


    RecyclerView rec_favourite;
    RecyclerView.LayoutManager layoutManager;
    ShowCreateeAdapter favouriteAdapter;

    ArrayList<ShowEmployeeGetSet> arrayListFavourite = new ArrayList<>();
    String strShopID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointment);
        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");


        imgBack = findViewById(R.id.imgBack);
        imgAdd = findViewById(R.id.imgAdd);


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAppointment.this, AddAppointmentActivity.class));

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");

        rec_favourite = findViewById(R.id.rec_favourite);
        layoutManager = new GridLayoutManager(ShowAppointment.this, 1, RecyclerView.VERTICAL, true);
        rec_favourite.setLayoutManager(layoutManager);
        show_Favourite();

    }


    @Override
    protected void onResume() {
        super.onResume();
        show_Favourite();
    }

    public void show_Favourite() {
        final ProgressDialog dialog = new ProgressDialog(ShowAppointment.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.showAppointment)
                .addBodyParameter("shop_id",strShopID)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fjhgdjgf",response.toString()+"");
                        try {
                            dialog.dismiss();
                            arrayListFavourite=new ArrayList<>();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);



                                ShowEmployeeGetSet favouriteModal = new ShowEmployeeGetSet();
                                favouriteModal.setAppointment_id(jsonObject.getString("appointment_id"));
                                favouriteModal.setCreated_at(jsonObject.getString("appointment_day"));
                                favouriteModal.setDate(jsonObject.getString("appointment_date"));
                                favouriteModal.setCustomer_name(jsonObject.getString("customer_name"));
                                favouriteModal.setPhone_number(jsonObject.getString("phone_number"));
                                arrayListFavourite.add(favouriteModal);


                            }


                          /*  next order statud is shipped out-tbhi review button show hogi.
                            witgdra amount  100 se uper new hoga.
                                    purchase history mme bhi video shoe honge*/



                           // layoutManager = new GridLayoutManager(ShowAppointment.this, 1, RecyclerView.VERTICAL, false);
                           // rec_favourite.setLayoutManager(layoutManager);
                            favouriteAdapter = new ShowCreateeAdapter(ShowAppointment.this, arrayListFavourite);
                            rec_favourite.setAdapter(favouriteAdapter);

                            dialog.dismiss();





                        } catch (JSONException e) {
                            Log.e("fjhgdjgf",e.getMessage()+"");
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("fjhgdjgf",anError.getMessage()+"");

                        dialog.dismiss();


                    }
                });


    }
}