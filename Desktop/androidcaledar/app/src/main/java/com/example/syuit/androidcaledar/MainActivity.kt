package jp.co.apps.workout.calendarsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.TextView

import com.example.syuit.androidcaledar.R

class MainActivity : AppCompatActivity() {

    private var titleText: TextView? = null
    private var prevButton: Button? = null
    private var nextButton: Button? = null
    private var mCalendarAdapter: jp.co.apps.workout.calendarsample.CalendarAdapter? = null
    private var calendarGridView: GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleText = findViewById(R.id.titleText)
        prevButton = findViewById(R.id.prevButton)
        prevButton!!.setOnClickListener {
            mCalendarAdapter!!.prevMonth()
            titleText!!.setText(mCalendarAdapter!!.title)
        }
        nextButton = findViewById(R.id.nextButton)
        nextButton!!.setOnClickListener {
            mCalendarAdapter!!.nextMonth()
            titleText!!.setText(mCalendarAdapter!!.title)
        }
        calendarGridView = findViewById(R.id.calendarGridView)
        mCalendarAdapter = jp.co.apps.workout.calendarsample.CalendarAdapter(this)
        calendarGridView!!.adapter = mCalendarAdapter
        titleText!!.setText(mCalendarAdapter!!.title)
    }

}