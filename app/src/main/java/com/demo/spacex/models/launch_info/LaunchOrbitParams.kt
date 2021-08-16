package com.demo.spacex.models.launch_info
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LaunchOrbitParams {
    @SerializedName("reference_system")
    @Expose
    var referenceSystem: String? = null

    @SerializedName("regime")
    @Expose
    var regime: String? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Int? = null

    @SerializedName("semi_major_axis_km")
    @Expose
    var semiMajorAxisKm: Double? = null

    @SerializedName("eccentricity")
    @Expose
    var eccentricity: Double? = null

    @SerializedName("periapsis_km")
    @Expose
    var periapsisKm: Double? = null

    @SerializedName("apoapsis_km")
    @Expose
    var apoapsisKm: Double? = null

    @SerializedName("inclination_deg")
    @Expose
    var inclinationDeg: Double? = null

    @SerializedName("period_min")
    @Expose
    var periodMin: Double? = null

    @SerializedName("lifespan_years")
    @Expose
    var lifespanYears: Int? = null

    @SerializedName("epoch")
    @Expose
    var epoch: String? = null

    @SerializedName("mean_motion")
    @Expose
    var meanMotion: Double? = null

    @SerializedName("raan")
    @Expose
    var raan: Double? = null

    @SerializedName("arg_of_pericenter")
    @Expose
    var argOfPericenter: Double? = null

    @SerializedName("mean_anomaly")
    @Expose
    var meanAnomaly: Double? = null

    override fun toString(): String {
        return "LaunchOrbitParams(referenceSystem=$referenceSystem, regime=$regime, longitude=$longitude, semiMajorAxisKm=$semiMajorAxisKm, eccentricity=$eccentricity, periapsisKm=$periapsisKm, apoapsisKm=$apoapsisKm, inclinationDeg=$inclinationDeg, periodMin=$periodMin, lifespanYears=$lifespanYears, epoch=$epoch, meanMotion=$meanMotion, raan=$raan, argOfPericenter=$argOfPericenter, meanAnomaly=$meanAnomaly)"
    }
}