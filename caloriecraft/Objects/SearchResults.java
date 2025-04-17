package com.example.caloriecraft.Objects;

public class SearchResults {
    String foodItemTitle;
    //String foodItemCalorie;
    String foodItemTagName;

    public SearchResults(String foodItemTitle, String foodItemTagName) {
        this.foodItemTitle = foodItemTitle;
        this.foodItemTagName = foodItemTagName;
    }

    public String getFoodItemTitle() {
        return foodItemTitle;
    }

    /*public void setFoodItemTitle(String foodItemTitle) {
        this.foodItemTitle = foodItemTitle;
    }*/

    public String getFoodItemTagName() {
        return foodItemTagName;
    }

    /*public void setFoodItemBrand(String foodItemBrand) {
        this.foodItemBrand = foodItemBrand;
    }*/
}
