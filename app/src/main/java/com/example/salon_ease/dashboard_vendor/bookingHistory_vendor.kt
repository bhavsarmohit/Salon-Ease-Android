package com.example.salon_ease.dashboard_vendor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.salon_ease.R

class bookingHistory_vendor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history_vendor)
        supportActionBar?.hide()
    }
}