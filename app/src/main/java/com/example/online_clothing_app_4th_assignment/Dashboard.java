package com.example.online_clothing_app_4th_assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    Button add_item, view_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add_item = findViewById(R.id.add_intent);
        add_item.setOnClickListener(this);
        view_item = findViewById(R.id.view_intent);
        view_item.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_intent){

        }
        if (v.getId() == R.id.view_intent){

        }
    }
}
