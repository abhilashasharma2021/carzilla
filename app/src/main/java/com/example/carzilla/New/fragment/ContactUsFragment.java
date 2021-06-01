package com.example.carzilla.New.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.ShowAppointment;
import com.example.carzilla.New.adapter.ShowCreateeAdapter;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ContactUsFragment extends Fragment {

    TextView txtEnail, txtnumber;
    ImageView imgBack;
    String strShopID = "";
    LinearLayout linearEmail,linearCall,linearWhatsApp,linearMessage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);


        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");

        imgBack = view.findViewById(R.id.imgBack);
        txtEnail = view.findViewById(R.id.txtEnail);
        txtnumber = view.findViewById(R.id.txtnumber);
        linearEmail = view.findViewById(R.id.linearEmail);
        linearCall = view.findViewById(R.id.linearCall);
        linearWhatsApp = view.findViewById(R.id.linearWhatsApp);
        linearMessage = view.findViewById(R.id.linearMessage);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });






        show_Favourite();
        return view;

    }


    public void show_Favourite() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.needHelp)
                .addBodyParameter("shop_id", strShopID)
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

                                String strphone_number = jsonObject.getString("phone_number");
                                String email_id = jsonObject.getString("email_id");
                                txtEnail.setText(email_id);
                                txtnumber.setText(strphone_number);

                                linearMessage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                                        smsIntent.setType("vnd.android-dir/mms-sms");
                                        smsIntent.putExtra("address", strphone_number);
                                        smsIntent.putExtra("sms_body","Body of Message");
                                        startActivity(smsIntent);
                                    }
                                });
                                linearWhatsApp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = "https://api.whatsapp.com/send?phone="+strphone_number;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                });
                                linearCall.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + strphone_number));
                                        startActivity(intent);
                                    }
                                });
                                linearEmail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                                        intent.putExtra(Intent.EXTRA_EMAIL, email_id);
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "");
                                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            startActivity(intent);
                                        }
                                    }
                                });


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

}