package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.Calendar;

public class UpdateAppointmentActivity extends AppCompatActivity {
    ImageView imgBack;
    Spinner spinMakeModel, spinFuelType;


    ArrayList<String> arrayMakeModelId;
    ArrayList<String> arrayMakeModelName;
    String strMakeModelId = "", strMakeModelName = "";
    String strShopID = "";
    EditText edtVechileNumber, edtDateofBirth, edtCustomername, edtPhoneNumber, edtEmail, edtddress, edtRemark,edtServicePackage;
    Button btnAppointment;

    String st_date = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

    String strAppointmentID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);


        edtVechileNumber = findViewById(R.id.edtVechileNumber);
        edtDateofBirth = findViewById(R.id.edtDateofBirth);
        edtCustomername = findViewById(R.id.edtCustomername);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtddress = findViewById(R.id.edtddress);
        edtRemark = findViewById(R.id.edtRemark);
        btnAppointment = findViewById(R.id.btnAppointment);
        edtServicePackage = findViewById(R.id.edtServicePackage);
        edtVechileNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


        edtDateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edtDateofBirth.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                st_date = edtDateofBirth.getText().toString().trim();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stredtVechileNumber = edtVechileNumber.getText().toString().trim();
                String stredtDateofBirth = edtDateofBirth.getText().toString().trim();
                String stredtCustomername = edtCustomername.getText().toString().trim();
                String stredtPhoneNumber = edtPhoneNumber.getText().toString().trim();
                String stredtEmail = edtEmail.getText().toString().trim();
                String stredtddress = edtddress.getText().toString().trim();
                String stredtRemark = edtRemark.getText().toString().trim();


                if (stredtVechileNumber.equals("")) {
                    Toast.makeText(UpdateAppointmentActivity.this, "please enter vehicle number", Toast.LENGTH_SHORT).show();
                } else if (stredtDateofBirth.equals("")) {
                    Toast.makeText(UpdateAppointmentActivity.this, "please enter date of birth ", Toast.LENGTH_SHORT).show();
                } else if (stredtCustomername.equals("")) {
                    Toast.makeText(UpdateAppointmentActivity.this, "please enter name ", Toast.LENGTH_SHORT).show();
                } else if (stredtPhoneNumber.equals("")) {
                    Toast.makeText(UpdateAppointmentActivity.this, "please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (stredtEmail.equals("")) {
                    Toast.makeText(UpdateAppointmentActivity.this, "please enter email ", Toast.LENGTH_SHORT).show();
                }
                else if (!stredtEmail.matches(emailPattern)) {
                    Toast.makeText(UpdateAppointmentActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();

                } else if (stredtddress.equals("")) {
                    Toast.makeText(UpdateAppointmentActivity.this, "please enter address", Toast.LENGTH_SHORT).show();
                } else if (stredtRemark.equals("")) {
                    Toast.makeText(UpdateAppointmentActivity.this, "please enter remark", Toast.LENGTH_SHORT).show();
                } else {
                     AddAppointment(stredtVechileNumber,stredtDateofBirth,stredtCustomername,stredtPhoneNumber,stredtEmail,stredtddress,stredtRemark);
                }
            }
        });


        spinMakeModel = findViewById(R.id.spinMakeModel);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");
        strAppointmentID = AppsContants.sharedpreferences.getString(AppsContants.AppointmwntID, "");


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


        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ShowMakeModel();
        show_Favourite();
    }


    public void show_Favourite() {
        final ProgressDialog dialog = new ProgressDialog(UpdateAppointmentActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.showAppointment)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("appointment_id", strAppointmentID)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fjhgdjgf", response.toString() + "");
                        try {
                            dialog.dismiss();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);


                                String appointment_id = jsonObject.getString("appointment_id");
                                String appointment_day = jsonObject.getString("appointment_day");
                                String customer_name = jsonObject.getString("customer_name");
                                String phone_number = jsonObject.getString("phone_number");
                                String vehicle_number = jsonObject.getString("vehicle_number");
                                String service_date = jsonObject.getString("service_date");
                                String email = jsonObject.getString("email");
                                String customer_address = jsonObject.getString("customer_address");
                                String customer_remark = jsonObject.getString("customer_remark");
                                String service_package = jsonObject.getString("service_package");


                                 edtVechileNumber.setText(vehicle_number);
                            edtDateofBirth.setText(service_date);
                               edtCustomername.setText(customer_name);
                                edtPhoneNumber.setText(phone_number);
                              edtEmail.setText(email);
                                 edtddress.setText(customer_address);
                                edtRemark.setText(customer_remark);
                                edtServicePackage.setText(service_package);



                            }


                            dialog.dismiss();


                        } catch (JSONException e) {
                            Log.e("fjhgdjgf", e.getMessage() + "");
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("fjhgdjgf", anError.getMessage() + "");

                        dialog.dismiss();


                    }
                });


    }


    public void AddAppointment(String stredtVechileNumber, String stredtDateofBirth, String stredtCustomername, String stredtPhoneNumber, String stredtEmail, String stredtddress, String stredtRemark) {

        final ProgressDialog dialog = new ProgressDialog(UpdateAppointmentActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.update_appointment)
                .addBodyParameter("make_model", strMakeModelName)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("appointment_id", strAppointmentID)
                .addBodyParameter("vehicle_number", stredtVechileNumber)
                .addBodyParameter("service_date", stredtDateofBirth)
                .addBodyParameter("phone_number", stredtPhoneNumber)
                .addBodyParameter("email", stredtEmail)
                .addBodyParameter("customer_name", stredtCustomername)
                .addBodyParameter("cust_address", stredtddress)
                .addBodyParameter("cust_remark", stredtRemark)
                .addBodyParameter("service_package", "1month")
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
                                Toast.makeText(UpdateAppointmentActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                dialog.dismiss();

                                Toast.makeText(UpdateAppointmentActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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


    public void ShowMakeModel() {
        AndroidNetworking.post(BaseUrl.make_model)
                .addBodyParameter("shop_id", strShopID)
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


                            ArrayAdapter adapter = new ArrayAdapter(UpdateAppointmentActivity.this, android.R.layout.simple_spinner_item, arrayMakeModelName);
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