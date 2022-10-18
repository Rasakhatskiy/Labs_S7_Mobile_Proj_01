package com.example.s07_mobile_proj_1

import kotlin.math.pow
import kotlin.math.sqrt

enum class ConicSectionType {
    None,
    Ellipse,
    Parabola,
    Hyperbola
}

class Globals {
    companion object {
        var a: Double = 0.0
        var b: Double = 0.0
        var type: ConicSectionType = ConicSectionType.None

        fun SolveYforEllipce(x: Double): Double {
            return sqrt(b.pow(2) - (b.pow(2) * x.pow(2) / a.pow(2)))
        }
    }
}