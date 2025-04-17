package com.example.caloriecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.caloriecraft.Objects.FoodDiary;
import com.example.caloriecraft.Objects.Date;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class FoodDetailsActivity extends AppCompatActivity {
    Toolbar toolbarFoodDetails;
    TextView tvFoodTitle, tvFoodCalorie, tvFoodProtein, tvFoodFat, tvFoodCarb, tvServingSize, tvPortionGuide;
    EditText etServingNumber;
    Button btnAddToDiary;
    ImageView imgAddServingNumber, imgMinusServingNumber;
    Spinner spinnerMealTime;
    DecimalFormat nodp = new DecimalFormat("0");
    DecimalFormat twodp = new DecimalFormat("0.00");
    DatabaseReference databaseReference, databaseUserList, databaseFoodItem;
    String foodTitle, foodBrand, servingUnit, mealTime, date;
    double servingSize, servingNumber, existingServingNumber;
    double foodCalorie, foodProtein, foodFat, foodCarb;
    Intent goToDiary;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        tvFoodTitle = findViewById(R.id.tv_food_title_food_details);
        tvFoodCalorie = findViewById(R.id.tv_food_calorie_food_details);
        tvFoodProtein = findViewById(R.id.tv_protein_value_food_details);
        tvFoodFat = findViewById(R.id.tv_fat_value_food_details);
        tvFoodCarb = findViewById(R.id.tv_carb_value_food_details);
        //tvServingUnit = findViewById(R.id.tv_serving_unit_food_details);
        toolbarFoodDetails = findViewById(R.id.tb_food_details);
        btnAddToDiary = findViewById(R.id.btn_add_to_diary_food_details);
        imgAddServingNumber = findViewById(R.id.img_add_serving_number_food_details);
        imgMinusServingNumber = findViewById(R.id.img_minus_serving_number_food_details);
        etServingNumber = findViewById(R.id.et_serving_number_food_details);
        tvServingSize = findViewById(R.id.et_serving_size_food_details);
        spinnerMealTime = findViewById(R.id.spinner_meal_time_food_details);
        tvPortionGuide = findViewById(R.id.tv_portion_guide_food_details);

        Bundle bundle = getIntent().getExtras();
        foodTitle = bundle.getString("Food Title");
        foodBrand = bundle.getString("Food Brand");
        foodCalorie = bundle.getDouble("Food Calorie");
        foodProtein = bundle.getDouble("Food Protein");
        foodFat = bundle.getDouble("Food Fat");
        foodCarb = bundle.getDouble("Food Carb");
        servingUnit = bundle.getString("Serving Unit");
        servingSize = Double.parseDouble(bundle.getString("Serving Size"));

        Date todayDate = new Date();
        date = todayDate.getTodayDate();
        servingNumber = 1;

        tvServingSize.setText(String.valueOf(servingSize+", " + servingUnit));
        etServingNumber.setText(String.valueOf(servingNumber));

        tvServingSize.setClickable(false);
        etServingNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    if (!etServingNumber.getText().toString().isEmpty()){
                        servingNumber = Double.parseDouble(etServingNumber.getText().toString());
                    }
                    else {
                        servingNumber = 1;
                        etServingNumber.setText(String.valueOf(servingNumber));
                    }
                    displayValue(servingNumber);
                }
                return false;
            }
        });
        imgAddServingNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servingNumber = servingNumber + 1;
                etServingNumber.setText(String.valueOf(servingNumber));
                displayValue(servingNumber);
            }
        });
        imgMinusServingNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (servingNumber <= 1.99){
                    servingNumber = 1;
                }
                else if (servingNumber > 1){
                    servingNumber = servingNumber - 1;
                }
                etServingNumber.setText(String.valueOf(servingNumber));
                displayValue(servingNumber);
            }
        });

        tvPortionGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToPortionGuide = new Intent(FoodDetailsActivity.this, PortionGuideActivity.class);
                startActivity(goToPortionGuide);
                //replaceFragment(new PortionGuideFragment());
            }
        });
        if (!foodBrand.equals("-")){
            //tvFoodTitle.setText(foodTitle);
            foodTitle = foodBrand + ", " + foodTitle;
        }
        else {
            foodTitle = ""+foodTitle;
        }
        tvFoodTitle.setText(foodTitle);
        tvFoodCalorie.setText(String.valueOf(nodp.format(foodCalorie)) + " Cal");
        tvFoodProtein.setText(String.valueOf(foodProtein) +" g");
        tvFoodFat.setText(String.valueOf(foodFat) +" g");
        tvFoodCarb.setText(String.valueOf(foodCarb) +" g");
        //tvServingUnit.setText(servingUnit);

        ArrayAdapter<CharSequence> mealTimeAdapter = ArrayAdapter.createFromResource(this, R.array.meal_time, android.R.layout.simple_list_item_1);
        mealTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealTime.setAdapter(mealTimeAdapter);
        spinnerMealTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mealTime = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setSupportActionBar(toolbarFoodDetails);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayShowHomeEnabled(true);
        toolbarFoodDetails.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarFoodDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnAddToDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodInfo(mealTime);
                updateProgress("calorieProgress");
                updateProgress("proteinProgress");
                updateProgress("fatProgress");
                updateProgress("carbProgress");
            }
        });
    }

    private void displayValue(double servingNumber) {
        tvFoodCalorie.setText(String.valueOf(nodp.format(foodCalorie*servingNumber)) + " Cal");
        tvFoodProtein.setText(String.valueOf(twodp.format(foodProtein*servingNumber)) +" g");
        tvFoodFat.setText(String.valueOf(twodp.format(foodFat*servingNumber)) +" g");
        tvFoodCarb.setText(String.valueOf(twodp.format(foodCarb*servingNumber)) +" g");
    }

    //saveFoodInfo is used in EditFoodDetailsActivity, FoodDetailsActivity, and QuickAddActivity
    //the most normal one because it doesn't have to use foodTitleQuickAdd or foodCaloriesPerServing
    private void saveFoodInfo(String mealTime) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseUserList = databaseReference.child(userID);

        //check for existing food before saving
        databaseFoodItem = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child(mealTime).child(foodTitle);
        databaseFoodItem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    existingServingNumber = snapshot.child("servingNumber").getValue(Double.class);
                    servingNumber += existingServingNumber;
                    //Toast.makeText(FoodDetailsActivity.this, String.valueOf(servingNumber), Toast.LENGTH_SHORT).show();
                } else {
                    servingNumber = servingNumber + 0;
                    //Toast.makeText(FoodDetailsActivity.this, String.valueOf(servingNumber), Toast.LENGTH_SHORT).show();
                }
                FoodDiary foodDiary = new FoodDiary(foodTitle, foodCalorie*servingNumber, foodProtein*servingNumber, foodFat*servingNumber, foodCarb*servingNumber, servingNumber, servingSize, servingUnit);
                databaseUserList.child("mealsDiary").child(date).child(mealTime).child(foodTitle).setValue(foodDiary);
                //updateProgress();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(FoodDetailsActivity.this, "Food added into diary", Toast.LENGTH_SHORT).show();
    }

    private void updateProgress(String progress) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child(progress);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double progressValue = 0;
                if (snapshot.exists()){
                    progressValue = snapshot.getValue(Double.class);
                    if (progress.equals("calorieProgress")){
                        progressValue += (foodCalorie*servingNumber);
                    }
                    if (progress.equals("proteinProgress")){
                        progressValue += (foodProtein*servingNumber);
                    }
                    if (progress.equals("fatProgress")){
                        progressValue += (foodFat*servingNumber);
                    }
                    if (progress.equals("carbProgress")){
                        progressValue += (foodCarb*servingNumber);
                    }
                }
                else {
                    if (progress.equals("calorieProgress")){
                        progressValue = (foodCalorie*servingNumber);
                    }
                    if (progress.equals("proteinProgress")){
                        progressValue = (foodProtein*servingNumber);
                    }
                    if (progress.equals("fatProgress")){
                        progressValue = (foodFat*servingNumber);
                    }
                    if (progress.equals("carbProgress")){
                        progressValue = (foodCarb*servingNumber);
                    }
                }
                databaseReference1.setValue(progressValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /*databaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    double calorieProgress = snapshot.child("calorieProgress").getValue(Double.class);
                    double proteinProgress = snapshot.child("proteinProgress").getValue(Double.class);
                    double fatProgress = snapshot.child("fatProgress").getValue(Double.class);
                    double carbProgress = snapshot.child("carbProgress").getValue(Double.class);
                    calorieProgress += (foodCalorie*servingNumber);
                    proteinProgress += (foodProtein*servingNumber);
                    fatProgress += (foodFat*servingNumber);
                    carbProgress += (foodCarb*servingNumber);
                    databaseUserList.child("mealsDiary").child(date).child("calorieProgress").setValue(calorieProgress);
                    databaseUserList.child("mealsDiary").child(date).child("proteinProgress").setValue(proteinProgress);
                    databaseUserList.child("mealsDiary").child(date).child("fatProgress").setValue(fatProgress);
                    databaseUserList.child("mealsDiary").child(date).child("carbProgress").setValue(carbProgress);
                    Toast.makeText(FoodDetailsActivity.this, calorieProgress +" " + proteinProgress + " " + fatProgress + " " + carbProgress, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

}