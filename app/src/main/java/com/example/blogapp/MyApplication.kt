package com.example.blogapp



import android.app.Application
import com.google.firebase.FirebaseApp


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase or any other global setup here
        FirebaseApp.initializeApp(this)
    }
}