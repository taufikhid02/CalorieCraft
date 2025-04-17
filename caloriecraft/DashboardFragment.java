package com.example.caloriecraft;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.caloriecraft.Objects.Date;
import com.example.caloriecraft.Objects.UserBodyStatsInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class DashboardFragment extends Fragment {

    TextView tvBMIValue, tvWeight, tvWeightGoal, tvBMIStatus, tvCalorieGoal, tvProteinGoal, tvFatGoal, tvCarbGoal, tvProgressDate;
    DatabaseReference databaseReference, databaseNutritionProgress, databaseNutritionProgress1;
    Date date;
    ProgressBar pbCalorieGoal, pbProteinGoal, pbFatGoal, pbCarbGoal;
    String userID, todayDate, weightGoal;
    int calorieTarget, proteinGoal,fatGoal, carbGoal;
    double BMIValue, weight, weightDifference;
    DecimalFormat onedp = new DecimalFormat("0.0");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvProgressDate = view.findViewById(R.id.tv_nutritionProgressDate);
        tvBMIValue = view.findViewById(R.id.tv_bmi_value);
        tvBMIStatus = view.findViewById(R.id.tv_bmi_status);
        tvWeight = view.findViewById(R.id.tv_current_weight);
        tvWeightGoal = view.findViewById(R.id.tv_weight_goal);
        tvCalorieGoal = view.findViewById(R.id.tv_calorie_dashboard);
        tvProteinGoal = view.findViewById(R.id.tv_protein_dashboard);
        tvFatGoal = view.findViewById(R.id.tv_fat_dashboard);
        tvCarbGoal = view.findViewById(R.id.tv_carb_dashboard);

        pbCalorieGoal = view.findViewById(R.id.pb_calorie_dashboard);
        pbProteinGoal = view.findViewById(R.id.pb_protein_dashboard);
        pbFatGoal = view.findViewById(R.id.pb_fat_dashboard);
        pbCarbGoal = view.findViewById(R.id.pb_carb_dashboard);

        date = new Date();
        todayDate = date.getTodayDate();
        tvProgressDate.setText(todayDate);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    retrieveBodyStatsData(snapshot);
                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void retrieveBodyStatsData(DataSnapshot snapshot) {
        DataSnapshot bodyStatsSnapshot = snapshot.child("bodyStatistics");
        if (bodyStatsSnapshot.exists()){
            UserBodyStatsInformation userBodyStatsInformation = bodyStatsSnapshot.getValue(UserBodyStatsInformation.class);
            if (userBodyStatsInformation != null){
                BMIValue = userBodyStatsInformation.getBmi();
                weight = userBodyStatsInformation.getWeight();
                weightDifference = userBodyStatsInformation.getWeightGoal() - weight;
                weightGoal = getWeightGoalMessage(weightDifference);
                calorieTarget = userBodyStatsInformation.getCalorieTarget();
                proteinGoal = userBodyStatsInformation.getProteinGoal();
                fatGoal = userBodyStatsInformation.getFatGoal();
                carbGoal = userBodyStatsInformation.getCarbGoal();

                // set text view according to the calculated values
                tvBMIValue.setText(String.valueOf(BMIValue));
                tvBMIStatus.setText(getBMIStatus(BMIValue));
                setBMIStatusTextColor();
                tvWeight.setText(String.valueOf(weight));
                tvWeightGoal.setText(weightGoal);

                getNutritionProgress("calorieProgress");
                getNutritionProgress("proteinProgress");
                getNutritionProgress("fatProgress");
                getNutritionProgress("carbProgress");

            }
            else {

            }
        }
        else {

        }
    }

    private void getNutritionProgress(String nutritionProgress) {
        DecimalFormat df = new DecimalFormat("0.00");
        databaseNutritionProgress = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(todayDate).child(nutritionProgress);
        databaseNutritionProgress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (nutritionProgress.equals("calorieProgress")){
                        int calorieProgress = snapshot.getValue(Integer.class);
                        tvCalorieGoal.setText(calorieProgress + "\nof\n" + String.valueOf(calorieTarget) + "\nCal");
                        pbCalorieGoal.setProgress(calculatePercentage((double) calorieProgress, (double) calorieTarget));
                    }
                    else if (nutritionProgress.equals("proteinProgress")){
                        double proteinProgress = snapshot.getValue(Double.class);
                        tvProteinGoal.setText("Proteins: " + df.format(proteinProgress) + " of " + String.valueOf(proteinGoal) + " g");
                        pbProteinGoal.setProgress(calculatePercentage((double) proteinProgress, (double) proteinGoal));
                    }
                    else if (nutritionProgress.equals("fatProgress")){
                        double fatProgress = snapshot.getValue(Double.class);
                        tvFatGoal.setText("Fats: " + df.format(fatProgress) + " of " + String.valueOf(fatGoal) + " g");
                        pbFatGoal.setProgress(calculatePercentage((double) fatProgress, (double) fatGoal));
                    }
                    else if (nutritionProgress.equals("carbProgress")){
                        double carbProgress = snapshot.getValue(Double.class);
                        tvCarbGoal.setText("Carbs: " + df.format(carbProgress) + " of " + String.valueOf(carbGoal) + " g");
                        pbCarbGoal.setProgress(calculatePercentage((double) carbProgress, (double) carbGoal));
                    }
                }
                else {
                    tvCalorieGoal.setText(0 + "\nof\n" + String.valueOf(calorieTarget) + "\nCal");
                    pbCalorieGoal.setProgress(0);
                    tvProteinGoal.setText("Proteins: " + 0 + " of " + String.valueOf(proteinGoal) + " g");
                    pbProteinGoal.setProgress(0);
                    tvFatGoal.setText("Fats: " + 0 + " of " + String.valueOf(fatGoal) + " g");
                    pbFatGoal.setProgress(0);
                    tvCarbGoal.setText("Carbs: " + 0 + " of " + String.valueOf(carbGoal) + " g");
                    pbCarbGoal.setProgress(calculatePercentage((double) 0, (double) carbGoal));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private int calculatePercentage(double progress, double goal) {
        double percentage = (progress/goal)*100;

        return (int) percentage;
    }
    private String getWeightGoalMessage(double weightDifference) {
        String message = null;
        if (weightDifference < 0){
            message = "Target Loss\n" + onedp.format(Math.abs(weightDifference)) + " kg";
        }
        else if (weightDifference >= 0){
            message = "Target Gain\n" + onedp.format(weightDifference) + " kg";
        }
        return message;
    }
    private String getBMIStatus(double BMI) {
        String status = null;
        if (BMI < 16){
            status = "Underweight\n(Severe Thinness)";
        }
        else if (BMI >=16 && BMI < 17){
            status = "Underweight\n(Moderate Thinness)";
        }
        else if (BMI >= 17 && BMI <= 18.5){
            status = "Underweight\n(Mild Thinness)";
        }
        else if (BMI > 18.5 && BMI <= 24.9){
            status = "Normal";
        }
        else if (BMI >= 25 && BMI <= 29.9){
            status = "Overweight";
        }
        if (BMI >= 30){
            status = "Obese";
        }
        return status;
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
}

        //Don't uncomment this
        /*int calorieProgress = calculatePercentage(randomValue, calorieTarget);
        int proteinProgress = calculatePercentage(randomValue, proteinGoal);
        int fatProgress = calculatePercentage(randomValue, fatGoal);
        int carbProgress = calculatePercentage(randomValue, carbGoal);*/