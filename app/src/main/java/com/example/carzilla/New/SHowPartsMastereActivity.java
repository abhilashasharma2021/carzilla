package com.example.carzilla.New;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.adapter.ShowpartsAdapter;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SHowPartsMastereActivity extends AppCompatActivity {
    ImageView imgBack;


    RecyclerView rec_favourite;
    RecyclerView.LayoutManager layoutManager;
    ShowpartsAdapter favouriteAdapter;

    ArrayList<ShowEmployeeGetSet> arrayListFavourite = new ArrayList<>();
    String strShopID="";

    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_how_parts_mastere);



        edtSearch = findViewById(R.id.edtSearch);



        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");
        Log.e("rgtiuydjkhjhkdv",strShopID);

        rec_favourite = findViewById(R.id.rec_favourite);
        layoutManager = new GridLayoutManager(SHowPartsMastereActivity.this, 1, RecyclerView.VERTICAL, false);
        rec_favourite.setLayoutManager(layoutManager);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        show_Favourite();

        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        show_Favourite();
    }

    public void show_Favourite() {
        final ProgressDialog dialog = new ProgressDialog(SHowPartsMastereActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.showSparePartt)
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
                                favouriteModal.setSpare_partSaveID(jsonObject.getString("spare_partSaveID"));
                                favouriteModal.setName(jsonObject.getString("spare_partNane"));
                                favouriteModal.setPrice(jsonObject.getString("price"));
                                favouriteModal.setIn_stock(jsonObject.getString("in_stock"));
                                favouriteModal.setPurchase(jsonObject.getString("purchase"));
                                arrayListFavourite.add(favouriteModal);


                            }


                            layoutManager = new GridLayoutManager(SHowPartsMastereActivity.this, 1, RecyclerView.VERTICAL, false);
                            rec_favourite.setLayoutManager(layoutManager);
                            favouriteAdapter = new ShowpartsAdapter(SHowPartsMastereActivity.this, arrayListFavourite);
                            rec_favourite.setAdapter(favouriteAdapter);
                            dialog.dismiss();
                            edtSearch.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                                    String text = String.valueOf(charSequence);
                                    favouriteAdapter.filter(text);

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });





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