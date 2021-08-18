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
import androidx.fragment.app.activityViewModels
import com.demo.spacex.R
import com.demo.spacex.databinding.FilterDialogBinding
import com.demo.spacex.main.viewmodels.FilterViewModel


class FilterDialog() : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val TAG: String = FilterDialog::class.java.simpleName

    private lateinit var binding : FilterDialogBinding

    private val filterViewModel: FilterViewModel by activityViewModels()

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

        // observe start date
        filterViewModel.startDate.observe(viewLifecycleOwner, {
            if(it != null){
                binding.startDateTextView.visibility = View.VISIBLE
                binding.startDateTextView.text = it
            }
        })

        // observe end date
        filterViewModel.endDate.observe(viewLifecycleOwner, {
            if(it != null){
                binding.endDateTextView.visibility = View.VISIBLE
                binding.endDateTextView.text = it
            }
        })

        // observe filter by launch success
        filterViewModel.launchSuccess.observe(viewLifecycleOwner, {
            if(it != null){
                binding.launchSuccessSwitch.isChecked = it
            }
        })

        // observe sort order
        filterViewModel.sortOrderLiveData.observe(viewLifecycleOwner, {
            if(it != null){
                if(it){
                    binding.sortLaunchesRadioGroup.check(R.id.sort_launches_toggle_on)
                }else if(!it){
                    binding.sortLaunchesRadioGroup.check(R.id.sort_launches_toggle_off)
                }else{
                    binding.sortLaunchesRadioGroup.clearCheck()
                }
            }
        })

        // init views
        initViews()
    }

    private fun initViews() {
        // show start date picker dialog filter
        binding.startDateLayout.setOnClickListener{
            showDatePickerDialog(true)
        }
        binding.startDateImageView.setOnClickListener{
            showDatePickerDialog(true)
        }

        // show end date picker dialog filter
        binding.endDateLayout.setOnClickListener{
            showDatePickerDialog(false)
        }
        binding.endDateImageView.setOnClickListener{
            showDatePickerDialog(false)
        }

        // launch success toggle switch
        binding.launchSuccessSwitch.setOnCheckedChangeListener { switch, isChecked ->
            // Handle switch checked/unchecked
            filterViewModel.setLaunchSuccess(isChecked)
        }

        // sort by ascending or descending buttons
        binding.sortLaunchesRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            // val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            if (checkedId == R.id.sort_launches_toggle_on) {
                filterViewModel.setSortOrder(true)
            }

            if (checkedId == R.id.sort_launches_toggle_off) {
                filterViewModel.setSortOrder(false)
            }
        }

        // button cancel click
        binding.dialogCancelBtn.setOnClickListener {
            dismiss()
        }

        // button filter click
        binding.dialogFilterBtn.setOnClickListener {
            // Called when the filter button is clicked
            filterViewModel.callFilterSortFunction(true)
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
            filterViewModel.selectStartDate(setDate)
        } else {
            filterViewModel.selectEndDate(setDate)
        }
    }
}