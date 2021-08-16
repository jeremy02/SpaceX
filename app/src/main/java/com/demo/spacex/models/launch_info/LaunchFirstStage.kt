package com.demo.spacex.models.launch_info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LaunchFirstStage {
    @SerializedName("cores")
    @Expose
    var cores: List<Core>? = null

    override fun toString(): String {
        return "LaunchFirstStage(cores=$cores)"
    }
}