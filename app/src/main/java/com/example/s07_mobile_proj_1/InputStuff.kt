package com.example.s07_mobile_proj_1

import android.text.InputFilter
import androidx.core.text.isDigitsOnly


class InputStuff {
    companion object {
        fun getRealNumberFilter(): InputFilter {
            return InputFilter { source, start, end, dest, dstart, dend ->
                if (dest.indexOfAny(charArrayOf('-')) >= 0 &&
                    source.indexOfAny(charArrayOf('-')) >= 0) {
                    return@InputFilter ""
                }

                if (source == "-" && dstart != 0) {
                    return@InputFilter ""
                }

                if (dest.indexOfAny(charArrayOf('.', ',')) >= 0 &&
                    source.indexOfAny(charArrayOf('.', ',')) >= 0) {
                    return@InputFilter ""
                }

                if ((source == "." || source == ",") && dstart == 0) {
                    return@InputFilter "0."
                }

                if (source == ",") {
                    return@InputFilter "."
                }

                null
            }
        }

        fun isOnlyLetters(word: String): Boolean {
            val regex = "^[[A-Za-z0-9]+ .,_-]*$".toRegex()
            return regex.matches(word)}

        fun getNameFilter(): InputFilter {
            return InputFilter { source, start, end, dest, dstart, dend ->
                if (!isOnlyLetters(source.toString())) {
                    return@InputFilter ""
                }
                null
            }
        }
    }
}