package com.example.s07_mobile_proj_1

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
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

        if (Globals.type == ConicSectionType.Ellipse) {
            binding.textViewArea.visibility = VISIBLE
            binding.textViewLength.visibility = VISIBLE
        } else {
            binding.textViewArea.visibility = GONE
            binding.textViewLength.visibility = GONE
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

        var xArc = 0f
        var yArc = 0f

        if (Globals.type == ConicSectionType.Ellipse) {
            Globals.a = abs(Globals.a)
            Globals.b = abs(Globals.b)

            val range = Globals.a * 2
            val step = abs(range) / 1000

            var x = -Globals.a
            while (x <= Globals.a) {
                val y = Globals.solveEllipseY(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
                x += step
            }

            val length = Globals.getEllipseArcLength()
            val area = Globals.getEllipseSectorArea()

            binding.textViewArea.text = "Sector area: $area"
            binding.textViewLength.text = "Arch length: $length"

            xArc = Globals.getEllipsePointByAngle()
            yArc = Globals.solveEllipseY(xArc)
        }

        if (Globals.type == ConicSectionType.Hyperbola) {
            Globals.a = abs(Globals.a)
            Globals.b = abs(Globals.b)

            val range = Globals.a * 2
            val step = abs(range) / 1000

            var x = -Globals.a - range
            while (x <= -Globals.a) {
                val y = Globals.solveHyperbolaY(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
                x += step
            }

            if (x != -Globals.a) {
                x = -Globals.a
                val y = Globals.solveHyperbolaY(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
            }

            x = Globals.a
            while (x <= Globals.a + range) {
                val y = Globals.solveHyperbolaY(x)
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
                val y = Globals.solveParabolaY(x)
                lineEntry1.add(Entry(x, y))
                lineEntry2.add(Entry(x, -y))
                x += step
            }

            if (x != dest) {
                x = dest
                val y = Globals.solveParabolaY(x)
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

        // for ellipce
        val lineEntryAngleLine1 = ArrayList<Entry>()
        lineEntryAngleLine1.add(Entry(0f, 0f))
        lineEntryAngleLine1.add(Entry(xArc, yArc))
        val lineDatasetAngleLine1 = LineDataSet(lineEntryAngleLine1, "")

        val lineEntryAngleLine2 = ArrayList<Entry>()
        lineEntryAngleLine2.add(Entry(0f, 0f))
        lineEntryAngleLine2.add(Entry(0f, Globals.b))
        val lineDatasetAngleLine2 = LineDataSet(lineEntryAngleLine2, "")

        var data = LineData(lineDataset1, lineDataset2)

        when (Globals.type) {
            ConicSectionType.Ellipse -> data =
                LineData(lineDataset1, lineDataset2, lineDatasetAngleLine1, lineDatasetAngleLine2)
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
            builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        } else {
            saveToInternal(filename)
            Toast.makeText(applicationContext, "saved", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveToInternal(filename: String): Boolean {
        return try {
            val savedFigure = SavedFigure(filename, Globals.type, Globals.a, Globals.b, Globals.d)
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
                        bufferedWriter.write(savedFigure.d.toString())
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