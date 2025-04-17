package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

public class BMIInfoActivity extends AppCompatActivity {
    TextView tvUnderweight, tvNormal, tvOverweight, tvObese;
    Chip chipUnderweight, chipNormal, chipOverweight, chipObese;
    HorizontalScrollView hsvBMIAwareness;
    ScrollView svBMIAwareness;
    ObjectAnimator animator, animator2;
    Toolbar toolbarBMIAwareness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_info);
        hsvBMIAwareness = findViewById(R.id.hsv_bmi_awareness);
        svBMIAwareness = findViewById(R.id.sv_bmi_awareness);
        toolbarBMIAwareness = findViewById(R.id.tb_bmi_awareness);
        tvUnderweight = findViewById(R.id.tv_underweight_bmi_awareness);
        tvNormal = findViewById(R.id.tv_normal_bmi_awareness);
        tvOverweight = findViewById(R.id.tv_overweight_bmi_awareness);
        tvObese = findViewById(R.id.tv_obese_bmi_awareness);
        chipUnderweight = findViewById(R.id.chip_underweight_bmi_awareness);
        chipNormal = findViewById(R.id.chip_normal_bmi_awareness);
        chipOverweight = findViewById(R.id.chip_overweight_bmi_awareness);
        chipObese = findViewById(R.id.chip_obese_bmi_awareness);

        setSupportActionBar(toolbarBMIAwareness);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarBMIAwareness.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarBMIAwareness.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        chipUnderweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToSpecificSection(tvUnderweight, chipUnderweight);
            }
        });
        chipNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToSpecificSection(tvNormal, chipNormal);
            }
        });
        chipOverweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToSpecificSection(tvOverweight, chipOverweight);
            }
        });
        chipObese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToSpecificSection(tvObese, chipObese);
            }
        });


    }

    private void scrollToSpecificSection(TextView textView, Button button) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                animator = ObjectAnimator.ofInt(svBMIAwareness, "scrollY", textView.getTop());
                animator2 = ObjectAnimator.ofInt(hsvBMIAwareness, "scrollX", button.getLeft());
                animator.start();
                animator2.start();
            }
        });
    }
}