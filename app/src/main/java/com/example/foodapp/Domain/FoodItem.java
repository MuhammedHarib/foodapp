package com.example.foodapp.Domain;

public class FoodItem {

    private String Title;
    private String ImagePath;
    private String Price; // Can also be double if needed: private double Price;

    // 🔴 REQUIRED EMPTY CONSTRUCTOR FOR FIREBASE
    public FoodItem() {}

    // ✅ Getters
    public String getTitle() {
        return Title;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getPrice() {
        return Price;
    }

    // ✅ Setters (Firebase requires them)
    public void setTitle(String title) {
        this.Title = title;
    }

    public void setImagePath(String imagePath) {
        this.ImagePath = imagePath;
    }

    public void setPrice(String price) {
        this.Price = price;
    }
}
