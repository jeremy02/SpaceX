package com.demo.spacex.network.services

import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.models.launch_info.Launches
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface ApiService {

    // get company info
    @GET("info")
    fun getCompanyInfo(): Single<CompanyInfo?>

    // get all the launches or by the specifies query params
    @GET("launches")
    fun getLaunches(@QueryMap filterSortParams: MutableMap<String, String>?, @Query("launch_success") launchSuccess: Boolean?): Single<List<Launches>>
}