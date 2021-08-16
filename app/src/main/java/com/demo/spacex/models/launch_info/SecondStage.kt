package com.demo.spacex.models.launch_info
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SecondStage {
    @SerializedName("block")
    @Expose
    var block: Int? = null

    @SerializedName("payloads")
    @Expose
    var payloads: List<LaunchPayload>? = null

    override fun toString(): String {
        return "SecondStage(block=$block, payloads=$payloads)"
    }
}