package com.example.smartcustomcalendar.model

data class CalendarDate(val day: Int, val month: Int, val year: Int) {

    override fun toString(): String {
      return "$day/$month/$year"
    }
}