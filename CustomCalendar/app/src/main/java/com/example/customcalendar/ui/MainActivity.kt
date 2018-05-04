package com.example.customcalendar.ui

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.widget.Toast
import butterknife.BindDrawable
import butterknife.BindView

import butterknife.ButterKnife
import com.example.customcalendar.R
import com.example.smartcustomcalendar.ui.SmartCustomCalendar
import com.example.smartcustomcalendar.ui.SmartCustomCalendarListener

class MainActivity : AppCompatActivity(), SmartCustomCalendarListener {

    @BindView(R.id.smartCustomCalendar)
    lateinit var smartCustomCalendar: SmartCustomCalendar

    @BindDrawable(R.drawable.circle)
    lateinit var circle: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setData()
    }

    private fun setData() {
        smartCustomCalendar.initSmartCustomCalendar(this){
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onChangeMonth(month: Int) {
        Toast.makeText(this, month.toString(), Toast.LENGTH_SHORT).show()
    }
}
