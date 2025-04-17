package com.example.caloriecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caloriecraft.Objects.Date;
import com.example.caloriecraft.Objects.FoodDiary;
import com.example.caloriecraft.Objects.TitleCaseConverter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuickAddActivity extends AppCompatActivity {
    Toolbar toolbarQuickAdd;
    EditText etFoodTitle, etServingSize, etServingUnit, etFoodCalorie, etFoodProtein, etFoodFat, etFoodCarb;
    TextInputLayout tiFoodTitle, tiFoodCalorie;
    Button btnAddToDiary;
    String foodTitle, mealTime, servingUnit, date;
    double servingNumber, existingServingNumber, servingSize, foodCalorie, foodProtein, foodFat, foodCarb;
    Spinner spinnerMealTime;
    Date todayDate = new Date();
    DatabaseReference databaseReference, databaseUserList, databaseFoodItem;
    Intent goToDiary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_add);
        toolbarQuickAdd = findViewById(R.id.tb_quick_add);
        tiFoodTitle = findViewById(R.id.ti_food_title_quick_add);
        tiFoodCalorie = findViewById(R.id.ti_calorie_quick_add);
        etFoodTitle = findViewById(R.id.et_food_title_quick_add);
        etServingSize = findViewById(R.id.et_serving_size_quick_add);
        etServingUnit = findViewById(R.id.et_serving_unit_quick_add);
        spinnerMealTime = findViewById(R.id.spinner_meal_time_quick_add);
        etFoodCalorie = findViewById(R.id.et_calorie_quick_add);
        etFoodProtein = findViewById(R.id.et_protein_quick_add);
        etFoodFat = findViewById(R.id.et_fat_quick_add);
        etFoodCarb = findViewById(R.id.et_carb_quick_add);
        btnAddToDiary = findViewById(R.id.btn_add_to_diary_quick_add);

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

        etFoodTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etFoodTitle.getText().toString().isEmpty()){
                    tiFoodTitle.setError("Field is empty");
                }
                else {
                    tiFoodTitle.setError("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etFoodCalorie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etFoodCalorie.getText().toString().isEmpty()){
                    tiFoodCalorie.setError("Field is empty");
                }
                else {
                    tiFoodCalorie.setError("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setSupportActionBar(toolbarQuickAdd);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarQuickAdd.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarQuickAdd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnAddToDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etFoodTitle.getText().toString().isEmpty() && !etFoodCalorie.getText().toString().isEmpty()){
                    //This part here (line 126-132) may take some times to understand.
                    // But believe me, the coding below is a shorter yet efficient version compared to the original one on line 143-173
                    // This basically involves three user-defined functions (I made this myself...) which are getStringValue, getDoubleValueOrDefault, and GetStringValueOrDefault.
                    // 1. getStringValue helps shorten the coding. Basically, instead of etFoodTitle.getText.toString(), you can use getStringValue(etFoodTitle) which saves you a lot of time.
                    // 2. getDoubleValueOrDefault is used for optional field like Food Protein and Carb where if it's empty, then it will return a default value like 0. Otherwise, it will return the value inserted by user.
                    // 3. getStringValueOrDefault works the same way as no. 2 except that it's for String and not Double.

                    foodTitle = getStringValue(etFoodTitle);
                    foodCalorie = Double.parseDouble(getStringValue(etFoodCalorie));
                    foodProtein = getDoubleValueOrDefault(etFoodProtein, 0);
                    foodFat = getDoubleValueOrDefault(etFoodFat, 0);
                    foodCarb = getDoubleValueOrDefault(etFoodCarb, 0);
                    servingSize = getDoubleValueOrDefault(etServingSize, 1);
                    servingUnit = getStringValueOrDefault(etServingUnit, "serving");
                    /*if (!etFoodProtein.getText().toString().isEmpty()){
                        foodProtein = Double.parseDouble(etFoodProtein.getText().toString());
                    }
                    else {
                        foodProtein = 0;
                    }

                    if (!etFoodFat.getText().toString().isEmpty()){
                        foodFat = Double.parseDouble(etFoodFat.getText().toString());
                    }
                    else {
                        foodFat = 0;
                    }
                    if (!etFoodCarb.getText().toString().isEmpty()){
                        foodCarb = Double.parseDouble(etFoodCarb.getText().toString());
                    }
                    else {
                        foodCarb = 0;
                    }
                    if (!etServingSize.getText().toString().isEmpty()){
                        servingSize = Double.parseDouble(etServingSize.getText().toString());
                    }
                    else {
                        servingSize = 0;
                    }
                    if (!etServingUnit.getText().toString().isEmpty()){
                        servingUnit = etServingUnit.getText().toString();
                    }
                    else {
                        servingUnit = "serving";
                    }*/
                    saveFoodInfo(mealTime);
                }
                if (etFoodTitle.getText().toString().isEmpty()){
                    tiFoodTitle.setError("Field is empty");
                }
                if (etFoodCalorie.getText().toString().isEmpty()){
                    tiFoodCalorie.setError("Field is empty");
                }
            }
        });
    }

    private String getStringValue(EditText editText){
        return editText.getText().toString();
    }
    private String getStringValueOrDefault(EditText editText, String defaultValue) {
        if (!getStringValue(editText).isEmpty()){
            return getStringValue(editText);
        }
        else {
            return defaultValue;
        }
    }
    private double getDoubleValueOrDefault(EditText editText, int defaultValue) {
        if(!getStringValue(editText).isEmpty()){
            return Double.parseDouble(getStringValue(editText));
        }
        else {
            return defaultValue;
        }
    }

    //saveFoodInfo is used in EditFoodDetailsActivity, FoodDetailsActivity, and QuickAddActivity
    //here it uses foodTitleQuickAdd instead of foodTitle;
    private void saveFoodInfo(String mealTime) {
        //TitleCaseConverter titleCaseConverter = new TitleCaseConverter();
        servingNumber = 1;
        date = todayDate.getTodayDate();
        String foodTitleQuickAdd = concatWord(foodTitle, "(Quick Add)");
        //String foodTitleQuickAdd = titleCaseConverter.convertToTitleCaseIteratingChars(foodTitle) + " (Quick Add)";
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseUserList = databaseReference.child(userID);
        databaseFoodItem = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child(mealTime).child(foodTitleQuickAdd);
        databaseFoodItem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    existingServingNumber = snapshot.child("servingNumber").getValue(Double.class);
                    servingNumber += existingServingNumber;
                    Toast.makeText(QuickAddActivity.this, String.valueOf(servingNumber), Toast.LENGTH_SHORT).show();
                } else {
                    servingNumber = servingNumber + 0;
                    Toast.makeText(QuickAddActivity.this, String.valueOf(servingNumber), Toast.LENGTH_SHORT).show();
                }
                FoodDiary foodDiary = new FoodDiary(foodTitleQuickAdd, foodCalorie*servingNumber, foodProtein*servingNumber, foodFat*servingNumber, foodCarb*servingNumber, servingNumber, servingSize, servingUnit);
                databaseUserList.child("mealsDiary").child(date).child(mealTime).child(foodTitleQuickAdd).setValue(foodDiary);
                updateProgress("calorieProgress");
                updateProgress("proteinProgress");
                updateProgress("fatProgress");
                updateProgress("carbProgress");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(QuickAddActivity.this, "Food added into diary", Toast.LENGTH_SHORT).show();
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
    private String concatWord(String foodTitle, String word) {
        TitleCaseConverter titleCaseConverter = new TitleCaseConverter();
        int wordLength = foodTitle.indexOf(word);
        String newFoodTitle = null;
        if (wordLength > 0){
            newFoodTitle = titleCaseConverter.convertToTitleCaseIteratingChars(foodTitle.replace(word, "")) + "(Quick Add)";
            return newFoodTitle;
        }
        else {
            newFoodTitle = titleCaseConverter.convertToTitleCaseIteratingChars(foodTitle) + " (Quick Add)";
            return newFoodTitle;
            //return titleCaseConverter.convertToTitleCaseIteratingChars(foodTitle) + " (Quick Add)";
        }

    }


}