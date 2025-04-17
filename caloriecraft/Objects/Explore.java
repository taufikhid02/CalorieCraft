package com.example.caloriecraft.Objects;

public class Explore {
    String title, description, goToPage;
    int imgExplore;

    public Explore(String title, String description, String goToPage, int imgExplore) {
        this.title = title;
        this.description = description;
        this.goToPage = goToPage;
        this.imgExplore = imgExplore;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGoToPage() {
        return goToPage;
    }

    public int getImgExplore() {
        return imgExplore;
    }
}
