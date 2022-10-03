package com.example.s07_mobile_proj_1

import android.content.res.ColorStateList
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.s07_mobile_proj_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var isFabOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.createFab.setOnClickListener{ manageMenu() }
        manageMenu()
    }

    private fun manageMenu() {
        if(isFabOpen){
            isFabOpen = false
            binding.createFab.setImageResource(R.drawable.ic_create_new)
            binding.createFab.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding.createFab.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))

            binding.layoutMenu.setBackgroundResource(0)
            binding.ellipseFab.hide()
            binding.hyperbolaFab.hide()
            binding.parabolaFab.hide()
        } else {
            isFabOpen = true
            binding.createFab.setImageResource(R.drawable.ic_close)
            binding.createFab.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
            binding.createFab.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))

            binding.layoutMenu.setBackgroundResource(R.drawable.bg_top_corner)
            binding.ellipseFab.show()
            binding.hyperbolaFab.show()
            binding.parabolaFab.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}