package com.example.carzilla.New.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carzilla.New.UpdateAppointmentActivity;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.R;

import java.util.List;

public class ShowCreateeAdapter extends RecyclerView.Adapter<ShowCreateeAdapter.ViewHolder> {

    private Context context;
    List<ShowEmployeeGetSet> favouriteList ;

    public ShowCreateeAdapter(Context context, List<ShowEmployeeGetSet>getDataAdapter) {
        this.context = context;
        this.favouriteList = getDataAdapter;

    }

    @NonNull
    @Override
    public ShowCreateeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_created_adapter, parent, false);

        ShowCreateeAdapter.ViewHolder viewHolder = new ShowCreateeAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowCreateeAdapter.ViewHolder holder, int position) {

        final ShowEmployeeGetSet favouriteModal=favouriteList.get(position);






        holder.txtName.setText(favouriteModal.getCreated_at());
        holder.txtComment.setText(favouriteModal.getDate());
        holder.txtCustomerName.setText(favouriteModal.getCustomer_name());
        holder.txtCustomerMobileNumber.setText(favouriteModal.getPhone_number());


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.AppointmwntID, favouriteModal.getAppointment_id());
                editor.commit();


                context.startActivity(new Intent(context, UpdateAppointmentActivity.class));



            }
        });




    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtComment,txtCustomerName,txtCustomerMobileNumber;
        ImageView imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtComment = itemView.findViewById(R.id.txtComment);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtCustomerMobileNumber = itemView.findViewById(R.id.txtCustomerMobileNumber);
            imgEdit = itemView.findViewById(R.id.imgEdit);



        }
    }
}