package com.demo.spacex.network.repository

import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.models.launch_info.Launches
import com.demo.spacex.models.launch_info.LaunchesResponse
import com.demo.spacex.network.RetrofitProvider
import com.demo.spacex.network.services.ApiService
import io.reactivex.Single

class NetworkRepository {

    // the service for get company info and launches
    private val apiService: ApiService = RetrofitProvider.provideRetrofit().create(ApiService::class.java)

    // call the service for get company info
    fun getCompanyInfo(): Single<CompanyInfo?> {
        return apiService.getCompanyInfo()
    }

    // call the service for get company info
    fun getLaunches(filterSortParams: MutableMap<String, String>?, launchSuccess: Boolean?): Single<List<Launches?>> {
        return apiService.getLaunches(filterSortParams, launchSuccess)
    }

    companion object {

        private var networkRepository: NetworkRepository? = null

        val instance: NetworkRepository?
            get() {
                if (networkRepository == null) {
                    networkRepository = NetworkRepository()
                }
                return networkRepository
            }
    }
}