package com.example.carzilla.New;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ShowCreatedOrderActivity extends AppCompatActivity {
    RecyclerView rec_favourite;
    RecyclerView.LayoutManager layoutManager;
    ShowCreateeAdapter favouriteAdapter;

    ArrayList<ShowEmployeeGetSet> arrayListFavourite = new ArrayList<>();
    ImageView imgAdd;
    ImageView imgBack;
    String strShopID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_created_order);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");

        imgAdd = findViewById(R.id.imgAdd);
        rec_favourite = findViewById(R.id.rec_favourite);
        layoutManager = new GridLayoutManager(ShowCreatedOrderActivity.this, 1, RecyclerView.VERTICAL, false);
        rec_favourite.setLayoutManager(layoutManager);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowCreatedOrderActivity.this, RepairOrderActivity.class));
            }
        });
        show_Favourite();

    }

    @Override
    protected void onResume() {
        super.onResume();
        show_Favourite();
    }

    public void show_Favourite() {
        final ProgressDialog dialog = new ProgressDialog(ShowCreatedOrderActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        Toast.makeText(this, "" + strShopID, Toast.LENGTH_SHORT).show();
        AndroidNetworking.post(BaseUrl.show_order)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("order_status", "0")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("mklgjhm", "onResponse: " + response);
                        try {
                            dialog.dismiss();
                            arrayListFavourite = new ArrayList<>();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ShowEmployeeGetSet favouriteModal = new ShowEmployeeGetSet();
                                favouriteModal.setAppointment_id(jsonObject.getString("order_id"));
                                favouriteModal.setCreated_at(jsonObject.getString("day"));
                                favouriteModal.setDate(jsonObject.getString("date"));
                                arrayListFavourite.add(favouriteModal);


                            }


                            layoutManager = new GridLayoutManager(ShowCreatedOrderActivity.this, 1, RecyclerView.VERTICAL, false);
                            rec_favourite.setLayoutManager(layoutManager);
                            favouriteAdapter = new ShowCreateeAdapter(ShowCreatedOrderActivity.this, arrayListFavourite);
                            rec_favourite.setAdapter(favouriteAdapter);
                            dialog.dismiss();


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

}