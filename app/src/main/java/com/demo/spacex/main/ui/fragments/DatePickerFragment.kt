package com.demo.spacex.main.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DatePickerFragment : DialogFragment() {

    private var parsedDate:Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if(bundle != null) {
            val dateString = bundle.getString("dialog_date_data")
            if(dateString != null) parsedDate = getFormattedDate(dateString)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Use the current date as the default date in the date picker
        val mCal: Calendar = Calendar.getInstance()

        Log.e("date", parsedDate.toString())

        if(parsedDate != null) {
            mCal.setTime(parsedDate!!)
        }

        val year: Int = mCal.get(Calendar.YEAR)
        val month: Int = mCal.get(Calendar.MONTH)
        val day: Int = mCal.get(Calendar.DAY_OF_MONTH)

        // Create a new DatePickerDialog instance
        val dialog = DatePickerDialog(
            requireContext(),
            AlertDialog.THEME_HOLO_LIGHT,
            parentFragment as OnDateSetListener,
            year,
            month,
            day
        )

        dialog.setTitle(tag)
        dialog.datePicker.tag = tag
        return dialog
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormattedDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = dateFormat.parse(dateString)
            return date
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }
    }
}