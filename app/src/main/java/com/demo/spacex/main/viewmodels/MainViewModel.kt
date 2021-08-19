package com.demo.spacex.main.viewmodels

import android.app.Application
import android.content.ClipData
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.spacex.models.FilterItem
import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.models.launch_info.Launches
import com.demo.spacex.models.launch_info.LaunchesResponse
import com.demo.spacex.network.repository.NetworkRepository
import com.demo.spacex.network.utils.NoNetworkException
import com.demo.spacex.network.utils.ResponseUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String =  MainViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()
    private var networkRepository: NetworkRepository = NetworkRepository()

    // used in getting company info
    private val companyInfoResponse = MutableLiveData<ResponseUtil<CompanyInfo>>()
    fun companyInfoLiveData(): LiveData<ResponseUtil<CompanyInfo>> = companyInfoResponse

    // used in getting the launches info
    private val launchesResponse = MutableLiveData<ResponseUtil<LaunchesResponse>>()
    fun launchesLiveData(): LiveData<ResponseUtil<LaunchesResponse>> = launchesResponse

    // used in setting the clicked launch
    private val mutableSelectedLaunchItem = MutableLiveData<Launches>()
    val selectedLaunchItem: LiveData<Launches> get() = mutableSelectedLaunchItem

    // call the api call to get company info
    fun getCompanyInfo(isFirst: Boolean) {
        compositeDisposable.add(
            networkRepository.getCompanyInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    companyInfoResponse.value = ResponseUtil.loading(isFirst)
                }
                .subscribeWith(object : DisposableSingleObserver<CompanyInfo?>() {
                    override fun onSuccess(res: CompanyInfo) {
//                        Log.e(TAG, res.toString())
                    }

                    override fun onError(e: Throwable) {
                        companyInfoResponse.value = when (e) {
                            is NoNetworkException -> ResponseUtil.networkLost()
                            else -> ResponseUtil.error(e)
                        }
                    }
                })
        )
    }

    // call the api call to get the launches
    fun getLaunches(isFirst: Boolean, filterItem: FilterItem) {
        // add the type param to the request
        val filterSortParams: MutableMap<String, String> = HashMap()

        // start date filter
        if(filterItem.startDate!=null){
            filterSortParams["start"] = filterItem.startDate!!
        }

        // end date filter
        if(filterItem.endDate != null){
            filterSortParams["end"] = filterItem.endDate!!
        }

        // sort by flight_number and whether asc or desc
        if(filterItem.sortOrder != null){
            filterSortParams["sort"] = filterItem.sortBy!!
            if(filterItem.sortOrder!!) {
                // filter in ascending order
                filterSortParams["order"] = "asc"
            }else{
                // filter in descending order
                filterSortParams["order"] = "desc"
            }
        }

        /*
             Please note we have called the param launch_success separately because it is a boolean variable and all the other
             parameter values are strings thats why they are added as a single HashMap of filterParams
         */
        compositeDisposable.add(
            networkRepository.getLaunches(filterSortParams, filterItem.launchSuccess)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    launchesResponse.value = ResponseUtil.loading(isFirst)
                }
                .subscribeWith(object : DisposableSingleObserver<List<Launches>>() {
                    override fun onSuccess(res: List<Launches>) {
                        launchesResponse.value =
                            ResponseUtil.succeed(LaunchesResponse(error = null,launchItems = res), isFirst = isFirst)
                    }
                    override fun onError(e: Throwable) {
                        Log.e("$TAG Error", e.toString())
                        companyInfoResponse.value = when (e) {
                            is NoNetworkException -> ResponseUtil.networkLost()
                            else -> ResponseUtil.error(e)
                        }
                    }
                })
        )
    }

    // setting the selected launch item to view in detail
    fun selectLaunchItem(selectedItem: Launches) {
        mutableSelectedLaunchItem.value = selectedItem
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}