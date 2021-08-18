package com.demo.spacex.network.utils

import com.demo.spacex.models.launch_info.Launches

data class ResponseUtil<T>(
        val status: Status,
        val data: T?,
        val error: Throwable?,
        val isFirst: Boolean = false
) {

    companion object {

        fun <T> loading(isFirst: Boolean) = ResponseUtil<T>(Status.LOADING, null, null, isFirst)

        fun <T> refreshing() = ResponseUtil<T>(Status.REFRESHING, null, null)

        fun <T> empty() = ResponseUtil<T>(Status.EMPTY, null, null)

        fun <T> succeed(data: T, isFirst: Boolean) = ResponseUtil(Status.SUCCEED, data, null, isFirst)

        fun <T> error(t: Throwable) = ResponseUtil<T>(Status.FAILED, null, t)

        fun <T> networkLost() = ResponseUtil<T>(Status.NO_CONNECTION, null, null)
    }
}

enum class Status {
    LOADING,
    REFRESHING,
    EMPTY,
    SUCCEED,
    FAILED,
    NO_CONNECTION
}

object NoNetworkException : Exception()