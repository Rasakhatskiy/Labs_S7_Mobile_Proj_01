package com.example.s07_mobile_proj_1

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.androidplot.xy.*
import com.example.s07_mobile_proj_1.databinding.ActivityShowPlotBinding
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.*

class ShowPlotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPlotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_plot)

        binding = ActivityShowPlotBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Wow! Nice Ellipse 😎"

        var xs = arrayOf<Double>()
        var ys = arrayOf<Double>()
        var ys2 = arrayOf<Double>()
        val step = 0.001

        var i = -Globals.a
        while (i <= Globals.a) {
            xs += i
            ys += Globals.SolveYforEllipce(i)
            ys2 += -Globals.SolveYforEllipce(i)
            i += step
        }



//        val domainLabels = arrayOf<Number> (1,2,3,6, 7, 8, 9, 10,13,14);
//        val series1Number = arrayOf<Number>(1,4,8,12,16,32,26,29,10,13);
//        val series2Number = arrayOf<Number>(2,8,4,7, 32,16,64,12,7, 10);

//        val series1 : XYSeries = SimpleXYSeries(Arrays.asList(* ys),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY
//            ,"Ellipse");
//        val series2 : XYSeries = SimpleXYSeries(Arrays.asList(* ys2),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY
//            ,"Series 1");
//
//        val series1Format = LineAndPointFormatter(Color.WHITE,Color.TRANSPARENT,null,null)
//        val series2Format = LineAndPointFormatter(Color.WHITE,Color.TRANSPARENT,null,null)
//
////        series1Format.interpolationParams = CatmullRomInterpolator.Params(10,
////            CatmullRomInterpolator.Type.Centripetal)
////        series2Format.interpolationParams = CatmullRomInterpolator.Params(10,
////            CatmullRomInterpolator.Type.Centripetal)
//
//        binding.plot.addSeries(series1,series1Format)
//        binding.plot.addSeries(series2,series2Format)
//
////        binding.plot.setDomainBoundaries(null, BoundaryMode.SHRINK, null, BoundaryMode.SHRINK)
//
//
//
//        binding.plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
//            override fun format(
//                obj: Any?,
//                toAppendTo: StringBuffer,
//                pos: FieldPosition
//            ): StringBuffer {
//                val i = Math.round((obj as Number).toFloat())
//                return toAppendTo.append(xs[i])
//            }
//
//            override fun parseObject(source: String?, pos: ParsePosition): Any? {
//                return null
//            }
//        }
////        binding.plot.setDomainBoundaries(null, BoundaryMode.SHRINK, null, BoundaryMode.SHRINK)
////        binding.plot.outerLimits.set(-50, 50, -50, 50)
//
////        PanZoom.attach(binding.plot)
//        binding.plot.setDomainStep(StepMode.INCREMENT_BY_PIXELS, 100.0);
//        binding.plot.setRangeStep(StepMode.INCREMENT_BY_PIXELS, 70.0);
//
//        binding.plot.on
//
//        PanZoom.attach(binding.plot, PanZoom.Pan.NONE, PanZoom.Zoom.STRETCH_VERTICAL);

    }
}