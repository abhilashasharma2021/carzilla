package com.example.carzilla.New.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carzilla.New.model.ShowEmployeeGetSet;
import com.example.carzilla.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowCheckInAdapter extends RecyclerView.Adapter<ShowCheckInAdapter.ViewHolder> {

    private Context context;
    List<ShowEmployeeGetSet> favouriteList ;
    private ArrayList<ShowEmployeeGetSet> arraylist;


    public ShowCheckInAdapter(Context context, List<ShowEmployeeGetSet>getDataAdapter) {
        this.context = context;
        this.arraylist = new ArrayList<ShowEmployeeGetSet>();
        this.arraylist.addAll(getDataAdapter);
        this.favouriteList = getDataAdapter;

    }

    @NonNull
    @Override
    public ShowCheckInAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkin_adapter, parent, false);

        ShowCheckInAdapter.ViewHolder viewHolder = new ShowCheckInAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowCheckInAdapter.ViewHolder holder, int position) {

        final ShowEmployeeGetSet favouriteModal=favouriteList.get(position);






        holder.txtName.setText(favouriteModal.getName());

    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);




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