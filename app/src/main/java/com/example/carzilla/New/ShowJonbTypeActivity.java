package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.adapter.ShowCheckInAdapter;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowJonbTypeActivity extends AppCompatActivity {
    ImageView imgBack;
    RecyclerView rec_favourite;

    RecyclerView.LayoutManager layoutManager;
    ShowCheckInAdapter favouriteAdapter;

    ArrayList<ShowEmployeeGetSet> arrayListFavourite = new ArrayList<>();

    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jonb_type);


        rec_favourite = findViewById(R.id.rec_favourite);
        layoutManager = new GridLayoutManager(ShowJonbTypeActivity.this, 1, RecyclerView.VERTICAL, false);
        rec_favourite.setLayoutManager(layoutManager);
        edtSearch = findViewById(R.id.edtSearch);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        show_CheckIn();
    }

    public void show_CheckIn() {
        final ProgressDialog dialog = new ProgressDialog(ShowJonbTypeActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.job_type)
                .addBodyParameter("shop_id","12")
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
                                favouriteModal.setCheck_listID(jsonObject.getString("job_typeID"));
                                favouriteModal.setName(jsonObject.getString("job_typeName"));
                                arrayListFavourite.add(favouriteModal);


                            }


                            layoutManager = new GridLayoutManager(ShowJonbTypeActivity.this, 1, RecyclerView.VERTICAL, false);
                            rec_favourite.setLayoutManager(layoutManager);
                            favouriteAdapter = new ShowCheckInAdapter(ShowJonbTypeActivity.this, arrayListFavourite);
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