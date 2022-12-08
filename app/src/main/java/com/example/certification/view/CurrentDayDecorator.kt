package com.example.certification.view

import android.app.Activity
import android.graphics.drawable.Drawable
import com.example.certification.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CurrentDayDecorator(context: Activity?, currentDay: CalendarDay): DayViewDecorator {
    private val drawable: Drawable = context?.getDrawable(R.drawable.selected_date)!!
    private var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}