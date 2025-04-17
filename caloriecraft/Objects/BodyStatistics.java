package com.example.caloriecraft.Objects;

import android.content.Context;
import android.content.res.Resources;

import com.example.caloriecraft.R;

public class BodyStatistics {
    Context context;
    int age, gender, activity;
    double weight, height;

    public BodyStatistics(){

    }
    public BodyStatistics(Context context, int age, double weight, double height, int gender, int activity) {
        this.context = context;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.activity = activity;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }
    public double getBMI(){
        double BMI = weight/(Math.pow(height, 2));
        return BMI*10000;
    }
    public String getBMIStatus() {
        String status = null;
        if (this.getBMI() < 16){
            status = "Underweight\n(Severe Thinness)";
        }
        else if (this.getBMI() >=16 && this.getBMI() < 17){
            status = "Underweight\n(Moderate Thinness)";
        }
        else if (this.getBMI() >= 17 && this.getBMI() <= 18.5){
            status = "Underweight\n(Mild Thinness)";
        }
        else if (this.getBMI() > 18.5 && this.getBMI() <= 24.999){
            status = "Normal";
        }
        else if (this.getBMI() >= 25 && this.getBMI() <= 29.99){
            status = "Overweight";
        }
        if (this.getBMI() >= 30){
            status = "Obese";
        }
        return status;
    }
    public String getBMIAdvice(){
        String advice = null;
        if (this.getBMI() < 16){
            //advice = "Seek medical advice to rule out underlying health issues. Gradually increase calorie intake under supervision, focusing on nutrient-dense foods. Frequent, high-calorie meals and snacks, enriched with healthy fats and proteins, are crucial.";
            advice = context.getString(R.string.bmi_advice_underweight_severe_thinness);

        }
        else if (this.getBMI() >=16 && this.getBMI() < 17){
            //advice = "Prioritize meals with balanced macronutrients (proteins, fats, carbs) and aim for consistent, small calorie surpluses. Consider protein-rich shakes or smoothies as an addition to your diet.";
            advice = context.getString(R.string.bmi_advice_underweight_moderate_thinness);
        }
        else if (this.getBMI() >= 17 && this.getBMI() <= 18.5){
            //advice = "Focus on adding healthy, calorie-dense snacks between meals. Include nuts, seeds, and avocados to gently increase your calorie intake.";
            advice = context.getString(R.string.bmi_advice_underweight_mild_thinness);
        }
        else if (this.getBMI() > 18.5 && this.getBMI() <= 24.999){
            //advice = "Maintain a balanced diet and regular physical activity, listen to your body's needs.";
            advice = context.getString(R.string.bmi_advice_normal);
        }
        else if (this.getBMI() >= 25 && this.getBMI() <= 29.999){
            //advice = "Gradually adjust eating habits for healthier options, incorporate regular, enjoyable physical activity.";
            advice = context.getString(R.string.bmi_advice_overweight);

        }
        if (this.getBMI() >= 30){
            //advice = "Consult healthcare professionals for a personalized plan, focus on sustainable lifestyle changes.";
            advice = context.getString(R.string.bmi_advice_obese);

        }
        return advice;
    }
    public int getCalorieMaintenance(){
        double BMR = 0;
        int AMR = 0;
        //0 = male, 1 = female
        if (gender == 0){
            BMR = (10*weight) + (6.25*height) - (5*age)+5;
            AMR = calculateAMR(BMR);
        }
        else if (gender == 1){
            BMR = (10*weight) + (6.25*height) - (5*age)-161;
            AMR = calculateAMR(BMR);
        }
        return AMR;
    }
    public int getIdealWeight(){

        // 1 inch = 2.54 cm
        double cmPerInch = 2.54;
        // 1 foot = 12 inches
        double inchesPerFoot = 12;
        //convert cm to inches
        double heightInInches = height/cmPerInch;

        double baseHeightInFeet = 5;

        // calculate how many inches are left after 5 feet
        double inchesOver5Feet = heightInInches - (baseHeightInFeet * inchesPerFoot);

        // ensure no negative inches for people under 5 feet
        inchesOver5Feet = Math.max(0, inchesOver5Feet);

        // calculate ideal weight based on gender
        double idealWeight  = 0;
        if (gender == 0){
            idealWeight = 48 + (2.7*inchesOver5Feet);
        }
        else if (gender == 1){
            idealWeight = 45.5 + (2.2*inchesOver5Feet);
        }
        return (int) Math.round(idealWeight);
    }
    public int getIdealWeight(double height, int gender){

        // 1 inch = 2.54 cm
        double cmPerInch = 2.54;
        // 1 foot = 12 inches
        double inchesPerFoot = 12;
        //convert cm to inches
        double heightInInches = height/cmPerInch;

        double baseHeightInFeet = 5;

        // calculate how many inches are left after 5 feet
        double inchesOver5Feet = heightInInches - (baseHeightInFeet * inchesPerFoot);

        // ensure no negative inches for people under 5 feet
        inchesOver5Feet = Math.max(0, inchesOver5Feet);

        // calculate ideal weight based on gender
        double idealWeight  = 0;
        if (gender == 0){
            idealWeight = 48 + (2.7*inchesOver5Feet);
        }
        else if (gender == 1){
            idealWeight = 45.5 + (2.2*inchesOver5Feet);
        }
        return (int) Math.round(idealWeight);
    }
    public int getMinimumWeight(double BMI, double height){
        double minimumWeight = BMI*Math.pow(height, 2);
        return (int) minimumWeight/10000;
    }
    public int getMaximumWeight(double BMI, double height){
        double maximumWeight = BMI*Math.pow(height, 2);
        return (int) maximumWeight/10000;
    }
    public int getCalorieSurplusModerate(){
        int calorieSurplus = getCalorieMaintenance() + 500;
        return calorieSurplus;
    }
    public int getCalorieSurplusMild(){
        int calorieSurplus = getCalorieMaintenance() + 250;
        return calorieSurplus;
    }
    public int getCalorieDeficitModerate(){
        int calorieDeficit = getCalorieMaintenance() - 500;
        return calorieDeficit;
    }
    public int getCalorieDeficitMild(){
        int calorieDeficit = getCalorieMaintenance() - 250;
        return calorieDeficit;
    }
    private int calculateAMR(double bmr) {
        double AMR = 0;
        if (activity == 0){
            AMR = bmr*1.2;
        }
        if (activity == 1){
            AMR = bmr*1.375;
        }
        if (activity == 2){
            AMR = bmr*1.550;
        }
        if (activity == 3){
            AMR = bmr*1.725;
        }
        if (activity == 4){
            AMR = bmr*1.9;
        }
        return (int) AMR;
    }
    public int [] getArrayMacroTarget(int calorieTarget){
        int moderateProteinTarget = (int) ((double) calorieTarget * 0.3)/4;
        int moderateFatTarget = (int) ((double) calorieTarget * 0.35)/9;
        int moderateCarbTarget = (int) ((double) calorieTarget * 0.35)/4;
        int lowerProteinTarget = (int) ((double) calorieTarget * 0.4)/4;
        int lowerFatTarget = (int) ((double) calorieTarget * 0.4)/9;
        int lowerCarbTarget = (int) ((double) calorieTarget * 0.2)/4;
        int highProteinTarget = (int) ((double) calorieTarget * 0.3)/4;
        int highFatTarget = (int) ((double) calorieTarget * 0.2)/9;
        int highCarbTarget = (int) ((double) calorieTarget * 0.5)/4;

        int [] arrayMacroTarget = new int [] {moderateProteinTarget, moderateFatTarget, moderateCarbTarget, lowerProteinTarget, lowerFatTarget, lowerCarbTarget,  highProteinTarget, highFatTarget, highCarbTarget};
        return arrayMacroTarget;
    }
}
