package com.example.foodapp;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // This ensures Firebase is initialized before any activity starts
        FirebaseApp.initializeApp(this);
    }
}