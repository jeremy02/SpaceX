package com.demo.spacex.models.launch_info

// used in getting Launches responses
data class LaunchesResponse(
    val error: String?,
    val launchItems: List<Launches>
) {
    override fun toString(): String {
        return "LaunchesResponse(error=$error, launchItems=$launchItems)"
    }
}