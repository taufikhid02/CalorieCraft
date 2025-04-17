package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class AboutUsActivity extends AppCompatActivity {
    Toolbar toolbarAboutUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbarAboutUs = findViewById(R.id.tb_about_us);
        setSupportActionBar(toolbarAboutUs);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarAboutUs.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarAboutUs.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}