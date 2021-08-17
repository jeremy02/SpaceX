package com.demo.spacex.main

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment : DialogFragment() {

    private var date = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Use the current date as the default date in the date picker
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */ // return DatePickerDialog(requireContext(), this, year, month, day)

        return DatePickerDialog(requireContext(), parentFragment as OnDateSetListener?, year, month, day)
    }

    fun onDateSet(view: DatePicker?, yy: Int, mm: Int, dd: Int) {
        populateSetDate(yy, mm + 1, dd)
    }

    fun populateSetDate(year: Int, month: Int, day: Int) {
        date = "$month/$day/$year"
    }

    fun getDate(): String? {
        return date
    }
}