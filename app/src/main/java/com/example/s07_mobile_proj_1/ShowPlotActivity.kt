package com.example.s07_mobile_proj_1

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s07_mobile_proj_1.databinding.ActivityShowPlotBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ShowPlotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPlotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_plot)

        binding = ActivityShowPlotBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        when (Globals.type) {
            ConicSectionType.Ellipse -> supportActionBar!!.title = "Wow! Nice Ellipse 😎"
            ConicSectionType.Hyperbola -> supportActionBar!!.title = "Wow! Nice Hyperbola 😍"
            ConicSectionType.Parabola -> supportActionBar!!.title = "Wow! Nice Parabola 🙀"
        }

        binding.buttonSaveFigure.setOnClickListener {
            showdialog()
        }

        setLineChartData()
    }

    fun setLineChartData() {
        val lineEntry1 = ArrayList<Entry>()
        val lineEntry2 = ArrayList<Entry>()
        val lineEntry3 = ArrayList<Entry>()
        val lineEntry4 = ArrayList<Entry>()

        if (Globals.type == ConicSectionType.Ellipse) {
            Globals.a = abs(Globals.a)
            Globals.b = abs(Globals.b)

            val range = Globals.a * 2
            val step = abs(range) / 1000

            var x = -Globals.a
            while (x <= Globals.a) {
                val y = Globals.SolveYforEllipce(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
                x += step
            }
        }

        if (Globals.type == ConicSectionType.Hyperbola) {
            Globals.a = abs(Globals.a)
            Globals.b = abs(Globals.b)

            val range = Globals.a * 2
            val step = abs(range) / 1000

            var x = -Globals.a - range
            while (x <= -Globals.a) {
                val y = Globals.SolveYforHyperbola(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
                x += step
            }

            if (x != -Globals.a) {
                x = -Globals.a
                val y = Globals.SolveYforHyperbola(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
            }

            x = Globals.a
            while (x <= Globals.a + range) {
                val y = Globals.SolveYforHyperbola(x)
                lineEntry3.add(Entry(x, y))
                lineEntry4.add(Entry(x, -y))
                x += step
            }


        }

        if (Globals.type == ConicSectionType.Parabola) {
            val range = Globals.a * 2
            val step = abs(range) / 1000

            var x = min(range, 0f)
            var dest = max(range, 0f)
            while (x <= dest) {
                val y = Globals.SolveYforParabola(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
                x += step
            }

            if (x != dest) {
                x = dest
                val y = Globals.SolveYforParabola(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
            }
        }

        val lineDataset1 = LineDataSet(lineEntry1, "")
        lineDataset1.color = R.color.purple_500
        lineDataset1.setDrawCircles(false)

        val lineDataset2 = LineDataSet(lineEntry2, "")
        lineDataset2.color = R.color.purple_500
        lineDataset2.setDrawCircles(false)

        val lineDataset3 = LineDataSet(lineEntry3, "")
        lineDataset3.color = R.color.purple_500
        lineDataset3.setDrawCircles(false)

        val lineDataset4 = LineDataSet(lineEntry4, "")
        lineDataset4.color = R.color.purple_500
        lineDataset4.setDrawCircles(false)

//        val data = LineData(lineDataset1, lineDataset2)

        var data = LineData(lineDataset1, lineDataset2)

        when (Globals.type) {
            ConicSectionType.Ellipse -> data = LineData(lineDataset1, lineDataset2)
            ConicSectionType.Hyperbola -> data =
                LineData(lineDataset1, lineDataset2, lineDataset3, lineDataset4)
            ConicSectionType.Parabola -> data = LineData(lineDataset1, lineDataset2)
        }



        binding.lineChart.data = data
        binding.lineChart.setBackgroundColor(resources.getColor(R.color.white))
        binding.lineChart.setScaleMinima(1.5f, 1f)
        binding.lineChart.setScaleEnabled(true)
        binding.lineChart.legend.isEnabled = false
    }

    private fun showdialog() {
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Save figure as")

        val input = EditText(this)

        input.hint = "Enter Name"
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.filters = Array(1) { InputStuff.getNameFilter() }
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val text = input.text.toString()
            checkFileExistEvent(text)
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun fileExists(filename: String): Boolean {
        val dir = filesDir
        val file = File(dir, filename)
        return file.exists()
    }

    private fun checkFileExistEvent(filename: String) {
        if (fileExists(filename)) {
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Confirm overwrite")
            builder.setMessage("File already exists. Overwrite it?")
            builder.setPositiveButton("Yes") { _, _ ->
                val file = File(filename)
                file.delete()
                saveToInternal(filename)
                Toast.makeText(applicationContext, "saved", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { dialog, _ -> dialog.cancel()}
            val alert = builder.create()
            alert.show()
        } else {
            saveToInternal(filename)
            Toast.makeText(applicationContext, "saved", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveToInternal(filename: String): Boolean {
        return try {
            val savedFigure = SavedFigure(filename, Globals.type, Globals.a, Globals.b)
            openFileOutput(filename, MODE_PRIVATE).use { stream ->
                OutputStreamWriter(stream).use { outputStreamWriter ->
                    BufferedWriter(outputStreamWriter).use { bufferedWriter ->
                        bufferedWriter.write(savedFigure.name)
                        bufferedWriter.newLine()
                        bufferedWriter.write(savedFigure.type.toString())
                        bufferedWriter.newLine()
                        bufferedWriter.write(savedFigure.a.toString())
                        bufferedWriter.newLine()
                        bufferedWriter.write(savedFigure.b.toString())
                        bufferedWriter.newLine()
                        true
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }



}