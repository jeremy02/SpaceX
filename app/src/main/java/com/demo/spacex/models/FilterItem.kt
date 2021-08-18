package com.demo.spacex.models

data class FilterItem(
    var startDate: String? = null,
    var endDate: String? = null,
    var launchSuccess: Boolean? = null,
    var sortBy: String? = null,
    var sortOrder: Boolean? = null
) {
    override fun toString(): String {
        return "FilterItem(startDate=$startDate, endDate=$endDate, launchSuccess=$launchSuccess, sortBy=$sortBy, sortOrder=$sortOrder)"
    }
}