package com.example.carzilla.New;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
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
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;
import com.google.android.material.slider.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RepairOrderActivity extends AppCompatActivity {
    ImageView imgBack;
    Spinner spinMakeModel,spinFuelType;

    ArrayList<String> arrayMakeModelId;
    ArrayList<String> arrayMakeModelName;
    ArrayList<String> arrayTFuelypelId;
    ArrayList<String> arrayTFuelypeName;
    String strMakeModelId="",strMakeModelName="";
    String strTFuelypeId="",strTFuelypeName="";
    String strShopID="";



    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";




    Slider slider;
    EditText edtVehicleNumber,edtKiloMEter,edtChasisNember,edtEngineNumber;
    EditText edtPhoneNumber,edtCumtomerName,edtEmail,edtTaxNumber,edtCustomerAddress,edtCustomerRemark;
    Button btnSaveDetails;


    double strPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_order);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");



        edtVehicleNumber = findViewById(R.id.edtVehicleNumber);
        edtKiloMEter = findViewById(R.id.edtKiloMEter);
        edtChasisNember = findViewById(R.id.edtChasisNember);
        edtEngineNumber = findViewById(R.id.edtEngineNumber);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtCumtomerName = findViewById(R.id.edtCumtomerName);
        edtEmail = findViewById(R.id.edtEmail);
        edtTaxNumber = findViewById(R.id.edtTaxNumber);
        edtCustomerAddress = findViewById(R.id.edtCustomerAddress);
        edtCustomerRemark = findViewById(R.id.edtCustomerRemark);
        btnSaveDetails = findViewById(R.id.btnSaveDetails);


        edtVehicleNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtChasisNember.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtEngineNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});



        btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stredtVehicleNumber= edtVehicleNumber.getText().toString().trim();
                String stredtKiloMEter= edtKiloMEter.getText().toString().trim();
                String stredtChasisNember= edtChasisNember.getText().toString().trim();
                String stredtEngineNumber= edtEngineNumber.getText().toString().trim();
                String stredtPhoneNumber= edtPhoneNumber.getText().toString().trim();
                String stredtCumtomerName= edtCumtomerName.getText().toString().trim();
                String stredtEmail= edtEmail.getText().toString().trim();
                String stredtTaxNumber= edtTaxNumber.getText().toString().trim();
                String stredtCustomerAddress= edtCustomerAddress.getText().toString().trim();
                String stredtCustomerRemark= edtCustomerRemark.getText().toString().trim();




                if (stredtVehicleNumber.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter vehicle number", Toast.LENGTH_SHORT).show();
                }
                else if (stredtKiloMEter.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter kilometer", Toast.LENGTH_SHORT).show();
                }else if (stredtChasisNember.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter chasis number", Toast.LENGTH_SHORT).show();
                }else if (stredtEngineNumber.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter engine number", Toast.LENGTH_SHORT).show();
                }else if (stredtPhoneNumber.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter phone number", Toast.LENGTH_SHORT).show();
                }else if (stredtCumtomerName.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter name", Toast.LENGTH_SHORT).show();
                }else if (stredtEmail.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter email", Toast.LENGTH_SHORT).show();
                }
                else if (!stredtEmail.matches(emailPattern)) {
                    Toast.makeText(RepairOrderActivity
                            .this, "Invalid email", Toast.LENGTH_SHORT).show();

                }
                else if (stredtCustomerRemark.equals("")){
                    Toast.makeText(RepairOrderActivity.this, "please enter remark", Toast.LENGTH_SHORT).show();
                }else{

                    AddOrder(stredtVehicleNumber,stredtKiloMEter,stredtChasisNember,stredtEngineNumber,stredtPhoneNumber,stredtCumtomerName,stredtEmail,stredtTaxNumber,stredtCustomerAddress,stredtCustomerRemark);

                }

            }
        });






        imgBack = findViewById(R.id.imgBack);
        spinMakeModel = findViewById(R.id.spinMakeModel);
        spinFuelType = findViewById(R.id.spinFuelType);
        slider = findViewById(R.id.slider);


        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

            }
        });




        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                strPercentage = value;

                Log.e("jhgkjdghj",slider+"");
                Log.e("jhgkjdghj",value+"");
                Log.e("jhgkjdghj",fromUser+"");
            }
        });



        spinFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strTFuelypeId = arrayTFuelypelId.get(i);
                strTFuelypeName = arrayTFuelypeName.get(i);
                Log.e("dsfkjdsfsd", strMakeModelId);
              /*  AppConstant.sharedpreferences = getSharedPreferences(AppConstant.MyPREFERENCES, Context.MODE_PRIVATE);
                strcountryname = AppConstant.sharedpreferences.getString(AppConstant.countryname, "");

*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinMakeModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strMakeModelId = arrayMakeModelId.get(i);
                strMakeModelName = arrayMakeModelName.get(i);
                Log.e("dsfkjdsfsd", strMakeModelId);
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


        ShowMakeModel();
        ShowFuelType();


    }

    public void AddOrder(String stredtVehicleNumber, String stredtKiloMEter, String stredtChasisNember, String stredtEngineNumber, String stredtPhoneNumber
            , String stredtCumtomerName, String stredtEmail, String stredtTaxNumber, String stredtCustomerAddress, String stredtCustomerRemark) {




        final ProgressDialog dialog = new ProgressDialog(RepairOrderActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.repair_order)
                .addBodyParameter("make_model", strMakeModelName)
                .addBodyParameter("fuel_type", strTFuelypeName)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("vehicle_number", stredtVehicleNumber)
                .addBodyParameter("kilometer_driven", stredtKiloMEter)
                .addBodyParameter("engine_number", stredtEngineNumber)
                .addBodyParameter("chassis_number", stredtChasisNember)
                .addBodyParameter("fuel_indicator", String.valueOf(strPercentage+"%"))
                .addBodyParameter("email", stredtEmail)
                .addBodyParameter("phone_number", stredtPhoneNumber)
                .addBodyParameter("customer_name", stredtCumtomerName)
                .addBodyParameter("tax_number", stredtTaxNumber)
                .addBodyParameter("cust_address", stredtCustomerAddress)
                .addBodyParameter("cust_remark", stredtCustomerRemark)
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
                                Toast.makeText(RepairOrderActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                                finish();

                            } else {
                                dialog.dismiss();

                                Toast.makeText(RepairOrderActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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




    public void ShowFuelType() {
        AndroidNetworking.post(BaseUrl.fuel_type)
                .addBodyParameter("shop_id",strShopID)
                .setTag("Show Category")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {


                            arrayTFuelypelId = new ArrayList<>();
                            arrayTFuelypeName = new ArrayList<>();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);


                            //  arrayCountryName.add(strUserCountry);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strCatID = jsonObject.getString("fuel_typeID");
                                String name = jsonObject.getString("name");



                                arrayTFuelypelId.add(strCatID);
                                arrayTFuelypeName.add(name);

                            }




                            ArrayAdapter adapter = new ArrayAdapter(RepairOrderActivity.this, android.R.layout.simple_spinner_item, arrayTFuelypeName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinFuelType.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }


    public void ShowMakeModel() {
        AndroidNetworking.post(BaseUrl.make_model)
                .addBodyParameter("shop_id",strShopID)
                .setTag("Show Category")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {


                            arrayMakeModelId = new ArrayList<>();
                            arrayMakeModelName = new ArrayList<>();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);


                            //  arrayCountryName.add(strUserCountry);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strCatID = jsonObject.getString("make_modelID");
                                String name = jsonObject.getString("name");



                                arrayMakeModelId.add(strCatID);
                                arrayMakeModelName.add(name);

                            }




                            ArrayAdapter adapter = new ArrayAdapter(RepairOrderActivity.this, android.R.layout.simple_spinner_item, arrayMakeModelName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinMakeModel.setAdapter(adapter);




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