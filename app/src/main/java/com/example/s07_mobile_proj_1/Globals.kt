package com.example.s07_mobile_proj_1

import kotlin.math.*

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

        var d: Float = 0f

        fun solveEllipseY(x: Float): Float {
            return sqrt(b.pow(2) - (b.pow(2) * x.pow(2) / a.pow(2)))
        }

        fun solveHyperbolaY(x: Float): Float {
            return sqrt((b.pow(2) * x.pow(2) / a.pow(2)) - b.pow(2))
        }

        fun solveParabolaY(x: Float): Float {
            return sqrt(2 * a * x)
        }

        fun getEllipsePointByAngle(): Float {
            val r = d * Math.PI.toFloat() / 180.0f
            return (a * b) / sqrt(b.pow(2) + a.pow(2) * tan(r).pow(2))
        }

        fun getEllipseSectorArea(): Float {
            val r = d * Math.PI.toFloat() / 180.0f
            return abs(0.5f * a * b * (tan((a * tan(r)) / b).pow(-1.0f)))
        }

        fun getEllipseArcLength(): Float {
            val r = d * Math.PI.toFloat() / 180.0f
            val t = atan((a / b) * tan(r))
            return sqrt(a.pow(2) * sin(t).pow(2) + b.pow(2) * cos(t).pow(2))
        }
    }
}