package com.demo.spacex.models.launch_info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LaunchSite {
    @SerializedName("site_id")
    @Expose
    var siteId: String? = null

    @SerializedName("site_name")
    @Expose
    var siteName: String? = null

    @SerializedName("site_name_long")
    @Expose
    var siteNameLong: String? = null

    override fun toString(): String {
        return "LaunchSite(siteId=$siteId, siteName=$siteName, siteNameLong=$siteNameLong)"
    }
}