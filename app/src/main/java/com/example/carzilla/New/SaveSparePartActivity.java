package com.example.carzilla.New;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

public class SaveSparePartActivity extends AppCompatActivity {
    ImageView imgBack;

    Spinner spinParts;

    List<String>arraypartID;
    List<String>arraypartName;

    String strPartsId="",strPartsName="";
    String strShopID="";

    EditText edtSparePartname,sparePartNumber,edtprice,edtPurchase,edtinStock,edtMinStock,edtRack,edtShin,edtCompanyName,
            edtDescription;
    Button btnSaveDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_spare_part);

        edtSparePartname = findViewById(R.id.edtSparePartname);
        sparePartNumber = findViewById(R.id.sparePartNumber);
        edtprice = findViewById(R.id.edtprice);
        edtPurchase = findViewById(R.id.edtPurchase);
        edtMinStock = findViewById(R.id.edtMinStock);
        edtinStock = findViewById(R.id.edtinStock);
        edtRack = findViewById(R.id.edtRack);
        edtShin = findViewById(R.id.edtShin);
        edtCompanyName = findViewById(R.id.edtCompanyName);
        edtDescription = findViewById(R.id.edtDescription);
        btnSaveDetails = findViewById(R.id.btnSaveDetails);

        btnSaveDetails.setOnClickListener(v -> {
            String stredtSparePartname = edtSparePartname.getText().toString().trim();
            String strsparePartNumber = sparePartNumber.getText().toString().trim();
            String stredtprice = edtprice.getText().toString().trim();
            String stredtPurchase = edtPurchase.getText().toString().trim();
            String stredtMinStock = edtMinStock.getText().toString().trim();
            String stredtinStock = edtinStock.getText().toString().trim();
            String stredtRack = edtRack.getText().toString().trim();
            String stredtShin = edtShin.getText().toString().trim();
            String stredtCompanyName = edtCompanyName.getText().toString().trim();
            String stredtDescription = edtDescription.getText().toString().trim();


            if (stredtSparePartname.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter part name", Toast.LENGTH_SHORT).show();
            }else  if (strsparePartNumber.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter part number", Toast.LENGTH_SHORT).show();
            }else  if (stredtprice.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter price", Toast.LENGTH_SHORT).show();
            }else  if (stredtPurchase.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter purchase", Toast.LENGTH_SHORT).show();
            }else  if (stredtMinStock.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter min stock", Toast.LENGTH_SHORT).show();
            }else  if (stredtinStock.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter in stock", Toast.LENGTH_SHORT).show();
            }else  if (stredtRack.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter rack", Toast.LENGTH_SHORT).show();
            }else  if (stredtShin.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter shin", Toast.LENGTH_SHORT).show();
            }else  if (stredtCompanyName.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter company name", Toast.LENGTH_SHORT).show();
            }else  if (stredtDescription.equals("")){
                Toast.makeText(SaveSparePartActivity.this, "please enter description", Toast.LENGTH_SHORT).show();
            }else{

                SaveDetails(stredtSparePartname,strsparePartNumber,stredtprice,stredtPurchase,stredtMinStock,stredtinStock,
                        stredtRack,stredtShin,stredtCompanyName,stredtDescription);
            }
        });


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");



        imgBack = findViewById(R.id.imgBack);
        spinParts = findViewById(R.id.spinParts);

        spinParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strPartsId = arraypartID.get(i);
                strPartsName = arraypartName.get(i);
                Log.e("dsfkjdsfsd", strPartsId);
              /*  AppConstant.sharedpreferences = getSharedPreferences(AppConstant.MyPREFERENCES, Context.MODE_PRIVATE);
                strcountryname = AppConstant.sharedpreferences.getString(AppConstant.countryname, "");

*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        imgBack.setOnClickListener(v -> finish());



        showSpareParts();

    }


    public void SaveDetails(String stredtSparePartname, String strsparePartNumber , String stredtprice, String stredtPurchase, String stredtMinStock, String stredtinStock, String stredtRack, String stredtShin, String stredtCompanyName, String stredtDescription) {



        final ProgressDialog dialog = new ProgressDialog(SaveSparePartActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.addSparePart)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("type", strPartsId)
                .addBodyParameter("sparePartName", stredtSparePartname)
                .addBodyParameter("sparePartNumber", strsparePartNumber)
                    .addBodyParameter("price", stredtprice)
                .addBodyParameter("minStock", stredtMinStock)
                .addBodyParameter("inStock", stredtinStock)
                .addBodyParameter("purchase", stredtPurchase)
                .addBodyParameter("rack", stredtRack)
                .addBodyParameter("hsn", stredtShin)
                .addBodyParameter("companyName", stredtCompanyName)
                .addBodyParameter("description", stredtDescription)
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
                                Toast.makeText(SaveSparePartActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                dialog.dismiss();

                                Toast.makeText(SaveSparePartActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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



    public void showSpareParts() {
        AndroidNetworking.post(BaseUrl.showSparePart)
                .addBodyParameter("shop_id",strShopID)
                .setTag("Show Category")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {


                            arraypartID = new ArrayList<>();
                            arraypartName = new ArrayList<>();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);


                            //  arrayCountryName.add(strUserCountry);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strCatID = jsonObject.getString("spare_partID");
                                String name = jsonObject.getString("name");



                                arraypartID.add(strCatID);
                                arraypartName.add(name);

                            }




                            ArrayAdapter adapter = new ArrayAdapter(SaveSparePartActivity.this, android.R.layout.simple_spinner_item, arraypartName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinParts.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

}