package com.demo.spacex.models.launch_info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Core {
    @SerializedName("core_serial")
    @Expose
    var coreSerial: String? = null

    @SerializedName("flight")
    @Expose
    var flight: Int? = null

    @SerializedName("block")
    @Expose
    var block: Int? = null

    @SerializedName("gridfins")
    @Expose
    var gridfins: Boolean? = null

    @SerializedName("legs")
    @Expose
    var legs: Boolean? = null

    @SerializedName("reused")
    @Expose
    var reused: Boolean? = null

    @SerializedName("land_success")
    @Expose
    var landSuccess: Boolean? = null

    @SerializedName("landing_intent")
    @Expose
    var landingIntent: Boolean? = null

    @SerializedName("landing_type")
    @Expose
    var landingType: String? = null

    @SerializedName("landing_vehicle")
    @Expose
    var landingVehicle: String? = null

    override fun toString(): String {
        return "Core(coreSerial=$coreSerial, flight=$flight, block=$block, gridfins=$gridfins, legs=$legs, reused=$reused, landSuccess=$landSuccess, landingIntent=$landingIntent, landingType=$landingType, landingVehicle=$landingVehicle)"
    }
}