package com.example.caloriecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
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

import com.example.caloriecraft.Objects.Date;
import com.example.caloriecraft.Objects.FoodDiary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class EditFoodDetailsActivity extends AppCompatActivity {

    TextView tvFoodTitle, tvFoodCalorie, tvFoodProtein, tvFoodFat, tvFoodCarb, etServingSize, tvPortionGuide;
    ImageView imgAddServingNumber, imgMinusServingNumber;
    EditText etServingNumber;
    Spinner spinnerMealTime;
    Toolbar toolbarEditFood;
    Button btnSave, btnRemove;
    String foodTitle, servingUnit, mealTime, mealTime2, date;
    double servingSize, servingNumber, existingServingNumber;
    double foodCalorie, foodProtein, foodFat, foodCarb;
    double foodCaloriePerServing, foodProteinPerServing, foodFatPerServing, foodCarbPerServing;
    DatabaseReference databaseReference, databaseUserList, databaseFoodItem;
    DecimalFormat nodp = new DecimalFormat("0");
    DecimalFormat twodp = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_details);
        tvFoodTitle = findViewById(R.id.tv_food_title_edit_food_details);
        tvFoodCalorie = findViewById(R.id.tv_food_calorie_edit_food_details);
        tvFoodProtein = findViewById(R.id.tv_protein_value_edit_food_details);
        tvFoodFat = findViewById(R.id.tv_fat_value_edit_food_details);
        tvFoodCarb = findViewById(R.id.tv_carb_value_edit_food_details);
        etServingNumber = findViewById(R.id.et_serving_number_edit_food_details);
        etServingSize = findViewById(R.id.et_serving_size_edit_food_details);
        spinnerMealTime = findViewById(R.id.spinner_meal_time_edit_food_details);
        toolbarEditFood = findViewById(R.id.tb_edit_food_details);
        btnSave = findViewById(R.id.btn_save_changes_edit_food_details);
        btnRemove = findViewById(R.id.btn_remove_from_diary_edit_food_details);
        imgAddServingNumber = findViewById(R.id.img_add_serving_number_edit_food_details);
        imgMinusServingNumber = findViewById(R.id.img_minus_serving_number_edit_food_details);
        tvPortionGuide = findViewById(R.id.tv_portion_guide_edit_food_details);

        Date todayDate = new Date();
        date = todayDate.getTodayDate();

        setSupportActionBar(toolbarEditFood);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayShowHomeEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);
        toolbarEditFood.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarEditFood.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ArrayAdapter<CharSequence> mealTimeAdapter = ArrayAdapter.createFromResource(this, R.array.meal_time, android.R.layout.simple_list_item_1);
        mealTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealTime.setAdapter(mealTimeAdapter);
        spinnerMealTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mealTime2 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent intent = getIntent();
        foodTitle = intent.getStringExtra("Food Title");
        mealTime = intent.getStringExtra("Meal Time");
        foodCalorie = intent.getDoubleExtra("Food Calorie", 0); // 0 is a default value
        foodProtein = intent.getDoubleExtra("Food Protein", 0); // 0 is a default value
        foodFat = intent.getDoubleExtra("Food Fat", 0); // 0 is a default value
        foodCarb = intent.getDoubleExtra("Food Carb", 0); // 0 is a default value
        servingNumber = intent.getDoubleExtra("Serving Number", 0);
        servingSize = intent.getDoubleExtra("Serving Size", 0);
        servingUnit = intent.getStringExtra("Serving Unit");

        foodCaloriePerServing = foodCalorie/servingNumber;
        foodProteinPerServing = foodProtein/servingNumber;
        foodFatPerServing = foodFat/servingNumber;
        foodCarbPerServing = foodCarb/servingNumber;

        tvFoodTitle.setText(foodTitle);
        tvFoodCalorie.setText(nodp.format(foodCalorie) + " Cal");
        tvFoodProtein.setText(twodp.format(foodProtein) + " g");
        tvFoodFat.setText(twodp.format(foodFat) + " g");
        tvFoodCarb.setText(twodp.format(foodCarb) + " g");
        etServingNumber.setText(String.valueOf(servingNumber));
        etServingSize.setText(servingSize + ", " + servingUnit);

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFoodInfo(mealTime);
                saveFoodInfo(mealTime2);
                Toast.makeText(EditFoodDetailsActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
            }
        });

        tvPortionGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToPortionGuide = new Intent(EditFoodDetailsActivity.this, PortionGuideActivity.class);
                startActivity(goToPortionGuide);
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditFoodDetailsActivity.this);
                builder.setTitle("Remove Food Item");
                builder.setMessage("Are you sure to remove this food?");
                builder
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeFoodInfo(mealTime);
                                Toast.makeText(EditFoodDetailsActivity.this, "Food item has been removed", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();

            }
        });

        if (mealTime.equals("Breakfast")){
            spinnerMealTime.setSelection(0);
        }
        else if (mealTime.equals("Lunch")){
            spinnerMealTime.setSelection(1);
        }
        else if (mealTime.equals("Dinner")){
            spinnerMealTime.setSelection(2);
        }
        else if (mealTime.equals("Snacks")){
            spinnerMealTime.setSelection(3);
        }
    }

    private void displayValue(double servingNumber) {

        tvFoodCalorie.setText(nodp.format(foodCaloriePerServing*servingNumber) + " Cal");
        tvFoodProtein.setText(twodp.format(foodProteinPerServing*servingNumber) + " g");
        tvFoodFat.setText(twodp.format(foodFatPerServing*servingNumber) + " g");
        tvFoodCarb.setText(twodp.format(foodCarbPerServing*servingNumber) + " g");
    }

    private void removeFoodInfo(String mealTime) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseFoodItem = databaseReference.child(userID).child("mealsDiary").child(date).child(mealTime).child(foodTitle);
        databaseFoodItem.removeValue();
        /*updateProgress("calorieProgress", false);
        updateProgress("proteinProgress", false);
        updateProgress("fatProgress", false);
        updateProgress("carbProgress", false);*/
    }

    //saveFoodInfo is used in EditFoodDetailsActivity, FoodDetailsActivity, and QuickAddActivity
    //here it uses foodCaloriesPerServing instead of just foodCalories (same goes for protein, fat, and carb)
    private void saveFoodInfo(String mealTime) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseUserList = databaseReference.child(userID);
        databaseFoodItem = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child(mealTime).child(foodTitle);
        databaseFoodItem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    existingServingNumber = snapshot.child("servingNumber").getValue(Double.class);
                    servingNumber += existingServingNumber;
                    Toast.makeText(EditFoodDetailsActivity.this, String.valueOf(servingNumber), Toast.LENGTH_SHORT).show();
                } else {
                    servingNumber = servingNumber + 0;
                    Toast.makeText(EditFoodDetailsActivity.this, String.valueOf(servingNumber), Toast.LENGTH_SHORT).show();
                }
                FoodDiary foodDiary = new FoodDiary(foodTitle, foodCaloriePerServing*servingNumber, foodProteinPerServing*servingNumber, foodFatPerServing*servingNumber, foodCarbPerServing*servingNumber, servingNumber, servingSize, servingUnit);
                databaseUserList.child("mealsDiary").child(date).child(mealTime).child(foodTitle).setValue(foodDiary);
                /*updateProgress("calorieProgress", true);
                updateProgress("proteinProgress", true);
                updateProgress("fatProgress", true);
                updateProgress("carbProgress", true);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateProgress(String progress, boolean isAdd) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child(progress);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double progressValue = 0;
                if (snapshot.exists()){
                    progressValue = snapshot.getValue(Double.class);
                    if (isAdd == true){
                        if (progress.equals("calorieProgress")){
                            progressValue += (foodCaloriePerServing*servingNumber);
                        }
                        if (progress.equals("proteinProgress")){
                            progressValue += (foodProteinPerServing*servingNumber);
                        }
                        if (progress.equals("fatProgress")){
                            progressValue += (foodFatPerServing*servingNumber);
                        }
                        if (progress.equals("carbProgress")){
                            progressValue += (foodCarbPerServing*servingNumber);
                        }
                    }
                    if (isAdd == false){
                        if (progress.equals("calorieProgress")){
                            progressValue -= (foodCaloriePerServing*servingNumber);
                        }
                        if (progress.equals("proteinProgress")){
                            progressValue -= (foodProteinPerServing*servingNumber);
                        }
                        if (progress.equals("fatProgress")){
                            progressValue -= (foodFatPerServing*servingNumber);
                        }
                        if (progress.equals("carbProgress")){
                            progressValue -= (foodCarbPerServing*servingNumber);
                        }
                    }

                }
                else {
                    if (progress.equals("calorieProgress")){
                        progressValue = (foodCaloriePerServing*servingNumber);
                    }
                    if (progress.equals("proteinProgress")){
                        progressValue = (foodProteinPerServing*servingNumber);
                    }
                    if (progress.equals("fatProgress")){
                        progressValue = (foodFatPerServing*servingNumber);
                    }
                    if (progress.equals("carbProgress")){
                        progressValue = (foodCarbPerServing*servingNumber);
                    }
                }
                databaseReference1.setValue(progressValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}