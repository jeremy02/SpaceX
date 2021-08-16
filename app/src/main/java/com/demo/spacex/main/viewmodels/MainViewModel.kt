package com.demo.spacex.main.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.spacex.models.company_info.CompanyInfo
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}