package com.demo.spacex.network.services

import com.demo.spacex.models.company_info.CompanyInfo
import com.demo.spacex.models.launch_info.Launches
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface ApiService {

    // get company info
    @GET("info")
    fun getCompanyInfo(): Single<CompanyInfo?>

    // get all the launches or by the specifies query params
    @GET("launches")

    // https://api.spacexdata.com/v3/launches?start=2017-08-01&end=2020-08-22&launch_success=true&sort=flight_number&order=desc
    fun getLaunches(@Query("start") startDate: String? = null ,
                    @Query("end") endDate: String? = null,
                    @Query("launch_success") launchSuccess: Boolean?): Single<Launches?>
}