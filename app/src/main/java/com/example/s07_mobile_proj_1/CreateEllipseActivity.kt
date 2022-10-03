package com.example.s07_mobile_proj_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreateEllipseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ellips)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Create Ellipse"
    }
}