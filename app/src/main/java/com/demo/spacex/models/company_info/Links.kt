package com.demo.spacex.models.company_info

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Links {
    @SerializedName("website")
    @Expose
    var website: String? = null

    @SerializedName("flickr")
    @Expose
    var flickr: String? = null

    @SerializedName("twitter")
    @Expose
    var twitter: String? = null

    @SerializedName("elon_twitter")
    @Expose
    var elonTwitter: String? = null

    override fun toString(): String {
        return "Links(website=$website, flickr=$flickr, twitter=$twitter, elonTwitter=$elonTwitter)"
    }
}