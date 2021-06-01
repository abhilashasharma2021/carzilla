package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.carzilla.R;

public class ShowIttemMasterActivity extends AppCompatActivity {
    ImageView imgBack;
    CardView cardCheckin,cardJob,cerdRepair,cardMaster,cerPartMaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ittem_master);


        imgBack = findViewById(R.id.imgBack);
        cardCheckin = findViewById(R.id.cardCheckin);
        cardJob = findViewById(R.id.cardJob);
        cerdRepair = findViewById(R.id.cerdRepair);
        cardMaster = findViewById(R.id.cardMaster);
        cerPartMaster = findViewById(R.id.cerPartMaster);



        cerPartMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowIttemMasterActivity.this,SHowPartsMastereActivity.class));
            }
        });

        cardMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowIttemMasterActivity.this,ShowServiceMasterActivity.class));
            }
        });

        cerdRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowIttemMasterActivity.this,ShowRepairTagActivity.class));
            }
        });

        cardJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowIttemMasterActivity.this,ShowJonbTypeActivity.class));
            }
        });

        cardCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ShowIttemMasterActivity.this,ShowCheckInActivity.class));
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}