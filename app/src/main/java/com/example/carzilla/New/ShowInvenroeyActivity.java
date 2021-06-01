package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class ShowInvenroeyActivity extends AppCompatActivity {

    TextView txtAddSparePart,txtSparePArtCount,txtInStockPrice;
    String strShopID="";
    CardView cardSparePart,cardInStockprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_invenroey);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");



        txtSparePArtCount = findViewById(R.id.txtSparePArtCount);
        txtInStockPrice = findViewById(R.id.txtInStockPrice);
        cardSparePart = findViewById(R.id.cardSparePart);
        cardInStockprice = findViewById(R.id.cardInStockprice);
        txtAddSparePart = findViewById(R.id.txtAddSparePart);

        txtAddSparePart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowInvenroeyActivity.this,SaveSparePartActivity.class));

            }
        });
        cardSparePart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowInvenroeyActivity.this,SHowPartsMastereActivity.class));

            }
        });
        cardInStockprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowInvenroeyActivity.this,SHowPartsMastereActivity.class));

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
        final ProgressDialog dialog = new ProgressDialog(ShowInvenroeyActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.SparePartCount)
                .addBodyParameter("shop_id",strShopID)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fjhgdjgf",response.toString()+"");
                        try {
                            dialog.dismiss();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);

                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String spare_part_count = jsonObject.getString("spare_part_count");
                                String in_stock_total = jsonObject.getString("in_stock_total");


                                txtSparePArtCount.setText(spare_part_count);
                                txtInStockPrice.setText(in_stock_total);

                            }

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