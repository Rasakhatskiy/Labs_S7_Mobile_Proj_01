package com.example.s07_mobile_proj_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.s07_mobile_proj_1.addressbook.AdressBookActivity
import com.example.s07_mobile_proj_1.db.DBAuthActivity

class Project2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        findViewById<Button>(R.id.button_DB).setOnClickListener {
            startActivity(Intent(this, DBAuthActivity::class.java))
        }
        findViewById<Button>(R.id.button_Contacts).setOnClickListener {
            startActivity(Intent(this, AdressBookActivity::class.java))
        }
    }
}