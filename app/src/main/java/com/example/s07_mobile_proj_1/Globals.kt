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
        var a: Float = 0f
        var b: Float = 0f
        var type: ConicSectionType = ConicSectionType.None

        fun SolveYforEllipce(x: Float): Float {
            return sqrt(b.pow(2) - (b.pow(2) * x.pow(2) / a.pow(2)))
        }

        fun SolveYforHyperbola(x: Float): Float {
            return sqrt((b.pow(2) * x.pow(2) / a.pow(2)) - b.pow(2))
        }

        fun SolveYforParabola(x: Float): Float {
            return sqrt(2 * a * x)
        }
    }
}