package com.example.s07_mobile_proj_1

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.s07_mobile_proj_1.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var isFabOpen = true

    private val adapter = SavedFigureAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.createFab.setOnClickListener{ manageMenu() }

        binding.ellipseFab.setOnClickListener {
            Globals.type = ConicSectionType.Ellipse
            val intent = Intent(this, CreateEllipseActivity::class.java)
            startActivity(intent)
        }

        binding.parabolaFab.setOnClickListener {
            Globals.type = ConicSectionType.Parabola
            val intent = Intent(this, CreateEllipseActivity::class.java)
            startActivity(intent)
        }

        binding.hyperbolaFab.setOnClickListener {
            Globals.type = ConicSectionType.Hyperbola
            val intent = Intent(this, CreateEllipseActivity::class.java)
            startActivity(intent)
        }

        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            rcView.adapter = adapter
        }

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    val position = viewHolder.adapterPosition
                    deleteFile(adapter.savedFigureList[position].name)
                    adapter.savedFigureList.removeAt(position)
                    binding.rcView.adapter?.notifyItemRemoved(position)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcView)

        fillAdapter()
        manageMenu()
    }

    private fun fillAdapter() {
        var ignoreFirst = true
        File(filesDir.absolutePath).walk().forEach {
            // first name is name of the dir
            if (ignoreFirst) {
                ignoreFirst = false
            } else {
                adapter.addSavedFigure(SavedFigure(it.name, ConicSectionType.None, 0.0f, 0.0f))
            }
        }
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