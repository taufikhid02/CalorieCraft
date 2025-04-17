package com.example.caloriecraft.Objects;

import android.widget.ImageView;

public class ServingUnit {
    int imgGuidePicture;
    String servingUnit;

    public ServingUnit(String servingUnit, int imgGuidePicture) {
        this.imgGuidePicture = imgGuidePicture;
        this.servingUnit = servingUnit;
    }

    public int getImgGuidePicture() {
        return imgGuidePicture;
    }

    public String getServingUnit() {
        return servingUnit;
    }
}
