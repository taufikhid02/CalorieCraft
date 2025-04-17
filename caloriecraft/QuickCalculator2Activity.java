package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriecraft.Objects.BodyStatistics;

import java.text.DecimalFormat;

public class QuickCalculator2Activity extends AppCompatActivity {
    CardView cvMacro;
    ScrollView svQuickCalculator2;
    TextView tvBMIValue, tvBMIStatus, tvBMIAdvice, tvIdealWeight, tvCalorieMaintenance, tvGainWeightMild, tvGainWeightModerate, tvLoseWeightMild, tvLoseWeightModerate;
    TextView tvModerateProteinGoal, tvModerateFatGoal, tvModerateCarbGoal, tvLowerProteinGoal, tvLowerFatGoal, tvLowerCarbGoal, tvHighProteinGoal, tvHighFatGoal, tvHighCarbGoal, tvCalorieTarget;
    int gender, age, activity, calorieTarget, idealWeight, calorieMaintenance, calorieSurplusMild, calorieSurplusModerate, calorieDeficitMild, calorieDeficitModerate;
    String BMIStatus, BMIAdvice;
    double height, weight, BMIValue;
    Toolbar toolbarQuickCalculator2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_calculator2);
        tvBMIValue = findViewById(R.id.tv_bmi_value_quick_calculator2);
        tvBMIStatus = findViewById(R.id.tv_bmi_status_quick_calculator2);
        tvBMIAdvice = findViewById(R.id.tv_bmi_advice_quick_calculator2);
        tvIdealWeight = findViewById(R.id.tv_ideal_weight_quick_calculator2);
        tvCalorieMaintenance = findViewById(R.id.tv_maintenance_calorie_quick_calculator2);
        tvGainWeightMild = findViewById(R.id.tv_gain_weight_mild_quick_calculator2);
        tvGainWeightModerate = findViewById(R.id.tv_gain_weight_moderate_quick_calculator2);
        tvLoseWeightMild = findViewById(R.id.tv_lose_weight_mild_quick_calculator2);
        tvLoseWeightModerate = findViewById(R.id.tv_lose_weight_moderate_quick_calculator2);
        tvModerateProteinGoal = findViewById(R.id.tv_proteinGoal_moderate_quick_calculator2);
        tvModerateFatGoal = findViewById(R.id.tv_fatGoal_moderate_quick_calculator2);
        tvModerateCarbGoal = findViewById(R.id.tv_carbGoal_moderate_quick_calculator2);
        tvLowerProteinGoal = findViewById(R.id.tv_proteinGoal_lower_quick_calculator2);
        tvLowerFatGoal = findViewById(R.id.tv_fatGoal_lower_quick_calculator2);
        tvLowerCarbGoal = findViewById(R.id.tv_carbGoal_lower_quick_calculator2);
        tvHighProteinGoal = findViewById(R.id.tv_proteinGoal_high_quick_calculator2);
        tvHighFatGoal = findViewById(R.id.tv_fatGoal_high_quick_calculator2);
        tvHighCarbGoal = findViewById(R.id.tv_carbGoal_high_quick_calculator2);
        tvCalorieTarget = findViewById(R.id.tv_calorie_target_value_quick_calculator2);
        toolbarQuickCalculator2 =findViewById(R.id.tb_quick_calculator2);
        cvMacro = findViewById(R.id.cv_macro_quick_calculator2);
        svQuickCalculator2 = findViewById(R.id.sv_quick_calculator2);

        setSupportActionBar(toolbarQuickCalculator2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarQuickCalculator2.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarQuickCalculator2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        gender = bundle.getInt("Gender");
        age = bundle.getInt("Age");
        height = bundle.getDouble("Height");
        weight = bundle.getDouble("Weight");
        activity = bundle.getInt("Activity");

        BodyStatistics bodyStatistics = new BodyStatistics(QuickCalculator2Activity.this, age, weight, height, gender, activity);
        DecimalFormat onedp = new DecimalFormat("0.0");
        BMIValue = bodyStatistics.getBMI();
        BMIStatus = bodyStatistics.getBMIStatus();
        setBMIStatusTextColor();
        BMIAdvice = bodyStatistics.getBMIAdvice();
        idealWeight = bodyStatistics.getIdealWeight();

        calorieMaintenance = bodyStatistics.getCalorieMaintenance();
        calorieSurplusModerate = bodyStatistics.getCalorieSurplusModerate();
        calorieSurplusMild = bodyStatistics.getCalorieSurplusMild();
        calorieDeficitModerate = bodyStatistics.getCalorieDeficitModerate();
        calorieDeficitMild = bodyStatistics.getCalorieDeficitMild();

        tvBMIValue.setText(onedp.format(BMIValue));
        tvBMIStatus.setText(BMIStatus);
        tvBMIAdvice.setText(BMIAdvice);
        tvIdealWeight.setText(String.valueOf(idealWeight));

        //Certain BMI is too low (Underweight). So, losing weight can negatively affect their health and vice versa for high BMI (Obese).
        //displayListOfCalorieTargets() can display or hide calorieTarget based on certain criteria.
        displayListOfCalorieTargets();

        calorieTarget = 0;
        tvCalorieTarget.setVisibility(View.GONE);
        cvMacro.setVisibility(View.GONE);

        tvLoseWeightMild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieTarget = calorieDeficitMild;
                if (BMIValue <= 18.5 ){
                    cvMacro.setVisibility(View.GONE);
                    tvCalorieTarget.setVisibility(View.GONE);
                    Toast.makeText(QuickCalculator2Activity.this, "Losing weight is not recommended for underweight", Toast.LENGTH_SHORT).show();
                }
                else {
                    displayMacroTarget(calorieTarget);
                }
            }
        });
        tvLoseWeightModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieTarget = calorieDeficitModerate;
                if (BMIValue <= 18.5 ){
                    cvMacro.setVisibility(View.GONE);
                    tvCalorieTarget.setVisibility(View.GONE);
                    Toast.makeText(QuickCalculator2Activity.this, "Losing weight is not recommended for underweight", Toast.LENGTH_SHORT).show();
                }
                else {
                    displayMacroTarget(calorieTarget);
                }
            }
        });
        tvGainWeightMild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieTarget = calorieSurplusMild;
                if (BMIValue >= 30 ){
                    //displayMacroTarget(0);
                    cvMacro.setVisibility(View.GONE);
                    tvCalorieTarget.setVisibility(View.GONE);
                    Toast.makeText(QuickCalculator2Activity.this, "Gaining weight is not recommended for obese", Toast.LENGTH_SHORT).show();
                }
                else {
                    displayMacroTarget(calorieTarget);
                }
            }
        });
        tvGainWeightModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieTarget = calorieSurplusModerate;
                if (BMIValue >= 30 ){
                    cvMacro.setVisibility(View.GONE);
                    tvCalorieTarget.setVisibility(View.GONE);
                    Toast.makeText(QuickCalculator2Activity.this, "Gaining weight is not recommended for obese", Toast.LENGTH_SHORT).show();
                }
                else {
                    displayMacroTarget(calorieTarget);
                }
            }
        });
    }

    private void displayMacroTarget(int calorieTarget) {
        BodyStatistics bodyStatistics = new BodyStatistics();
        int [] array = bodyStatistics.getArrayMacroTarget(calorieTarget);

        tvCalorieTarget.setText("Macronutrient plan for " + calorieTarget + " Cal");

        tvModerateProteinGoal.setText(String.valueOf(array[0])+"g");
        tvModerateFatGoal.setText(String.valueOf(array[1])+"g");
        tvModerateCarbGoal.setText(String.valueOf(array[2])+"g");

        tvLowerProteinGoal.setText(String.valueOf(array[3])+"g");
        tvLowerFatGoal.setText(String.valueOf(array[4])+"g");
        tvLowerCarbGoal.setText(String.valueOf(array[5])+"g");

        tvHighProteinGoal.setText(String.valueOf(array[6])+"g");
        tvHighFatGoal.setText(String.valueOf(array[7])+"g");
        tvHighCarbGoal.setText(String.valueOf(array[8])+"g");

        tvCalorieTarget.setVisibility(View.VISIBLE);
        cvMacro.setVisibility(View.VISIBLE);
        scrollToBottom();
    }
    private void displayListOfCalorieTargets() {
        tvCalorieMaintenance.setText(String.valueOf(calorieMaintenance)+ " Cal");
        if (BMIValue <= 18.5){ //Underweight
            //tvBMIStatus.setTextColor(Color.parseColor("#FF3131")); //red
            tvLoseWeightMild.setText("0.25 kg/week\n(" + "-- Cal)");
            tvLoseWeightModerate.setText("0.50 kg/week\n(" + "-- Cal)");
            tvGainWeightMild.setText("0.25 kg/week\n(" + String.valueOf(calorieSurplusMild)+" Cal)");
            tvGainWeightModerate.setText("0.50 kg/week\n(" + String.valueOf(calorieSurplusModerate)+" Cal)");
        }
        else if ((BMIValue > 18.5 && BMIValue <=24.99) || (BMIValue >= 25 && BMIValue <=29.99)){ //Normal & Overweight
            //tvBMIStatus.setTextColor(Color.parseColor("#F1B602")); //yellow
            tvLoseWeightMild.setText("0.25 kg/week\n(" + String.valueOf(calorieDeficitMild)+" Cal)");
            tvLoseWeightModerate.setText("0.50 kg/week\n(" + String.valueOf(calorieDeficitModerate)+" Cal)");
            tvGainWeightMild.setText("0.25 kg/week\n(" + String.valueOf(calorieSurplusMild)+" Cal)");
            tvGainWeightModerate.setText("0.50 kg/week\n(" + String.valueOf(calorieSurplusModerate)+" Cal)");
        }
        else if (BMIValue >= 30){ //Obese
            //tvBMIStatus.setTextColor(Color.parseColor("#FF3131")); //red
            tvLoseWeightMild.setText("0.25 kg/week\n(" + String.valueOf(calorieDeficitMild)+" Cal)");
            tvLoseWeightModerate.setText("0.50 kg/week\n(" + String.valueOf(calorieDeficitModerate)+" Cal)");
            tvGainWeightMild.setText("0.25 kg/week\n(" + "-- Cal)");
            tvGainWeightModerate.setText("0.50 kg/week\n(" + "-- Cal)");
        }
    }
    private void setBMIStatusTextColor(){
        if (BMIValue <= 18.5 || BMIValue >= 30) { //Underweight & Obese
            tvBMIStatus.setTextColor(Color.parseColor("#FF3131"));
        }
        else if (BMIValue <=29.9 && BMIValue >= 25) { //Overweight
            tvBMIStatus.setTextColor(Color.parseColor("#F1B602"));
        }
        else { //Normal
            tvBMIStatus.setTextColor(Color.parseColor("#00BF63"));
        }
    }
    private void scrollToBottom() {
        svQuickCalculator2.post(new Runnable() {
            @Override
            public void run() {
                svQuickCalculator2.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}