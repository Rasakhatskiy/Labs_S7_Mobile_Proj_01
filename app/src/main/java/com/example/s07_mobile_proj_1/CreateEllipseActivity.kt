package com.example.s07_mobile_proj_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
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
            var a: Double = 0.0
            var b: Double = 0.0
            try {
                a = binding.ellipseInputA.text.toString().toDouble()
                b = binding.ellipseInputB.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext,"Invalid input!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (a == 0.0 || b == 0.0) {
                Toast.makeText(applicationContext,"Division by 0!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Globals.a = a
            Globals.b = b
            Globals.type = ConicSectionType.Ellipse

            val intent = Intent(this, ShowPlotActivity::class.java)
            startActivity(intent)
        }


    }


}