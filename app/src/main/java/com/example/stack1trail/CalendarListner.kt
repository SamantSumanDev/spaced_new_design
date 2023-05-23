package com.example.stack1trail

import android.icu.util.Calendar


interface CalendarListener {
    fun onFirstDateSelected(startDate: Calendar)
    fun onDateRangeSelected(startDate: Calendar, endDate: Calendar)
}
