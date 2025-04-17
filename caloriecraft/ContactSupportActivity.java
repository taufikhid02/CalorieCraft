package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import java.nio.channels.AcceptPendingException;

public class ContactSupportActivity extends AppCompatActivity {
    Toolbar toolbarContactSupport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);
        toolbarContactSupport = findViewById(R.id.tb_contact_support);
        setSupportActionBar(toolbarContactSupport);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarContactSupport.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarContactSupport.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}