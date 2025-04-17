package com.example.caloriecraft.Objects;

import java.util.List;

public class SearchResponse {
    private List<Branded> branded;
    private List<Common> common;

    public List<Common> getCommon() {
        return common;
    }

    public void setCommon(List<Common> common) {
        this.common = common;
    }

    public List<Branded> getBranded() {
        return branded;
    }

    public void setBranded(List<Branded> branded) {
        this.branded = branded;
    }

    // Getter and setters
    public static class Branded {
        private String food_name;
        private String brand_name;
        private String nix_brand_id;
        private String serving_unit;
        private double nf_calories, nf_total_fat, nf_total_carbohydrate, nf_protein, serving_qty;

        public double getCalories() {
            return nf_calories;
        }

        public double getFats() {
            return nf_total_fat;
        }

        public double getCarbohydrates() {
            return nf_total_carbohydrate;
        }

        public double getProteins() {
            return nf_protein;
        }

        public double getServingQty() {
            return serving_qty;
        }

        public String getServingUnit() {
            return serving_unit;
        }


        public String getFood_name() {
            return food_name;
        }

    }
    public static class Common {
        private String food_name;
        private String tag_name;

        public String getFood_name() {
            return food_name;
        }

        public void setFood_name(String food_name) {
            this.food_name = food_name;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }
    }
}




