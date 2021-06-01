package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;
import com.rilixtech.widget.countrycodepicker.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.Job;

public class UpdateSparePartActivity extends AppCompatActivity {
    ImageView imgBack;

    Spinner spinParts;

    List<String> arraypartID;
    List<String>arraypartName;

    String strPartsId="",strPartsName="";
    String strShopID="";

    EditText edtSparePartname,sparePartNumber,edtprice,edtPurchase,edtinStock,edtMinStock,edtRack,edtShin,edtCompanyName,
            edtDescription;
    Button btnSaveDetails;
    String strShopIDSparePartID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_spare_part);









      /*  Repair Order Screen

        Fuel Indicator is dark while selected/selected.(DONE)

                The Kilometer field is accepting text also.(DONE)

        Under Customer Information, Tax Number and Customer Address is optional(DONE)

        Create Repair Order Button is said as “Save Vender”.(DONE)

        Vehicle Number (India), Phone number and email address should be validated with RegEx.(DONE)

                Vehicle, Chassis and Engine Number should be Converted to All Capital letters on input.(DONE)


        Configuration Menu

        Change the menu text to Configuration.(DONE)

        X is not working.(DONE)


        Employees Menu

        Add Employee - Phone and address should be validated with RegEx.(DONE)

        Vendor Menu

        Add Vendor - Phone and address should be validated with RegEx.(DONE)

                On Edit - No information about which field is what, Field label/Description is not shown. This is a common problem in all edit screens.

                No View Details Screen(DONE)





        Item Master -> Service Master


                Touch area to change the value is very small.(DONE)



        Item Master -> Part Master

        Not able to add new

                On Edit - No information about which field is what, Field label/Description is not shown. This is a common problem in all edit screens.(DONE)





                Appointment

        Date of Birth should be Service Date.(DONE)

                List Screen Don’t have any details about who’s appointment and what is the contact/vehicle details.(please send me the design for it?)


                No edit/delete Screen.(also send me the screen design for it)

*/


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

        btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toast.makeText(UpdateSparePartActivity.this, "please enter part name", Toast.LENGTH_SHORT).show();
                }else  if (strsparePartNumber.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter part number", Toast.LENGTH_SHORT).show();
                }else  if (stredtprice.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter price", Toast.LENGTH_SHORT).show();
                }else  if (stredtPurchase.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter purchase", Toast.LENGTH_SHORT).show();
                }else  if (stredtMinStock.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter min stock", Toast.LENGTH_SHORT).show();
                }else  if (stredtinStock.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter in stock", Toast.LENGTH_SHORT).show();
                }else  if (stredtRack.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter rack", Toast.LENGTH_SHORT).show();
                }else  if (stredtShin.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter shin", Toast.LENGTH_SHORT).show();
                }else  if (stredtCompanyName.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter company name", Toast.LENGTH_SHORT).show();
                }else  if (stredtDescription.equals("")){
                    Toast.makeText(UpdateSparePartActivity.this, "please enter description", Toast.LENGTH_SHORT).show();
                }else{

                    SaveDetails(stredtSparePartname,strsparePartNumber,stredtprice,stredtPurchase,stredtMinStock,stredtinStock,
                            stredtRack,stredtShin,stredtCompanyName,stredtDescription);
                }
            }
        });


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");
        strShopIDSparePartID = AppsContants.sharedpreferences.getString(AppsContants.SparePartID, "");




        imgBack = findViewById(R.id.imgBack);
        spinParts = findViewById(R.id.spinParts);

        spinParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strPartsId = arraypartID.get(i);
                strPartsName = arraypartName.get(i);
                Log.e("dsfkjdsfsd", strPartsId);
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                strPartsId = AppsContants.sharedpreferences.getString(AppsContants.sparePertID, "");

              /*  AppConstant.sharedpreferences = getSharedPreferences(AppConstant.MyPREFERENCES, Context.MODE_PRIVATE);
                strcountryname = AppConstant.sharedpreferences.getString(AppConstant.countryname, "");

*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        showSpareParts();

        show_Favourite();


    }


    public void SaveDetails(String stredtSparePartname, String strsparePartNumber , String stredtprice, String stredtPurchase, String stredtMinStock, String stredtinStock, String stredtRack, String stredtShin, String stredtCompanyName, String stredtDescription) {


        final ProgressDialog dialog = new ProgressDialog(UpdateSparePartActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.updateSpareParts)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("spare_partSaveID", strShopIDSparePartID)
                .addBodyParameter("spare_partID", strPartsId)
                .addBodyParameter("spare_partNane", stredtSparePartname)
                .addBodyParameter("spare_partNumber", strsparePartNumber)
                .addBodyParameter("price", stredtprice)
                .addBodyParameter("minimum_stock", stredtMinStock)
                .addBodyParameter("in_stock", stredtinStock)
                .addBodyParameter("purchase", stredtPurchase)
                .addBodyParameter("rack", stredtRack)
                .addBodyParameter("hsn", stredtShin)
                .addBodyParameter("company_name", stredtCompanyName)
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
                                Toast.makeText(UpdateSparePartActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                dialog.dismiss();

                                Toast.makeText(UpdateSparePartActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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



    public void show_Favourite() {
        final ProgressDialog dialog = new ProgressDialog(UpdateSparePartActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.showSparePartt)
                .addBodyParameter("shop_id",strShopID)
                .addBodyParameter("spare_partSaveID",strShopIDSparePartID)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("dgjhdhkjg",response.toString()+"");


                        try {
                            dialog.dismiss();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);



                                String spare_partSaveID  =jsonObject.getString("spare_partSaveID");
                                String spare_partNane  =jsonObject.getString("spare_partNane");
                                String price  =jsonObject.getString("price");
                                String in_stock  =jsonObject.getString("in_stock");
                                String minimum_stock  =jsonObject.getString("minimum_stock");
                                String purchase  =jsonObject.getString("purchase");
                                String rack  =jsonObject.getString("rack");
                                String hsn  =jsonObject.getString("hsn");
                                String company_name  =jsonObject.getString("company_name");
                                String description  =jsonObject.getString("description");
                                String spare_partNumber  =jsonObject.getString("spare_partNumber");
                                String spare_partID  =jsonObject.getString("spare_partID");


                              edtSparePartname.setText(spare_partNane);
                                edtprice.setText(price);
                                edtPurchase.setText(purchase);
                                edtMinStock.setText(minimum_stock);
                                edtinStock.setText(in_stock);
                                edtRack.setText(rack);
                                edtShin.setText(hsn);
                                edtCompanyName.setText(company_name);
                                edtDescription.setText(description);
                                sparePartNumber.setText(spare_partNumber);
                                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                editor.putString(AppsContants.sparePertID, spare_partID);
                                editor.commit();


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



    public void showSpareParts() {
        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strPartsId = AppsContants.sharedpreferences.getString(AppsContants.sparePertID, "");

        AndroidNetworking.post(BaseUrl.showSparePart)
                .addBodyParameter("shop_id",strShopID)
                .setTag("Show Category")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("rtidfkhkfd",response.toString()+"");


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




                            ArrayAdapter adapter = new ArrayAdapter(UpdateSparePartActivity.this, android.R.layout.simple_spinner_item, arraypartName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinParts.setAdapter(adapter);

                            for ( int j = 0; j <arraypartID.size() ; j++) {
                                String name1 = arraypartID.get(j);
                                if (name1.equals(strPartsId)) {
                                    spinParts.setSelection(j);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("rtidfkhkfd",e.getMessage()+"");

                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("rtidfkhkfd",anError.getMessage()+"");

                    }
                });


    }


}