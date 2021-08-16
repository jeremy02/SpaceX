package com.demo.spacex.models.launch_info
import com.demo.spacex.models.company_info.Links
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Launches {
    @SerializedName("flight_number")
    @Expose
    var flightNumber: Int? = null

    @SerializedName("mission_name")
    @Expose
    var missionName: String? = null

    @SerializedName("mission_id")
    @Expose
    var missionId: List<Any>? = null

    @SerializedName("launch_year")
    @Expose
    var launchYear: String? = null

    @SerializedName("launch_date_unix")
    @Expose
    var launchDateUnix: Int? = null

    @SerializedName("launch_date_utc")
    @Expose
    var launchDateUtc: String? = null

    @SerializedName("launch_date_local")
    @Expose
    var launchDateLocal: String? = null

    @SerializedName("is_tentative")
    @Expose
    var isTentative: Boolean? = null

    @SerializedName("tentative_max_precision")
    @Expose
    var tentativeMaxPrecision: String? = null

    @SerializedName("tbd")
    @Expose
    var tbd: Boolean? = null

    @SerializedName("launch_window")
    @Expose
    var launchWindow: Int? = null

    @SerializedName("rocket")
    @Expose
    var rocket: Rocket? = null

    @SerializedName("ships")
    @Expose
    var ships: List<String>? = null

    @SerializedName("launch_site")
    @Expose
    var launchSite: LaunchSite? = null

    @SerializedName("launch_success")
    @Expose
    var launchSuccess: Boolean? = null

    @SerializedName("links")
    @Expose
    var links: Links? = null

    @SerializedName("details")
    @Expose
    var details: String? = null

    @SerializedName("upcoming")
    @Expose
    var upcoming: Boolean? = null

    @SerializedName("static_fire_date_utc")
    @Expose
    var staticFireDateUtc: Any? = null

    @SerializedName("static_fire_date_unix")
    @Expose
    var staticFireDateUnix: Any? = null

    @SerializedName("timeline")
    @Expose
    var timeline: Any? = null

    @SerializedName("crew")
    @Expose
    var crew: Any? = null

    @SerializedName("last_date_update")
    @Expose
    var lastDateUpdate: String? = null

    @SerializedName("last_ll_launch_date")
    @Expose
    var lastLlLaunchDate: Any? = null

    @SerializedName("last_ll_update")
    @Expose
    var lastLlUpdate: Any? = null

    @SerializedName("last_wiki_launch_date")
    @Expose
    var lastWikiLaunchDate: String? = null

    @SerializedName("last_wiki_revision")
    @Expose
    var lastWikiRevision: String? = null

    @SerializedName("last_wiki_update")
    @Expose
    var lastWikiUpdate: String? = null

    @SerializedName("launch_date_source")
    @Expose
    var launchDateSource: String? = null

    override fun toString(): String {
        return "Launches(flightNumber=$flightNumber, missionName=$missionName, missionId=$missionId, launchYear=$launchYear, launchDateUnix=$launchDateUnix, launchDateUtc=$launchDateUtc, launchDateLocal=$launchDateLocal, isTentative=$isTentative, tentativeMaxPrecision=$tentativeMaxPrecision, tbd=$tbd, launchWindow=$launchWindow, rocket=$rocket, ships=$ships, launchSite=$launchSite, launchSuccess=$launchSuccess, links=$links, details=$details, upcoming=$upcoming, staticFireDateUtc=$staticFireDateUtc, staticFireDateUnix=$staticFireDateUnix, timeline=$timeline, crew=$crew, lastDateUpdate=$lastDateUpdate, lastLlLaunchDate=$lastLlLaunchDate, lastLlUpdate=$lastLlUpdate, lastWikiLaunchDate=$lastWikiLaunchDate, lastWikiRevision=$lastWikiRevision, lastWikiUpdate=$lastWikiUpdate, launchDateSource=$launchDateSource)"
    }
}