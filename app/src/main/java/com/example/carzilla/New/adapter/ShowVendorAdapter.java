package com.example.carzilla.New.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carzilla.New.UpdateVendorActivity;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.R;

import java.util.List;

public class ShowVendorAdapter extends RecyclerView.Adapter<ShowVendorAdapter.ViewHolder> {

    private Context context;
    List<ShowEmployeeGetSet> favouriteList;

    public ShowVendorAdapter(Context context, List<ShowEmployeeGetSet> getDataAdapter) {
        this.context = context;
        this.favouriteList = getDataAdapter;

    }

    @NonNull
    @Override
    public ShowVendorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_vendor_adeapter, parent, false);

        ShowVendorAdapter.ViewHolder viewHolder = new ShowVendorAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowVendorAdapter.ViewHolder holder, int position) {

        final ShowEmployeeGetSet favouriteModal = favouriteList.get(position);


        String whole = favouriteModal.getName();
        String first = whole.substring(0, 1);
        System.out.println(first);
        Log.e("rtiyufdgjkhm", first);

        holder.txt.setText(first);


        holder.txtName.setText(favouriteModal.getName());
        holder.txtMobile.setText(favouriteModal.getPhone_number());
        holder.txtemail.setText(favouriteModal.getEmail_id());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.VendorIDd, favouriteModal.getVendor_id());
                editor.commit();
              context.startActivity(new Intent(context, UpdateVendorActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtMobile, txtemail, txt;
        ImageView imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtemail = itemView.findViewById(R.id.txtemail);
            txt = itemView.findViewById(R.id.txt);
            imgEdit = itemView.findViewById(R.id.imgEdit);


        }
    }

}