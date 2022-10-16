package com.example.s07_mobile_proj_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.s07_mobile_proj_1.databinding.ActivityCreateEllipsBinding

class CreateEllipseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEllipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ellips)

        binding = ActivityCreateEllipsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Create Ellipse"

        binding.ellipseInputA.filters = Array(1) { InputStuff.getRealNumberFilter() }
        binding.ellipseInputB.filters = Array(1) { InputStuff.getRealNumberFilter() }

        binding.buttonShowPlot.setOnClickListener {
            val intent = Intent(this, ShowPlotActivity::class.java)
            startActivity(intent)
        }


    }


}