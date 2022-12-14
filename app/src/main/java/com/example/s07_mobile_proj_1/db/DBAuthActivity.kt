package com.example.s07_mobile_proj_1.db;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.example.s07_mobile_proj_1.R

class DBAuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab6_auth)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.button8).setOnClickListener {
            val login = findViewById<TextInputEditText>(R.id.login_input).text.toString()
            val password = findViewById<TextInputEditText>(R.id.password_input).text.toString()

            val db = DBHelper(this)
            if (!db.authorize(User(name = login, password = password))) {
                Toast.makeText(this, "Incorrect login or password!", Toast.LENGTH_LONG).show()
            } else {
                startActivity(Intent(this, DBActivity::class.java))
            }
        }
    }
}