package com.caretech.servicefocus.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import com.caretech.servicefocus.core.color
import com.caretech.servicefocus.core.icon
import com.haibin.calendarview.CalendarView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : BaseActivity(), com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    override fun layout(): Int = R.layout.activity_calendar


    lateinit var calendarView: CalendarView

    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Calendar"



        calendarView = view().findViewById(R.id.calendarView)
    }

    override fun START() {


        calendarView.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
            override fun onCalendarOutOfRange(calendar: com.haibin.calendarview.Calendar) {
                updateDate(calendar)
            }

            override fun onCalendarSelect(calendar: com.haibin.calendarview.Calendar, isClick: Boolean) {
                updateDate(calendar)
            }

        })
    }

    override fun STOP() {
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    override fun onDateSet(view: com.wdullaer.materialdatetimepicker.date.DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendarView.scrollToCalendar(year, monthOfYear, dayOfMonth, true)
    }

    fun updateDate(calendar: com.haibin.calendarview.Calendar) {
        val date = Date(calendar.timeInMillis)
        supportActionBar!!.title = SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date)
        supportActionBar!!.subtitle = calendar.year.toString()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.calendar_menu, menu)
        menu!!.findItem(R.id.calendarSelect).icon = applicationContext.icon(FontAwesome.Icon.faw_calendar_alt, Color.WHITE, 20)
        menu.findItem(R.id.listEvents).icon = applicationContext.icon(FontAwesome.Icon.faw_list, Color.WHITE, 20)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("ResourceAsColor")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {

            android.R.id.home -> onBackPressed()

            R.id.calendarSelect -> {
                val now = Calendar.getInstance()
                val dpd = DatePickerDialog.newInstance(
                        this@CalendarActivity,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                )

                dpd.accentColor = applicationContext.color(R.color.colorPrimary)
                dpd.show(supportFragmentManager, "Pick Date")
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
