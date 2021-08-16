package com.demo.spacex.network.services

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface ApiService {

    // get company info
    @POST("customer/login")
    fun getCompanyInfo(
        @Body loginRequest: LoginRequestParams
    ): Single<LoginResponse?>
}