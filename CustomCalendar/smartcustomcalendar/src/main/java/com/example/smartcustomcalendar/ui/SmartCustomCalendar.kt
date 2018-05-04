package com.example.smartcustomcalendar.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.smartcustomcalendar.R
import com.example.smartcustomcalendar.model.CalendarDate
import com.example.smartcustomcalendar.util.CalendarUtil
import com.example.smartcustomcalendar.util.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.smar_custom_calendar.view.*
import java.util.*

class SmartCustomCalendar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {


    private var month = Calendar.getInstance().get(Calendar.MONTH)
    private var year = Calendar.getInstance().get(Calendar.YEAR)
    private var listener: ((CalendarDate) -> Unit)? = null

    private var monthAdapter = MonthAdapter(month, year, listener)
    private var smartCustomCalendarListener: SmartCustomCalendarListener? = null

    private var lineVisibility = true
    private var lineDrawable: Int = R.drawable.line_divider
    private var weekDayTextColor = 0
    private var weekDayBackGroundColor = 0

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.smar_custom_calendar, this)

        val array = getContext().obtainStyledAttributes(attrs, R.styleable.SmartCustomCalendar)

        lineVisibility = array.getBoolean(R.styleable.SmartCustomCalendar_lineVisible, true)
        lineDrawable = array.getResourceId(R.styleable.SmartCustomCalendar_lineDrawableColor, 0)

        weekDayTextColor = array.getColor(R.styleable.SmartCustomCalendar_weekDayTextColor, 0)
        weekDayBackGroundColor = array.getColor(R.styleable.SmartCustomCalendar_weekDayBackGroundColor, 0)

        array.recycle()
    }

    @JvmOverloads
    fun initSmartCustomCalendar(smartCustomCalendarListener: SmartCustomCalendarListener, userListener:((CalendarDate) -> Unit)? = null) {
        this.smartCustomCalendarListener = smartCustomCalendarListener

        recyclerView.layoutManager = GridLayoutManager(context, 7)
        recyclerView.setHasFixedSize(false)

        if (lineVisibility)
            recyclerView.addItemDecoration(SimpleDividerItemDecoration(context, lineDrawable))

        currentMonth.text = context.getString(R.string.current_month, CalendarUtil.getMonth(month + 1), year.toString())

        monthAdapter.listener = userListener
        recyclerView.adapter = monthAdapter

        setButtonClick()
    }

    private fun setButtonClick() {
        btnNext.setOnClickListener { onChangeMonth(true) }
        btnBefore.setOnClickListener{ onChangeMonth(false) }
    }

    @JvmOverloads
    fun setBackgroundResourceColor(color: Int) {
        layoutCalendar.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    private fun onChangeMonth(isNext: Boolean) {
        month = if (isNext) month + 1 else month - 1

        if (isNext && month > 11) {
            month = 0
            year += 1
        } else if (month < 0) {
            month = 11
            year -= -1
        }

        currentMonth.text = context.getString(R.string.current_month, CalendarUtil.getMonth(month + 1), year.toString())
        recyclerView.adapter = MonthAdapter(month, year, monthAdapter.listener)

        smartCustomCalendarListener?.onChangeMonth(month + 1)
    }
}