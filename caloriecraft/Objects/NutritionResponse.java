package com.example.caloriecraft.Objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NutritionResponse {
    @SerializedName("foods")
    private List<Food> foods;
    public List<Food> getFoods() {
        return foods;
    }

    public static class Food {
        @SerializedName("food_name")
        private String foodName;

        @SerializedName("brand_name")
        private String brandName;

        @SerializedName("nf_calories")
        private double calories;

        @SerializedName("nf_total_carbohydrate")
        private double carbohydrates;

        @SerializedName("nf_protein")
        private double proteins;

        @SerializedName("nf_total_fat")
        private double fats;

        @SerializedName("serving_unit")
        private String servingUnit;

        @SerializedName("serving_qty")
        private String servingQty;


        // Getters
        public String getFoodName() {
            return foodName;
        }
        public String getBrandName() {
            return brandName;
        }
        public double getCalories() {
            return calories;
        }
        public double getCarbohydrates() {
            return carbohydrates;
        }
        public double getProteins() {
            return proteins;
        }
        public double getFats() {
            return fats;
        }
        public String getServingUnit() {
            return servingUnit;
        }
        public String getServingQty() {
            return servingQty;
        }
    }
}
