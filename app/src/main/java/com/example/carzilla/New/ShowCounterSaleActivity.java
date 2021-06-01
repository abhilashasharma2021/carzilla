package com.example.carzilla.New;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carzilla.R;

public class ShowCounterSaleActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txtPurchaseOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_counter_sale);


        imgBack = findViewById(R.id.imgBack);
        txtPurchaseOrder = findViewById(R.id.txtPurchaseOrder);
        txtPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowCounterSaleActivity.this, RepairCOunterSaleOrderActivity.class));
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