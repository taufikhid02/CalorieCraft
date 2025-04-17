package com.example.caloriecraft.Objects;

public class MacroTarget {
    String sex, physicalLevel, macroPlan, cardViewColor;

    int age, weightGoal, calorieTarget, proteinGoal, fatGoal, carbGoal;
    double height, weight, bmi;

    public MacroTarget(String cardViewColor, String sex, int age, double height, double weight, String physicalLevel, int calorieTarget, double bmi, int weightGoal, String macroPlan, int proteinGoal, int fatGoal, int carbGoal) {
        this.cardViewColor = cardViewColor;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.physicalLevel = physicalLevel;
        this.calorieTarget = calorieTarget;
        this.bmi = bmi;
        this.weightGoal = weightGoal;
        this.macroPlan = macroPlan;
        this.proteinGoal = proteinGoal;
        this.fatGoal = fatGoal;
        this.carbGoal = carbGoal;
    }

    public String getCardViewColor() {
        return cardViewColor;
    }

    public String getSex() {
        return sex;
    }

    public String getPhysicalLevel() {
        return physicalLevel;
    }

    public int getAge() {
        return age;
    }

    public int getWeightGoal() {
        return weightGoal;
    }

    public int getCalorieTarget() {
        return calorieTarget;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBmi() {
        return bmi;
    }

    public String getMacroPlan() {
        return macroPlan;
    }

    public int getProteinGoal() {
        return proteinGoal;
    }

    public int getFatGoal() {
        return fatGoal;
    }

    public int getCarbGoal() {
        return carbGoal;
    }
}
