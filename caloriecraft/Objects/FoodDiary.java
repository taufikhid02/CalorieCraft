package com.example.caloriecraft.Objects;

public class FoodDiary {
    String foodTitle, servingUnit;
    double foodCalorie, foodProtein, foodFat, foodCarb, servingNumber, servingSize;

    public FoodDiary(){

    }
    public FoodDiary(String foodTitle, double foodCalorie, double foodProtein, double foodFat, double foodCarb, double servingNumber, double servingSize, String servingUnit) {
        this.foodTitle = foodTitle;
        this.foodCalorie = foodCalorie;
        this.foodProtein = foodProtein;
        this.foodFat = foodFat;
        this.foodCarb = foodCarb;
        this.servingNumber = servingNumber;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
    }
    public String getFoodTitle() {
        return foodTitle;
    }

    public double getFoodCalorie() {
        return foodCalorie;
    }

    public double getFoodProtein() {
        return foodProtein;
    }

    public double getFoodFat() {
        return foodFat;
    }

    public double getFoodCarb() {
        return foodCarb;
    }
    public double getServingNumber() {
        return servingNumber;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public double getServingSize() {
        return servingSize;
    }
}
