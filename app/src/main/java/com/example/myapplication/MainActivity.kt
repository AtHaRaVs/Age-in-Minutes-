package com.example.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var SelectedDate :TextView? = null                                     // declaring date on the screen
    private var ageInMin: TextView? = null                                         // declaring time displaying how many mins are passed since the selected date
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)                // declaring + intializing button
        SelectedDate = findViewById(R.id.SelectedDate)                               // initializing date
        ageInMin = findViewById(R.id.ageInMin)                                       // initializing time
        btnDatePicker.setOnClickListener{
            datePicker()                                                             // main function which will convert date to mins
        }
    }
    private fun datePicker(){
        val myCalendar = Calendar.getInstance()                                      // object from java calender library
        val year = myCalendar.get(Calendar.YEAR)                                     // variable storing year
        val month = myCalendar.get(Calendar.MONTH)                                   // variable storing month (months start from index 0 , thats why +1 )
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)                              // variable storing date
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,year,month,dayOfMonth->
            val dateSelected = "$dayOfMonth/${month+1}/$year"
            SelectedDate?.text = dateSelected
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)                // declaring format for our dates
            val theDate = sdf.parse(dateSelected)                                          // converting the selected date from dialogue box to specific format
            theDate?.let{                                                            // safe casting just for optimization
                val SelectedDateInMins = theDate.time/60000                                //converting date to mili secs then divding to convetr them to mins
                val CurrentDate = System.currentTimeMillis()
                val CurrentDateInMins = CurrentDate/60000
                val differenceInMins = CurrentDateInMins - SelectedDateInMins              //getting the mins spent till that date by difference
                ageInMin?.text = differenceInMins.toString()                               // difference is in long so has to be converted to string
            }
        },
        year,
        month,
        day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000                     // making sure date doesnt exceed current date
        dpd.show()
    }
}