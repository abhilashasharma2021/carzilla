package com.example.carzilla.New.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carzilla.New.SHowPartsMastereActivity;
import com.example.carzilla.New.SaveSparePartActivity;
import com.example.carzilla.New.UpdateSparePartActivity;
import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.New.other.AppsContants;
import com.example.carzilla.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowpartsAdapter  extends RecyclerView.Adapter<ShowpartsAdapter.ViewHolder> {

    private Context context;
    List<ShowEmployeeGetSet> favouriteList;

    private ArrayList<ShowEmployeeGetSet> arraylist;


    public ShowpartsAdapter(Context context, List<ShowEmployeeGetSet> getDataAdapter) {
        this.context = context;
        this.favouriteList = getDataAdapter;
        this.arraylist = new ArrayList<ShowEmployeeGetSet>();
        this.arraylist.addAll(getDataAdapter);


    }

    @NonNull
    @Override
    public ShowpartsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_parts_master_adapter, parent, false);

        ShowpartsAdapter.ViewHolder viewHolder = new ShowpartsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowpartsAdapter.ViewHolder holder, int position) {

        final ShowEmployeeGetSet favouriteModal = favouriteList.get(position);


        String whole = favouriteModal.getName();
        String first = whole.substring(0, 1);
        System.out.println(first);
        Log.e("rtiyufdgjkhm", first);


        if (first.equals("A")) {
            holder.relImage.setBackgroundColor(Color.parseColor("#eb1c24"));

        } else {
            holder.relImage.setBackgroundColor(Color.parseColor("#38AECC"));

        }

        holder.txtAlphabate.setText(first);
        holder.txtName.setText(favouriteModal.getName());
        //holder.edtTax.setText(favouriteModal.getTxt());
        holder.edtPrice.setText(favouriteModal.getPrice());
        holder.edtStock.setText(favouriteModal.getIn_stock());
        holder.edtPurchase.setText(favouriteModal.getPurchase());


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.SparePartID, favouriteModal.getSpare_partSaveID());
                editor.commit();

                  //context.startActivity(new Intent(context, SaveSparePartActivity.class));
                context.startActivity(new Intent(context, UpdateSparePartActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAlphabate;

        EditText edtStock, edtPurchase,edtPrice;
        RelativeLayout relImage;
        ImageView imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            edtStock = itemView.findViewById(R.id.edtStock);
            edtPurchase = itemView.findViewById(R.id.edtPurchase);
            edtPrice = itemView.findViewById(R.id.edtPrice);
            relImage = itemView.findViewById(R.id.relImage);
            txtAlphabate = itemView.findViewById(R.id.txtAlphabate);
            imgEdit = itemView.findViewById(R.id.imgEdit);



        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        favouriteList.clear();
        if (charText.length() == 0) {
            favouriteList.addAll(arraylist);
        } else {
            for (ShowEmployeeGetSet wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    favouriteList.add(wp);
                }/*else if (wp.getLastname().toLowerCase(Locale.getDefault()).contains(charText)){

                    typeDataList.add(wp);

                }*/
              /*
                else if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    dataAdapters.add(wp);
                } else if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                    dataAdapters.add(wp);
                }*/

            }
        }
        notifyDataSetChanged();
    }

}
