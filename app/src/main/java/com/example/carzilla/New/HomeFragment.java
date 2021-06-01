package com.example.carzilla.New;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.New.other.BaseUrl;
import com.example.carzilla.R;
import com.example.carzilla.databinding.BottomManagerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.carzilla.New.NavigationActivity.drawer;


public class HomeFragment extends Fragment {

    ImageView dashboardMenuMain;
    ImageView drawerMenu;
    LinearLayout linearReports,linearRepairOrder,linearOrder,limnearCounterSale,linearAccount;
    TextView txtCreatedCount,txtInProgressCount,txtCompletedCount;
    String strShopID="";

    LinearLayout linearCreated,linearInProgress,linerrCreatedOrder,linearInventory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strShopID = AppsContants.sharedpreferences.getString(AppsContants.ShopUserId, "");


        dashboardMenuMain = view.findViewById(R.id.dashboardMenuMain);
        linearCreated = view.findViewById(R.id.linearCreated);
        linerrCreatedOrder = view.findViewById(R.id.linerrCreatedOrder);
        linearInProgress = view.findViewById(R.id.linearInProgress);
        drawerMenu = view.findViewById(R.id.drawerMenu);
        linearReports = view.findViewById(R.id.linearReports);
        linearRepairOrder = view.findViewById(R.id.linearRepairOrder);
        linearOrder = view.findViewById(R.id.linearOrder);
        limnearCounterSale = view.findViewById(R.id.limnearCounterSale);
        linearAccount = view.findViewById(R.id.linearAccount);
        txtCreatedCount = view.findViewById(R.id.txtCreatedCount);
        txtInProgressCount = view.findViewById(R.id.txtInProgressCount);
        txtCompletedCount = view.findViewById(R.id.txtCompletedCount);
        linearInventory = view.findViewById(R.id.linearInventory);

        linearInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowInvenroeyActivity.class));

            }
        });

        linearInProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getActivity(),ShowInProgressOrderActivity.class));
            }
        });
        linerrCreatedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getActivity(),ShowCompletedOrderActivity.class));
            }
        });
        linearCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getActivity(),ShowCreatedOrderActivity.class));
            }
        });
        linearAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AccountActivity.class));
            }
        });

        limnearCounterSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowCounterSaleActivity.class));
            }
        });

        linearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowOrderActivity.class));
            }
        });

        linearRepairOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RepairOrderActivity.class));
            }
        });


        linearReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowReportsActivity.class));
            }
        });


        drawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        dashboardMenuMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottom = new BottomSheetDialog(getActivity());
                BottomManagerBinding binding = BottomManagerBinding.inflate(getLayoutInflater());
                bottom.setContentView(R.layout.bottom_manager);


                RelativeLayout relEmployee = bottom.findViewById(R.id.relEmployee);
                RelativeLayout relVendor = bottom.findViewById(R.id.relVendor);
                RelativeLayout relAppointment = bottom.findViewById(R.id.relAppointment);
                RelativeLayout relItemMaster = bottom.findViewById(R.id.relItemMaster);
                RelativeLayout relAccesblity = bottom.findViewById(R.id.relAccesblity);
                ImageView imgCross = bottom.findViewById(R.id.imgCross);


                imgCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottom.dismiss();
                    }
                });

                relAccesblity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ShowAccessiblityActivity.class));

                    }
                });

                relItemMaster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ShowIttemMasterActivity.class));

                    }
                });

                relAppointment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ShowAppointment.class));

                    }
                });


                relVendor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // startActivity(new Intent(getActivity(), AddVendorActivity.class));
                        startActivity(new Intent(getActivity(), ShowVendorActivity.class));
                    }
                });
                relEmployee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(getActivity(), AddEmployeeActivity.class));
                        startActivity(new Intent(getActivity(), SHowEmployeeActivity.class));
                    }
                });

                bottom.show();
            }
        });

        showCount();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCount();
    }

    public void showCount() {



        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        AndroidNetworking.post(BaseUrl.created_order_count)
                .addBodyParameter("shop_id", strShopID)
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



                                String str = response.getString("data");
                                    JSONObject jsonObject = new JSONObject(str);
                                    String strcretated = jsonObject.getString("create");
                                    String strprogress = jsonObject.getString("progress");
                                    String strcomplete = jsonObject.getString("complete");

                                txtCreatedCount.setText(strcretated);
                                txtInProgressCount.setText(strprogress);
                                txtCompletedCount.setText(strcomplete);






                            } else {
                                dialog.dismiss();

                                Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
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


}