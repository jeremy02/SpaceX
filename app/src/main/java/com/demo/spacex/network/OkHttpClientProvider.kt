package com.demo.spacex.network

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientProvider {

    private val TAG: String =  OkHttpClientProvider::class.java.simpleName

    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()

        // add logging
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        // add connection pools and protocol
        val connectionPool = ConnectionPool(
            20, // max total connections
            5, // connection keep alive time
            TimeUnit.MINUTES // time units measure
        )
        httpClient.connectionPool(connectionPool)

        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }
}