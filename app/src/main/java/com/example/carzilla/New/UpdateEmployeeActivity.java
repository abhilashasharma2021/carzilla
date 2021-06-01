package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.carzilla.New.adapter.ShowEmployeeAdapter;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateEmployeeActivity extends AppCompatActivity {
    ImageView imgBack;


    Spinner spinEmployeeRole;

    String strRoleId="",strRoleName="";
    ArrayList<String> arrayRoleId;
    ArrayList<String> arrayRoleName;
    EditText edtEmployeeName,edtPhoneNumber,edtEmail,edtJoiningDate;
    Button btnSaveDetails;

    String st_date="";
    String   strShopID="";
    String   strEmployeeID="";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
    Matcher m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");
        strEmployeeID= AppsContants.sharedpreferences.getString(AppsContants.EmployeeID, "");







        btnSaveDetails = findViewById(R.id.btnSaveDetails);
        edtEmployeeName = findViewById(R.id.edtEmployeeName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtJoiningDate = findViewById(R.id.edtJoiningDate);
        imgBack = findViewById(R.id.imgBack);
        spinEmployeeRole = findViewById(R.id.spinEmployeeRole);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern r = Pattern.compile(pattern);

                String stredtEmployeeName = edtEmployeeName.getText().toString().trim();
                String stredtPhoneNumber = edtPhoneNumber.getText().toString().trim();
                String stredtEmail= edtEmail.getText().toString().trim();
                String stredtJoiningDate = edtJoiningDate.getText().toString().trim();

              /*  if (!edtPhoneNumber.getText().toString().isEmpty()) {
                    m = r.matcher(edtPhoneNumber.getText().toString().trim());
                } else {
                    Toast.makeText(AddEmployeeActivity.this, "Please enter mobile number ", Toast.LENGTH_LONG).show();
                }
                if (m.find()) {
                    Toast.makeText(AddEmployeeActivity.this, "MATCH", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddEmployeeActivity.this, "NO MATCH", Toast.LENGTH_LONG).show();
                }*/


                if (stredtEmployeeName.equals("")){
                    Toast.makeText(UpdateEmployeeActivity.this, "please enter employee name", Toast.LENGTH_SHORT).show();
                }else if(stredtPhoneNumber.equals("")){
                    Toast.makeText(UpdateEmployeeActivity.this, "please enter phone number", Toast.LENGTH_SHORT).show();



                }else if(stredtEmail.equals("")){
                    Toast.makeText(UpdateEmployeeActivity.this, "please enter email", Toast.LENGTH_SHORT).show();

                }
                else if (!stredtEmail.matches(emailPattern)) {
                    Toast.makeText(UpdateEmployeeActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();


                }else if(stredtJoiningDate.equals("")){
                    Toast.makeText(UpdateEmployeeActivity.this, "please enter joining Date", Toast.LENGTH_SHORT).show();

                }



                else{

                    AddEmployee(stredtEmployeeName,stredtPhoneNumber,stredtEmail,stredtJoiningDate);
                    //  login(strMobileNumber,strPasswordr);
                }


            }
        });


        edtJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateEmployeeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edtJoiningDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                st_date = edtJoiningDate.getText().toString().trim();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        spinEmployeeRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strRoleId = arrayRoleId.get(i);
                strRoleName = arrayRoleName.get(i);
                Log.e("dsfkjdsfsd", strRoleId);
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                strRoleId = AppsContants.sharedpreferences.getString(AppsContants.EmployeeRole, "");

              /*  AppConstant.sharedpreferences = getSharedPreferences(AppConstant.MyPREFERENCES, Context.MODE_PRIVATE);
                strcountryname = AppConstant.sharedpreferences.getString(AppConstant.countryname, "");

*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ShowRole();
        showDetails();
    }


    public void showDetails() {
        final ProgressDialog dialog = new ProgressDialog(UpdateEmployeeActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.show_employee)
                .addBodyParameter("shop_id",strShopID)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            dialog.dismiss();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);


                                String employee_id = jsonObject.getString("employee_id");


                                if (strEmployeeID.equals(employee_id)){
                                    String employee_role = jsonObject.getString("employee_role");
                                    String name = jsonObject.getString("name");
                                    String email_id = jsonObject.getString("email_id");
                                    String phone_number = jsonObject.getString("phone_number");
                                    String joining_date = jsonObject.getString("joining_date");


                                    edtEmployeeName.setText(name);
                                     edtPhoneNumber.setText(phone_number);
                                    edtEmail.setText(email_id);
                                    edtJoiningDate.setText(joining_date);

                                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                    editor.putString(AppsContants.EmployeeRole, employee_role);
                                    editor.commit();


                                }


                            }



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


    public void AddEmployee(String stredtEmployeeName, String stredtPhoneNumber, String stredtEmail, String stredtJoiningDate) {



        final ProgressDialog dialog = new ProgressDialog(UpdateEmployeeActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.update_employee)
                .addBodyParameter("shop_id", strShopID)
                .addBodyParameter("name", stredtEmployeeName)
                .addBodyParameter("phone", stredtPhoneNumber)
                .addBodyParameter("email", stredtEmail)
                .addBodyParameter("employee_role", strRoleName)
                .addBodyParameter("joining_date", stredtJoiningDate)
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
                                finish();
                                Toast.makeText(UpdateEmployeeActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                            } else {
                                dialog.dismiss();

                                Toast.makeText(UpdateEmployeeActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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




    public void ShowRole() {
        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strRoleId = AppsContants.sharedpreferences.getString(AppsContants.EmployeeRole, "");

        AndroidNetworking.post(BaseUrl.employee_role)
                .addBodyParameter("shop_id",strShopID)
                .setTag("Show Category")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {


                            arrayRoleId = new ArrayList<>();
                            arrayRoleName = new ArrayList<>();
                            String str = response.getString("data");
                            JSONArray jsonArray = new JSONArray(str);


                            //  arrayCountryName.add(strUserCountry);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strCatID = jsonObject.getString("job_typeID");
                                String name = jsonObject.getString("job_typeName");



                                arrayRoleId.add(strCatID);
                                arrayRoleName.add(name);

                            }




                            ArrayAdapter adapter = new ArrayAdapter(UpdateEmployeeActivity.this, android.R.layout.simple_spinner_item, arrayRoleName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinEmployeeRole.setAdapter(adapter);

                            for ( int j = 0; j <arrayRoleName.size() ; j++) {
                                String name1 = arrayRoleName.get(j);
                                if (name1.equals(strRoleId)) {
                                    spinEmployeeRole.setSelection(j);
                                }
                            }


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