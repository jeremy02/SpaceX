package com.demo.spacex.models.launch_info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Fairings {
    @SerializedName("reused")
    @Expose
    var reused: Boolean? = null

    @SerializedName("recovery_attempt")
    @Expose
    var recoveryAttempt: Boolean? = null

    @SerializedName("recovered")
    @Expose
    var recovered: Boolean? = null

    @SerializedName("ship")
    @Expose
    var ship: String? = null

    override fun toString(): String {
        return "Fairings(reused=$reused, recoveryAttempt=$recoveryAttempt, recovered=$recovered, ship=$ship)"
    }
}