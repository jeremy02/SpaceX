package com.demo.spacex.main.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.models.launch_info.Launches
import com.demo.spacex.network.RetrofitProvider
import com.demo.spacex.network.repository.NetworkRepository
import com.demo.spacex.network.services.ApiService
import com.demo.spacex.network.utils.NoNetworkException
import com.demo.spacex.network.utils.ResponseUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String =  MainViewModel::class.java.simpleName

    // using Rx
    private val compositeDisposable = CompositeDisposable()
    private var networkRepository: NetworkRepository = NetworkRepository()

    // used in getting company info
    private val companyInfoResponse = MutableLiveData<ResponseUtil<CompanyInfo>>()
    fun companyInfoLiveData(): LiveData<ResponseUtil<CompanyInfo>> = companyInfoResponse

    // used in getting the launches
    private val launchesResponse = MutableLiveData<ResponseUtil<Launches>>()
    fun launchesLiveData(): LiveData<ResponseUtil<Launches>> = launchesResponse

    val requestModel: MutableLiveData<BuyGoodsRequest> = MutableLiveData()

    data class BuyGoodsRequest(
        var transferMethod: Int = 0,
        var amount: String = "",
        var isBuyGoods: Int = 0,
        var tillNumber: String = "",
        var accountNumber: String = "",
        var businessNumber: String = "",
        var categoryId: Int = -1,
        var categoryName: String = "",
        var subcategoryId: Int = -1,
        var subcategoryName: String = "",
        var narrative: String = "",
        var merchantName: String? = null
    )

    private val apiService: ApiService
        get() {
            return RetrofitProvider.provideRetrofit().create(ApiService::class.java)
        }

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
                        Log.e(TAG, res.toString())
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


    // get the launches
    fun getLaunches(isFirst: Boolean, filterItem: FilterItem) {
        // add the type param to the request
        val filterDatesParams: MutableMap<String, String> = HashMap()
        if(filterItem.startDate!=null){
            filterDatesParams.set("start", filterItem.startDate!!)
        }
        if(filterItem.endDate != null){
            filterDatesParams.set("end", filterItem.endDate!!)
        }

        compositeDisposable.add(
            networkRepository.getLaunches(filterDatesParams, filterItem.launchSuccess)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    launchesResponse.value = ResponseUtil.loading(isFirst)
                }
                .subscribeWith(object : DisposableSingleObserver<Launches?>() {
                    override fun onSuccess(res: Launches) {
                        Log.e(TAG, res.toString())
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}