package com.example.s07_mobile_proj_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.*
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
        when(Globals.type) {
            ConicSectionType.Ellipse -> supportActionBar!!.title = "Create Ellipse"
            ConicSectionType.Parabola -> supportActionBar!!.title = "Create Parabola"
            ConicSectionType.Hyperbola -> supportActionBar!!.title = "Create Hyperbola"
            ConicSectionType.None -> TODO()
        }

        binding.ellipseInputA.filters = Array(1) { InputStuff.getRealNumberFilter() }
        binding.ellipseInputB.filters = Array(1) { InputStuff.getRealNumberFilter() }

        if (Globals.type == ConicSectionType.Parabola) {
            binding.ellipseInputBP.visibility = GONE
        } else {
            binding.ellipseInputBP.visibility = VISIBLE
        }


        binding.buttonShowPlot.setOnClickListener {
            var a = 0f
            var b = 0f
            try {
                a = binding.ellipseInputA.text.toString().toFloat()

                if (Globals.type != ConicSectionType.Parabola) {
                    b = binding.ellipseInputB.text.toString().toFloat()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (a == 0f) {
                Toast.makeText(applicationContext, "Division by 0!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (b == 0f && Globals.type != ConicSectionType.Parabola) {
                Toast.makeText(applicationContext, "Division by 0!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Globals.a = a
            Globals.b = b

            val intent = Intent(this, ShowPlotActivity::class.java)
            startActivity(intent)
        }


    }


}