package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PortionGuideDetailsActivity extends AppCompatActivity {
    TextView tvServingUnitDetails;
    ImageView imgServingUnitPicture;

    Toolbar toolbarPortionGuideDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portion_guide_details);
        tvServingUnitDetails = findViewById(R.id.tv_serving_unit_details_portion_guide_details);
        imgServingUnitPicture = findViewById(R.id.img_serving_unt_picture_portion_guide_details);
        toolbarPortionGuideDetails = findViewById(R.id.tb_portion_guide_details);

        setSupportActionBar(toolbarPortionGuideDetails);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarPortionGuideDetails.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarPortionGuideDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String servingUnitDetails = bundle.getString("Serving Unit Detail");
        int servingUnitPicture = bundle.getInt("Serving Unit Picture");
        tvServingUnitDetails.setText(servingUnitDetails);
        imgServingUnitPicture.setImageResource(servingUnitPicture);

    }
}