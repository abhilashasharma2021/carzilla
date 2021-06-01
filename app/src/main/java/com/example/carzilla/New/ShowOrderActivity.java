package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ShowOrderActivity extends AppCompatActivity {
    RecyclerView rec_favourite;
    RecyclerView.LayoutManager layoutManager;
    ShowCreateeAdapter favouriteAdapter;

    ArrayList<ShowEmployeeGetSet> arrayListFavourite = new ArrayList<>();
    ImageView imgAdd;
    ImageView imgBack;
    String strShopID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

      /*  txtPurchaseOrder = findViewById(R.id.txtPurchaseOrder);

        imgBack = findViewById(R.id.imgBack);
        txtPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(ShowOrderActivity.this,PurchaseOrderActivity.class));
            }
        });*/


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");

        imgAdd = findViewById(R.id.imgAdd);
        rec_favourite = findViewById(R.id.rec_favourite);
        layoutManager = new GridLayoutManager(ShowOrderActivity.this, 1, RecyclerView.VERTICAL, false);
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
                startActivity(new Intent(ShowOrderActivity.this,RepairOrderActivity.class));
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
        final ProgressDialog dialog = new ProgressDialog(ShowOrderActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.showAllOrder)
                .addBodyParameter("shop_id",strShopID)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            dialog.dismiss();
                            arrayListFavourite=new ArrayList<>();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);




                                ShowEmployeeGetSet favouriteModal = new ShowEmployeeGetSet();
                                favouriteModal.setEmployee_id(jsonObject.getString("order_id"));
                                favouriteModal.setCreated_at(jsonObject.getString("day"));
                                favouriteModal.setDate(jsonObject.getString("date"));
                                arrayListFavourite.add(favouriteModal);


                            }


                            layoutManager = new GridLayoutManager(ShowOrderActivity.this, 1, RecyclerView.VERTICAL, false);
                            rec_favourite.setLayoutManager(layoutManager);
                            favouriteAdapter = new ShowCreateeAdapter(ShowOrderActivity.this, arrayListFavourite);
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