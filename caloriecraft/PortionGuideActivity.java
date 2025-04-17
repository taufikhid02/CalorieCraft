package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caloriecraft.Adapters.ServingUnitRecyclerViewAdapter;
import com.example.caloriecraft.Objects.ServingUnit;

import java.util.ArrayList;
import java.util.List;

public class PortionGuideActivity extends AppCompatActivity {
    RecyclerView rvServingUnit;
    ImageView imgNextServingUnit, imgPreviousServingUnit;
    ServingUnitRecyclerViewAdapter servingUnitRecylerViewAdapter;
    LinearLayoutManager linearLayoutManager;
    TextView tvServingUnit;
    List<ServingUnit> servingUnitList;
    Toolbar toolbarPortionGuide;
    CardView cvServingUnit;
    int unit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portion_guide);
        toolbarPortionGuide = findViewById(R.id.tb_portion_guide);
        rvServingUnit = findViewById(R.id.rv_serving_unit_row_portion_guide);
        tvServingUnit = findViewById(R.id.tv_serving_unit_portion_guide);
        imgNextServingUnit = findViewById(R.id.img_next_serving_unit_portion_guide);
        imgPreviousServingUnit = findViewById(R.id.img_previous_serving_unit_food_portion);
        rvServingUnit = findViewById(R.id.rv_serving_unit_row_portion_guide);
        cvServingUnit = findViewById(R.id.cv_serving_unit_portion_guide);
        cvServingUnit.bringToFront();
        setSupportActionBar(toolbarPortionGuide);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarPortionGuide.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarPortionGuide.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        linearLayoutManager = new LinearLayoutManager(PortionGuideActivity.this);
        rvServingUnit.setLayoutManager(linearLayoutManager);
        rvServingUnit.setHasFixedSize(true);
        unit = 1;
        updateRecyclerView();
        ServingUnitRecyclerViewAdapter servingUnitRecylerViewAdapter = new ServingUnitRecyclerViewAdapter(PortionGuideActivity.this, servingUnitList);
        rvServingUnit.setAdapter(servingUnitRecylerViewAdapter);
        imgNextServingUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unit == 6){
                    unit = 6;
                }
                else {
                    unit += 1;
                }
                updateRecyclerView();
            }
        });
        imgPreviousServingUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unit == 1){
                    unit = 1;
                }
                else {
                    unit -= 1;
                }
                updateRecyclerView();
            }
        });
    }

    private void updateRecyclerView() {
        setServingUnit(unit);
        servingUnitList = getServingUnitList(unit);
        servingUnitRecylerViewAdapter = new ServingUnitRecyclerViewAdapter(PortionGuideActivity.this, servingUnitList);
        rvServingUnit.setAdapter(servingUnitRecylerViewAdapter);
    }

    private void setServingUnit(int unit) {
        if (unit == 1){
            tvServingUnit.setText("Cup");
            imgPreviousServingUnit.setVisibility(View.GONE);
            imgNextServingUnit.setVisibility(View.VISIBLE);
        } else if (unit == 2) {
            tvServingUnit.setText("Slice");
        } else if (unit == 3) {
            tvServingUnit.setText("Ounce (Oz)");
        } else if (unit == 4) {
            tvServingUnit.setText("Tablespoon (tbsp)");
        } else if (unit == 5) {
            tvServingUnit.setText("Teaspoon (tsp)");
        } else if (unit == 6) {
            tvServingUnit.setText("Medium Fruit");
        }
        displayNextPreviousButton(unit);
    }

    private void displayNextPreviousButton(int unit) {
        if (unit==1){
            imgPreviousServingUnit.setVisibility(View.GONE);
            imgNextServingUnit.setVisibility(View.VISIBLE);
        }
        else if (unit == 6){
            imgPreviousServingUnit.setVisibility(View.VISIBLE);
            imgNextServingUnit.setVisibility(View.GONE);
        }
        else {
            imgPreviousServingUnit.setVisibility(View.VISIBLE);
            imgNextServingUnit.setVisibility(View.VISIBLE);
        }
    }

    private List<ServingUnit> getServingUnitList(int unit) {
        List<ServingUnit> servingUnitList = new ArrayList<>();
        if (unit == 1) { //unit = cup
            servingUnitList.add(new ServingUnit("1 Cup\n=\n1 Baseball Ball", R.drawable.bg_1_cup));
            servingUnitList.add(new ServingUnit("1 Cup of Low-Fat Milk\n=\n1 Carton of Milk", R.drawable.bg_1_cup_low_fat_milk));
            servingUnitList.add(new ServingUnit("1/2 Cup\n=\n1/2 Baseball Ball", R.drawable.bg_half_cup));
            servingUnitList.add(new ServingUnit("1/3 Cup\n=\n1 Tennis Ball", R.drawable.bg_one_third_cup));
            servingUnitList.add(new ServingUnit("1/4 Cup\n=\n1 Golf ball", R.drawable.bg_one_forth_cup));
        } if (unit == 2){ //unit = slice
            servingUnitList.add(new ServingUnit("1 Slice\n=\n1 Disk", R.drawable.bg_slice));
        } if (unit == 3) { //unit = ounce (oz)
            servingUnitList.add(new ServingUnit("1 Oz\n=\n1/2 Baseball Ball", R.drawable.bg_1_oz));
            servingUnitList.add(new ServingUnit("3 Oz of Fish\n=\n1 Checkbook", R.drawable.bg_3_oz_fish));
            servingUnitList.add(new ServingUnit("3 Oz of Chicken/Beef\n=\n1 Deck of Cards", R.drawable.bg_3_oz_chicken));
        } if (unit == 4) { //unit = tablespoon (tbsp)
            servingUnitList.add(new ServingUnit("1 tbsp\n=\n1/2 one-ounce shot glass", R.drawable.bg_1_tbsp));
            servingUnitList.add(new ServingUnit("2 tbsp\n=\n1 one-ounce shot glass", R.drawable.bg_2_tbsp));
        } if (unit == 5) { //unit = teaspoon (tsp)
            servingUnitList.add(new ServingUnit("1 tsp\n=\n1 Die", R.drawable.bg_1_tsp));
        } if (unit == 6){ //unit = medium fruit
            servingUnitList.add(new ServingUnit("1 medium fruit\n=\n1 Baseball Ball", R.drawable.bg_1_medium_fruit));
        }

        return servingUnitList;
    }
}