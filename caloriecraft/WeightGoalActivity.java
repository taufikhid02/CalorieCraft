package com.example.caloriecraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.caloriecraft.Objects.BodyStatistics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class WeightGoalActivity extends AppCompatActivity {
    Button btnNext;
    TextInputLayout tiWeightGoal;
    FloatingActionButton fabBack;
    TextInputEditText etWeightGoal;
    ImageView imgAddWeightGoal, imgMinusWeightGoal;
    double height, weight;
    int idealWeight, minimumWeight, maximumWeight, weightInput;
    int age, gender, activity, weightGoal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_goal);
        etWeightGoal = findViewById(R.id.et_weight_goal);
        fabBack = findViewById(R.id.fab_back_weight_goal);
        btnNext = findViewById(R.id.btn_next_weight_goal);
        tiWeightGoal = findViewById(R.id.ti_weight_goal);

        Bundle bundle = getIntent().getExtras();
        age = bundle.getInt("Age");
        height = bundle.getDouble("Height");
        weight = bundle.getDouble("Weight");
        gender = bundle.getInt("Gender");
        activity = bundle.getInt("Activity");

        BodyStatistics bodyStatistics = new BodyStatistics();
        idealWeight = bodyStatistics.getIdealWeight(height, gender);
        minimumWeight = bodyStatistics.getMinimumWeight(18.5, height);
        maximumWeight = bodyStatistics.getMaximumWeight(30, height);

        etWeightGoal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String weightInput = etWeightGoal.getText().toString().trim();

                if (!weightInput.isEmpty()){
                    try {
                        weightGoal = Integer.parseInt(weightInput);
                        if (weightGoal <= minimumWeight){
                            tiWeightGoal.setError("Goal must be above " + minimumWeight + " kg (Underweight)");
                            tiWeightGoal.setHelperText("");
                        }
                        else if (weightGoal >= maximumWeight){
                            tiWeightGoal.setError("Goal must be below " + maximumWeight + " kg (Obese)");
                            tiWeightGoal.setHelperText("");
                        }
                        else if (weightGoal == idealWeight){
                            tiWeightGoal.setHelperText("You're good to go!");
                            tiWeightGoal.setError(null); // Clear any existing error
                        }
                        else {
                            tiWeightGoal.setHelperText("Recommended weight is " + idealWeight + " kg (Normal)");
                            tiWeightGoal.setError(null); // Clear any existing error
                        }
                    } catch (NumberFormatException e) {
                        tiWeightGoal.setError("Please enter a valid number");
                    }
                }
                else {
                    tiWeightGoal.setError("Field is empty");
                    tiWeightGoal.setHelperText(""); // Clear helper text if present
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weightGoalInput = etWeightGoal.getText().toString();
                if (weightGoalInput.isEmpty()){
                    tiWeightGoal.setError("Field is empty");
                }
                else if (weightGoal <= minimumWeight){
                    tiWeightGoal.setError("Goal must be above " + minimumWeight + " kg (Underweight)");
                    //tvHelperError.setHelperText("");
                }
                else if (weightGoal >= maximumWeight) {
                    tiWeightGoal.setError("Goal must be below " + maximumWeight + " kg (Obese)");
                } else {
                    try {
                        weightGoal = Integer.parseInt(weightGoalInput);
                        Bundle bundleWeightGoal = new Bundle();
                        bundleWeightGoal.putDouble("Weight", weight);
                        bundleWeightGoal.putInt("Age", age);
                        bundleWeightGoal.putDouble("Height", height);
                        bundleWeightGoal.putInt("Activity", activity);
                        bundleWeightGoal.putInt("Gender", gender);
                        bundleWeightGoal.putInt("Weight Goal", weightGoal);
                        Intent goToCalorieTarget = new Intent(WeightGoalActivity.this, CalorieTargetActivity.class);
                        goToCalorieTarget.putExtras(bundleWeightGoal);
                        startActivity(goToCalorieTarget);
                    } catch (NumberFormatException e) {
                        Toast.makeText(WeightGoalActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    }
                    //Intent goToSetMacro = new Intent(WeightGoalActivity.this, MacroTargetActivity.class);
                    //goToSetMacro.putExtras(bundleWeightGoal);
                }

            }
        });

    }
}