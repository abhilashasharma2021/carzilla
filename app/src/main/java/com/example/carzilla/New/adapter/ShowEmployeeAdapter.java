package com.example.carzilla.New.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carzilla.New.UpdateAppointmentActivity;
import com.example.carzilla.New.UpdateEmployeeActivity;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.R;

import java.util.List;

public class ShowEmployeeAdapter extends RecyclerView.Adapter<ShowEmployeeAdapter.ViewHolder> {

    private Context context;
    List<ShowEmployeeGetSet> favouriteList ;

    public ShowEmployeeAdapter(Context context, List<ShowEmployeeGetSet>getDataAdapter) {
        this.context = context;
        this.favouriteList = getDataAdapter;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_employee_adapter, parent, false);

        ShowEmployeeAdapter.ViewHolder viewHolder = new ShowEmployeeAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ShowEmployeeGetSet favouriteModal=favouriteList.get(position);

holder.imgEdit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
        editor.putString(AppsContants.EmployeeID, favouriteModal.getEmployee_id());

        editor.commit();

        context.startActivity(new Intent(context, UpdateEmployeeActivity.class));
    }
});





          holder.txtName.setText(favouriteModal.getName());
          holder.txtMobile.setText(favouriteModal.getPhone_number());
          holder.txtemail.setText(favouriteModal.getEmail_id());
          holder.txtOenwe.setText(favouriteModal.getEmployee_role());

    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtMobile,txtemail,txtOenwe;
        ImageView imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtemail = itemView.findViewById(R.id.txtemail);
            txtOenwe = itemView.findViewById(R.id.txtOenwe);
            imgEdit = itemView.findViewById(R.id.imgEdit);



        }
    }

}