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

    // https://api.spacexdata.com/v3/launches?start=2017-08-01&end=2020-08-22&launch_success=true&sort=flight_number&order=desc
    fun getLaunches(@QueryMap filterDates: MutableMap<String, String>?, @Query("launch_success") launchSuccess: Boolean?): Single<Launches?>
}