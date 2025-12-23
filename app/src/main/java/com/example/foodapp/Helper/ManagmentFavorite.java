package com.example.foodapp.Helper;

import android.content.Context;

import com.example.foodapp.Domain.Foods;

import java.util.ArrayList;

public class ManagmentFavorite {

    private TinyDB tinyDB;

    public ManagmentFavorite(Context context) {
        tinyDB = new TinyDB(context);
    }

    public ArrayList<Foods> getFavoriteList() {
        return tinyDB.getListObject("FavoriteList");
    }

    public boolean isFavorite(Foods food) {
        for (Foods f : getFavoriteList()) {
            if (f.getTitle().equals(food.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public void addFavorite(Foods food) {
        ArrayList<Foods> list = getFavoriteList();
        if (!isFavorite(food)) {
            list.add(food);
            tinyDB.putListObject("FavoriteList", list);
        }
    }

    public void removeFavorite(Foods food) {
        ArrayList<Foods> list = getFavoriteList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().equals(food.getTitle())) {
                list.remove(i);
                break;
            }
        }
        tinyDB.putListObject("FavoriteList", list);
    }
}
