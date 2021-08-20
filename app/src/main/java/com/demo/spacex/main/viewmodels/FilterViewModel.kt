package com.demo.spacex.main.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FilterViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String = FilterViewModel::class.java.simpleName

    // start date in filter
    private val mutableStartDateItem = MutableLiveData<String>()
    val startDate: LiveData<String> get() = mutableStartDateItem

    fun selectStartDate(startDate: String?) {
        mutableStartDateItem.value = startDate
    }

    // end date in filter
    private val mutableEndDateItem = MutableLiveData<String>()
    val endDate: LiveData<String> get() = mutableEndDateItem

    fun selectEndDate(endDate: String?) {
        mutableEndDateItem.value = endDate
    }

    // filter by launch success
    private val mutableLaunchSuccessItem = MutableLiveData<Boolean>()
    val launchSuccess: LiveData<Boolean> get() = mutableLaunchSuccessItem

    fun setLaunchSuccess(launchSuccess: Boolean?) {
        mutableLaunchSuccessItem.value = launchSuccess
    }

    // filter by launch success
    private val mutableSortOrderItem = MutableLiveData<Boolean>()
    val sortOrder: LiveData<Boolean> get() = mutableSortOrderItem

    fun setSortOrder(sortAsc: Boolean?) {
        mutableSortOrderItem.value = sortAsc
    }

    // filter by launch success
    private val mutableCallLaunchesApiFunctionItem = MutableLiveData<Boolean>()
    val callLaunchesApiFunctionLiveData: LiveData<Boolean> get() = mutableCallLaunchesApiFunctionItem

    fun callLaunchesApiFunction(callFunction: Boolean) {
        mutableCallLaunchesApiFunctionItem.value = callFunction
    }
}