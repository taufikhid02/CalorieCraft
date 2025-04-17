package com.example.caloriecraft;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriecraft.Objects.BodyStatistics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalorieTargetActivity extends AppCompatActivity {
    RadioButton rbGainWeight1, rbGainWeight2, rbLoseWeight1, rbLoseWeight2;
    RadioGroup rgSetTarget;
    Button btnNext;
    TextView tvMaintenanceCalorie;
    FloatingActionButton fabBack;
    double bmi, height, weight;
    int age, gender, activity, calorieTarget, weightGoal;

    int calorieMaintenance, calorieSurplus1, calorieSurplus2, calorieDeficit1, calorieDeficit2;
    String sex, physicalLevel;

    //DatabaseReference databaseReference, databaseUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_target);

        //Bundle from CompleteProfileActivity
        Bundle bundle = getIntent().getExtras();
        height = bundle.getDouble("Height");
        age = bundle.getInt("Age");
        weight = bundle.getDouble("Weight");
        gender = bundle.getInt("Gender");
        activity = bundle.getInt("Activity");
        weightGoal = bundle.getInt("Weight Goal");

        sex = sexClassifier(gender);
        physicalLevel = activityClassifier(activity);

        tvMaintenanceCalorie = findViewById(R.id.tv_maintenance_calorie);
        rbGainWeight1 = (RadioButton)findViewById(R.id.rb_gain_weight_1);
        rbGainWeight2 = (RadioButton)findViewById(R.id.rb_gain_weight_2);
        rbLoseWeight1 = (RadioButton)findViewById(R.id.rb_lose_weight_1);
        rbLoseWeight2 = (RadioButton)findViewById(R.id.rb_lose_weight_2);
        rgSetTarget = findViewById(R.id.rg_set_calorie_target);
        btnNext = findViewById(R.id.btn_next_calorie_target);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("CalorieTargetActivity", "Confirm button clicked");
                if (rgSetTarget.getCheckedRadioButtonId()== -1){
                    Toast.makeText(CalorieTargetActivity.this, "Please select your daily calorie target", Toast.LENGTH_SHORT).show();
                }
                else {
                    int selectedId = rgSetTarget.getCheckedRadioButtonId();
                    if (selectedId == rbGainWeight1.getId()){
                        calorieTarget = calorieSurplus1;
                    }
                    else if (selectedId == rbGainWeight2.getId()){
                        calorieTarget = calorieSurplus2;
                    }
                    else if (selectedId == rbLoseWeight1.getId()){
                        calorieTarget = calorieDeficit2;
                    }
                    else if (selectedId == rbLoseWeight2.getId()){
                        calorieTarget = calorieDeficit1;
                    }
                    /*String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseUserList = databaseReference.child(userID);*/
                    //saveProfile();

                    Bundle bundleCalorieTarget = new Bundle();
                    bundleCalorieTarget.putDouble("Weight", weight);
                    bundleCalorieTarget.putInt("Age", age);
                    bundleCalorieTarget.putDouble("Height", height);
                    bundleCalorieTarget.putInt("Activity", activity);
                    bundleCalorieTarget.putInt("Gender", gender);
                    bundleCalorieTarget.putInt("Weight Goal", weightGoal);
                    bundleCalorieTarget.putInt("Calorie Target", calorieTarget);
                    bundleCalorieTarget.putDouble("BMI", bmi);

                    Intent goToSetMacro = new Intent(CalorieTargetActivity.this, MacroTargetActivity.class);
                    goToSetMacro.putExtras(bundleCalorieTarget);
                    startActivity(goToSetMacro);
                }
            }
        });
        fabBack = findViewById(R.id.fab_back_set_target);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        BodyStatistics bodyStats = new BodyStatistics(CalorieTargetActivity.this, age, weight, height, gender, activity);
        bmi = parseDouble(String.format("%.1f", bodyStats.getBMI()));
        calorieMaintenance = bodyStats.getCalorieMaintenance();
        calorieSurplus1 = bodyStats.getCalorieSurplusModerate();
        calorieSurplus2 = bodyStats.getCalorieSurplusMild();
        calorieDeficit1 = bodyStats.getCalorieDeficitModerate();
        calorieDeficit2 = bodyStats.getCalorieDeficitMild();

        tvMaintenanceCalorie.setText(String.valueOf(calorieMaintenance));
        rbGainWeight1.setText(String.valueOf(calorieSurplus1) + "Cal (GAIN 0.50kg/week)");
        rbGainWeight2.setText(String.valueOf(calorieSurplus2) + "Cal (GAIN 0.25kg/week)");
        rbLoseWeight1.setText(String.valueOf(calorieDeficit2) + "Cal (LOSE 0.25kg/week)");
        rbLoseWeight2.setText(String.valueOf(calorieDeficit1) + "Cal (LOSE 0.50kg/week)");
        double weightDifference = weightGoal - weight;
        if (bmi <= 18.5 || weightDifference > 0){
            rbLoseWeight1.setVisibility(View.GONE);
            rbLoseWeight2.setVisibility(View.GONE);
        }
        else if (weightDifference < 0){
            rbGainWeight1.setVisibility(View.GONE);
            rbGainWeight2.setVisibility(View.GONE);
        }
    }

    private String activityClassifier(int activity) {
        String physicalActivity = null;
        if (activity == 0){
            physicalActivity = "sedentary";
        }
        if (activity == 1){
            physicalActivity = "light exercise";
        }
        if (activity == 2){
            physicalActivity = "moderate exercise";
        }
        if (activity == 3){
            physicalActivity = "heavy exercise";
        }
        if (activity == 4){
            physicalActivity = "athlete";
        }
        return physicalActivity;
    }

    private String sexClassifier(int gender) {
        String sex = null;
        if (gender == 0){
            sex = "male";
        }
        if (gender == 1){
            sex = "female";
        }
        return sex;
    }

    /*private void saveProfile() {
        UserProfile newUserProfile = new UserProfile(sex, age, height, weight, physicalLevel, calorieTarget, bmi);
        databaseUserList.child("Profile").setValue(newUserProfile);
        Intent goToDashboard = new Intent(SetTargetsActivity.this, MainPage2Activity.class);
        startActivity(goToDashboard);
    }*/
}