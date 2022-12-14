package com.example.s07_mobile_proj_1.db;

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evrencoskun.tableview.TableView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.s07_mobile_proj_1.R

class DBActivity : AppCompatActivity() {
    private val adapter: MyTableViewAdapter = MyTableViewAdapter()
    private val chs = arrayListOf<ColumnHeader>()
    private val rhs = arrayListOf<RowHeader>()
    private val cellList = arrayListOf<ArrayList<Cell>>()
    private val dbHelper = DBHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val brandList = dbHelper.findAllBrands()

        chs.add(ColumnHeader("Name"))
        chs.add(ColumnHeader("Place (top all)"))
        chs.add(ColumnHeader("Value (\$M)"))
        chs.add(ColumnHeader("Difference from last year +%"))

        for (brand in brandList) {
            rhs.add(RowHeader(brand.id))
            cellList.add(arrayListOf(Cell(brand.name), Cell(brand.place), Cell(brand.value), Cell(brand.diff)))
        }

        val tv = this.findViewById<TableView>(R.id.content_container)

        tv.setAdapter(adapter)
        adapter.setAllItems(chs.toList(), rhs.toList(), cellList.toList())

        findViewById<Button>(R.id.gt5only).setOnClickListener {
            rhs.clear()
            cellList.clear()
            for (student in dbHelper.findAllBrands()
                .filter { it.diff > 5 }) {
                rhs.add(RowHeader(student.id))
                cellList.add(
                    arrayListOf(
                        Cell(student.name),
                        Cell(student.place),
                        Cell(student.value),
                        Cell(student.diff)
                    )
                )
            }

            adapter.setAllItems(chs.toList(), rhs.toList(), cellList.toList())
            tv.invalidate()
            tv.refreshDrawableState()
        }

        findViewById<Button>(R.id.gt20B).setOnClickListener {
            val allStudents = dbHelper.findAllBrands()
            val filteredBrands = allStudents.filter { it.value > 20000 }

            MaterialAlertDialogBuilder(this)
                .setTitle("Calculation")
                .setMessage("There are ${filteredBrands.size} brands valued more than $20B")
                .setNeutralButton("Ok", null)
                .show()
        }

        findViewById<Button>(R.id.add_brand_button).setOnClickListener {
            var name = ""
            var place = 0
            var value = 0
            var diff = 0

            val nameEdit = EditText(this)
            MaterialAlertDialogBuilder(this)
                .setTitle("Input Name")
                .setMessage("Input brand name")
                .setView(nameEdit)
                .setNeutralButton("Next") { _, _ ->
                    try {
                        name = nameEdit.text.toString()
                    } catch (ex: Exception) {
                        Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
                        return@setNeutralButton
                    }


                    val placeEdit = EditText(this)
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Input place")
                        .setMessage("Input brand place (top all)")
                        .setView(placeEdit)
                        .setNeutralButton("Next") { _, _ ->

                            try {
                                place = placeEdit.text.toString().toInt()
                            } catch (ex: Exception) {
                                Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
                                return@setNeutralButton
                            }

                            val valueEdit = EditText(this)
                            MaterialAlertDialogBuilder(this)
                                .setTitle("Input value")
                                .setMessage("Input brand value (millions \$)")
                                .setView(valueEdit)
                                .setNeutralButton("Next") { _, _ ->
                                    try {
                                        value = valueEdit.text.toString().toInt()
                                    } catch (ex: Exception) {
                                        Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
                                        return@setNeutralButton
                                    }

                                    val diffEdit = EditText(this)
                                    MaterialAlertDialogBuilder(this)
                                        .setTitle("Input rating difference")
                                        .setMessage("Input rating difference +-%")
                                        .setView(diffEdit)
                                        .setNeutralButton("Next") { _, _ ->
                                            try {
                                                diff = diffEdit.text.toString().toInt()
                                            } catch (ex: Exception) {
                                                Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
                                                return@setNeutralButton
                                            }

                                            dbHelper.addStudent(Brand(name = name, place = place, value = value, diff = diff))
                                            val brands = dbHelper.findAllBrands()

                                            rhs.clear()
                                            cellList.clear()

                                            for (brand in brands) {
                                                rhs.add(RowHeader(brand.id))
                                                cellList.add(
                                                    arrayListOf(
                                                        Cell(brand.name),
                                                        Cell(brand.place),
                                                        Cell(brand.value),
                                                        Cell(brand.diff)
                                                    )
                                                )
                                            }

                                            adapter.setAllItems(chs.toList(), rhs.toList(), cellList.toList())
                                            tv.invalidate()
                                            tv.refreshDrawableState()
                                        }
                                        .show()
                                }
                                .show()
                        }
                        .show()
                }
                .show()
        }
    }
}