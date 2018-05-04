package com.example.smartcustomcalendar.ui

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.customcalendar.extention.visible
import com.example.smartcustomcalendar.util.CalendarUtil
import com.example.smartcustomcalendar.R
import com.example.smartcustomcalendar.model.CalendarDate
import kotlinx.android.synthetic.main.item_calendar.view.*
import java.util.*
import kotlin.collections.ArrayList

class MonthAdapter constructor(
        var month: Int,
        var year: Int,
        var listener: ((CalendarDate) -> Unit)?,
        var listDays: ArrayList<CalendarDate>? = ArrayList()): RecyclerView.Adapter<MonthAdapter.ViewHolder>() {

    private val calendar = GregorianCalendar(year, month, 1)
    private val calendarToday = Calendar.getInstance()
    private var daysLastMonth = 0
    private var daysNextMonth = 0
    private var daysToShow = ArrayList<String>()

    init {
        populateMonth()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.day as TextView
        val img = itemView.img as ImageView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val date = CalendarUtil.getDate(daysLastMonth, month, year, daysNextMonth, daysToShow, position)
        val calendarDates =  listDays?.filterIndexed { _, calendarDate -> calendarDate.month - 1 == date.month}

        //calendarDates?.forEach { holder.img.visible(CalendarUtil.setImageVisiblity(date, it)) }

        holder.day.text = date.day.toString()
        holder.day.setTextColor(ContextCompat.getColor(context, CalendarUtil.getRightColor(calendarToday, date, month)))

        holder.itemView.setOnClickListener {
            listener?.invoke(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false))
    override fun getItemCount(): Int = daysToShow.size

    private fun populateMonth() {
        val firstDay = CalendarUtil.getDay(calendar.get(Calendar.DAY_OF_WEEK)) - 1

        val quantityDaysCurrentMonth = CalendarUtil.daysInMonth(month, CalendarUtil.daysInMonth, year, calendar)
        val quantityDaysLastMonth = CalendarUtil.daysInMonth(if (month == 0) 11 else month - 1, CalendarUtil.daysInMonth, year, calendar) - firstDay

        (1..firstDay).forEach { daysToShow.add((quantityDaysLastMonth + it).toString()) }
        (1..quantityDaysCurrentMonth).forEach { daysToShow.add(it.toString()) }

        daysLastMonth = firstDay
        daysNextMonth = 1

        while (daysToShow.size % 7 != 0) {
            daysToShow.add(daysNextMonth.toString())
            daysNextMonth++
        }
    }
}