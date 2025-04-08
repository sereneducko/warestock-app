package com.example.stockapp;

import androidx.annotation.NonNull;

public class Category {

    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString () {
        return name;
    }
}
