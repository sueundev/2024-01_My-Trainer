package com.example.mytrainer;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private String name;
    private int calories;
    private int quantity;
    private String date;

    public FoodItem(String name, int calories, int quantity, String date) {
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}
