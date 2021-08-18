package com.demo.spacex.models.launch_info
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LaunchPayload {
    @SerializedName("payload_id")
    @Expose
    var payloadId: String? = null

    @SerializedName("norad_id")
    @Expose
    var noradId: List<Int>? = null

    @SerializedName("reused")
    @Expose
    var reused: Boolean? = null

    @SerializedName("customers")
    @Expose
    var customers: List<String>? = null

    @SerializedName("nationality")
    @Expose
    var nationality: String? = null

    @SerializedName("manufacturer")
    @Expose
    var manufacturer: String? = null

    @SerializedName("payload_type")
    @Expose
    var payloadType: String? = null

    @SerializedName("payload_mass_kg")
    @Expose
    var payloadMassKg: Any? = null

    @SerializedName("payload_mass_lbs")
    @Expose
    var payloadMassLbs: Double? = null

    @SerializedName("orbit")
    @Expose
    var orbit: String? = null

    @SerializedName("orbit_params")
    @Expose
    var orbitParams: LaunchOrbitParams? = null

    override fun toString(): String {
        return "LaunchPayload(payloadId=$payloadId, noradId=$noradId, reused=$reused, customers=$customers, nationality=$nationality, manufacturer=$manufacturer, payloadType=$payloadType, payloadMassKg=$payloadMassKg, payloadMassLbs=$payloadMassLbs, orbit=$orbit, orbitParams=$orbitParams)"
    }
}