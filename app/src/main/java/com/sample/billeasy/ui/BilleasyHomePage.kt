package com.sample.billeasy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.billeasy.R
import com.sample.billeasy.database.MovieDb

class BilleasyHomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MovieDb.getDatabase(this)
    }
}