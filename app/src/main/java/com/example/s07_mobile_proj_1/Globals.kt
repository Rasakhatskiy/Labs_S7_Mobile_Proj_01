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
            val v = (a * b) / sqrt(b.pow(2) + a.pow(2) * tan(r).pow(2))
            return if (d <= 90) v else -v
        }

        fun getEllipseArcLength(): Float {
            val r = d * Math.PI.toFloat() / 180.0f
            val t1 = 0f
            val t2 = atan((a / b) * tan(r))
            return abs(trapezoidalIntegral(t1, t2, 42f))
        }

        private fun ellipseIntegratedFunc(x: Float): Float {
            return sqrt(a.pow(2) * sin(x).pow(2) + b.pow(2) * cos(x).pow(2))
        }


        private fun trapezoidalIntegral(t1: Float, t2: Float, n: Float): Float {
            // Grid spacing
            val h = (t2 - t1) / n

            // Computing sum of first and last terms
            var s: Float = ellipseIntegratedFunc(t1) + ellipseIntegratedFunc(t2)

            // Adding middle terms in above formula
            var i = 1
            while (i < n) {
                s += 2 * ellipseIntegratedFunc(t1 + i * h)
                i++
            }

            // h/2 indicates (b-a)/2n. Multiplying h/2
            // with s.
            return h / 2 * s
        }


        fun getEllipseSectorArea(): Float {
            val r = d * Math.PI.toFloat() / 180.0f
            return abs(0.5f * a * b * (tan((a * tan(r)) / b).pow(-1.0f)))
        }


    }
}