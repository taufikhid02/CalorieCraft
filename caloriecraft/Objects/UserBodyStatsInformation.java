package com.example.caloriecraft.Objects;

public class UserBodyStatsInformation {
    private String sex, physicalLevel;
    private int age, calorieTarget, weightGoal, proteinGoal, fatGoal, carbGoal;
    double height, weight, bmi;


    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public UserBodyStatsInformation() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }

    public UserBodyStatsInformation(String sex, int age, double height, double weight, String physicalLevel, int calorieTarget, double bmi, int weightGoal, int proteinGoal, int fatGoal, int carbGoal) {
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.physicalLevel = physicalLevel;
        this.calorieTarget = calorieTarget;
        this.bmi = bmi;
        this.weightGoal = weightGoal;
        this.proteinGoal = proteinGoal;
        this.fatGoal = fatGoal;
        this.carbGoal = carbGoal;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhysicalLevel() {
        return physicalLevel;
    }

    public void setPhysicalLevel(String physicalLevel) {
        this.physicalLevel = physicalLevel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCalorieTarget() {
        return calorieTarget;
    }

    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    public int getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(int weightGoal) {
        this.weightGoal = weightGoal;
    }

    public int getProteinGoal() {
        return proteinGoal;
    }

    public void setProteinGoal(int proteinGoal) {
        this.proteinGoal = proteinGoal;
    }

    public int getFatGoal() {
        return fatGoal;
    }

    public void setFatGoal(int fatGoal) {
        this.fatGoal = fatGoal;
    }

    public int getCarbGoal() {
        return carbGoal;
    }

    public void setCarbGoal(int carbGoal) {
        this.carbGoal = carbGoal;
    }
}
