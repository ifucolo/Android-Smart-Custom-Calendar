package com.example.smartcustomcalendar.util
import com.example.smartcustomcalendar.R
import com.example.smartcustomcalendar.model.CalendarDate
import java.util.*

object CalendarUtil {

    val daysInMonth =  intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    fun getDate(mDaysLastMonth: Int, month: Int, year: Int, mDaysNextMonth: Int, daysToShow: ArrayList<String>, position: Int): CalendarDate {

        return when {
            position < mDaysLastMonth -> {
                CalendarDate(
                        Integer.parseInt(daysToShow[position]),
                        if (month == 0) 11 else month - 1,
                        if (month == 0) year - 1 else year
                )
            }
            position <= daysToShow.size - mDaysNextMonth -> {
                CalendarDate(
                        position - mDaysLastMonth + 1,
                        month,
                        year
                )
            } else -> {
                CalendarDate(
                        Integer.parseInt(daysToShow[position]),
                        if (month == 11) 0 else month + 1,
                        if (month == 11) year + 1 else year
                )
            }
        }
    }

    fun setImageVisiblity(date: CalendarDate, mood: CalendarDate): Boolean = date.day == mood.day && date.month == mood.month - 1 && date.year == mood.year

    private fun isToday(calendarToday: Calendar, date: CalendarDate): Boolean =
         calendarToday.get(Calendar.MONTH) == date.month
                && calendarToday.get(Calendar.YEAR) == date.year
                && calendarToday.get(Calendar.DAY_OF_MONTH) == date.day

    fun getRightColor(calendarToday: Calendar, date: CalendarDate, month: Int): Int =
            when {
                isToday(calendarToday, date) -> R.color.colorAccent
                date.month == month -> R.color.white
                else -> R.color.gray
            }

    fun getDay(day: Int): Int = when (day) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }

    fun daysInMonth(month: Int, mDaysInMonth: IntArray, mYear: Int, mCalendar: GregorianCalendar): Int {
        var daysInMonth = mDaysInMonth[month]

        if (month == 1 && mCalendar.isLeapYear(mYear))
            daysInMonth++

        return daysInMonth
    }

    fun getMonth(month: Int): String {
        when (month) {
            1 -> return "Janeiro"
            2 -> return "Fevereiro"
            3 -> return "Março"
            4 -> return "Abril"
            5 -> return "Maio"
            6 -> return "Junho"
            7 -> return "Julho"
            8 -> return "Agosto"
            9 -> return "Setembro"
            10 -> return "Outubro"
            11 -> return "Novembro"
            12 -> return "Dezembro"
        }
        return ""
    }

}