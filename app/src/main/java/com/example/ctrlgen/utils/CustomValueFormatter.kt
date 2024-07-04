package com.example.ctrlgen.utils

import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class CustomValueFormatter : ValueFormatter() {
    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
        // Return an empty string to hide the individual slice values
        return ""
    }
}
