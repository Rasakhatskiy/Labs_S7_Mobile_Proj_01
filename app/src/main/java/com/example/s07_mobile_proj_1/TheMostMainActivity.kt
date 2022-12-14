package com.example.s07_mobile_proj_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.s07_mobile_proj_1.addressbook.AdressBookActivity
import com.example.s07_mobile_proj_1.databinding.ActivityTheMostMainActvityBinding
import com.example.s07_mobile_proj_1.db.DBAuthActivity

class TheMostMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTheMostMainActvityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_most_main_actvity)
        binding = ActivityTheMostMainActvityBinding.inflate(layoutInflater)


        findViewById<Button>(R.id.button_start_poject_1).setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_start_poject_2).setOnClickListener {
            startActivity(Intent(this, Project2Activity::class.java))
        }
    }
}