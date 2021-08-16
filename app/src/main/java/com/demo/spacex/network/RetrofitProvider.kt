package com.demo.spacex.network

import com.demo.spacex.contants.Configuration
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClientProvider.provideOkHttpClient())
            .baseUrl(Configuration.SERVER_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}