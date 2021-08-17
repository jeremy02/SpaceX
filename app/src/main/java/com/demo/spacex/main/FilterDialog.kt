package com.demo.spacex.main

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.demo.spacex.R
import com.demo.spacex.databinding.FilterDialogBinding


class FilterDialog() : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val TAG: String = FilterDialog::class.java.simpleName

    private lateinit var binding : FilterDialogBinding

    private var isLaunchSuccess: Boolean? = null
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // make dialog width match parent
        setStyle(STYLE_NO_TITLE, R.style.FullWidthDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FilterDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // Set transparent background and no title
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init views and show start date picker dialog filter
        binding.startDateLayout.setOnClickListener{
            showDatePickerDialog(true)
        }
        binding.startDateImageView.setOnClickListener{
            showDatePickerDialog(true)
        }

        // init views and show end date picker dialog filter
        binding.endDateLayout.setOnClickListener{
            showDatePickerDialog(false)
        }
        binding.endDateImageView.setOnClickListener{
            showDatePickerDialog(false)
        }

        // launch success toggle switch
        binding.launchSuccessSwitch.setOnCheckedChangeListener { switch, isChecked ->
            // Handle switch checked/unchecked
            isLaunchSuccess = isChecked
        }

        // button cancel click
        binding.dialogCancelBtn.setOnClickListener {
            dismiss()
        }

        // button filter click
        binding.dialogFilterBtn.setOnClickListener {
            val dialogListener = requireActivity() as DialogListener
            dialogListener.onFilterLaunchesDialog(startDate, endDate, isLaunchSuccess)
            dismiss()
        }
    }

    // show date picker dialog
    private fun showDatePickerDialog(isStartDate: Boolean) {
        val newFragment: DialogFragment = DatePickerFragment()
        if(isStartDate) {
            newFragment.show(childFragmentManager, getString(R.string.filter_dialog_start_date))
        }else{
            newFragment.show(childFragmentManager, getString(R.string.filter_dialog_end_date))
        }
    }

    interface DialogListener {
        fun onFilterLaunchesDialog(startDate: String?, endDate: String?, isLaunchSuccess: Boolean?)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val month = monthOfYear + 1
        var formattedM = "" + month
        var formattedDy = "" + dayOfMonth
        if (month < 10) {
            formattedM = "0$month"
        }
        if (dayOfMonth < 10) {
            formattedDy = "0$dayOfMonth"
        }

        // the formatted date
        val setDate = "$year-$formattedM-$formattedDy"

        if (view.tag == getString(R.string.filter_dialog_start_date)) {
            startDate = setDate
            binding.startDateTextView.visibility = View.VISIBLE
            binding.startDateTextView.text = startDate
        } else {
            endDate = setDate
            binding.endDateTextView.visibility = View.VISIBLE
            binding.endDateTextView.text = endDate
        }
    }
}