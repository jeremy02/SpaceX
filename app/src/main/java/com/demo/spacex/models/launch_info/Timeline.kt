package com.demo.spacex.models.launch_info
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Timeline {
    @SerializedName("webcast_liftoff")
    @Expose
    var webcastLiftoff: Int? = null

    @SerializedName("go_for_prop_loading")
    @Expose
    var goForPropLoading: Int? = null

    @SerializedName("rp1_loading")
    @Expose
    var rp1Loading: Int? = null

    @SerializedName("stage1_lox_loading")
    @Expose
    var stage1LoxLoading: Int? = null

    @SerializedName("stage2_lox_loading")
    @Expose
    var stage2LoxLoading: Int? = null

    @SerializedName("engine_chill")
    @Expose
    var engineChill: Int? = null

    @SerializedName("prelaunch_checks")
    @Expose
    var prelaunchChecks: Int? = null

    @SerializedName("propellant_pressurization")
    @Expose
    var propellantPressurization: Int? = null

    @SerializedName("go_for_launch")
    @Expose
    var goForLaunch: Int? = null

    @SerializedName("ignition")
    @Expose
    var ignition: Int? = null

    @SerializedName("liftoff")
    @Expose
    var liftoff: Int? = null

    @SerializedName("maxq")
    @Expose
    var maxq: Int? = null

    @SerializedName("meco")
    @Expose
    var meco: Int? = null

    @SerializedName("stage_sep")
    @Expose
    var stageSep: Int? = null

    @SerializedName("second_stage_ignition")
    @Expose
    var secondStageIgnition: Int? = null

    @SerializedName("fairing_deploy")
    @Expose
    var fairingDeploy: Int? = null

    @SerializedName("first_stage_entry_burn")
    @Expose
    var firstStageEntryBurn: Int? = null

    @SerializedName("seco-1")
    @Expose
    var seco1: Int? = null

    @SerializedName("first_stage_landing")
    @Expose
    var firstStageLanding: Int? = null

    @SerializedName("second_stage_restart")
    @Expose
    var secondStageRestart: Int? = null

    @SerializedName("seco-2")
    @Expose
    var seco2: Int? = null

    @SerializedName("payload_deploy")
    @Expose
    var payloadDeploy: Int? = null

    override fun toString(): String {
        return "Timeline(webcastLiftoff=$webcastLiftoff, goForPropLoading=$goForPropLoading, rp1Loading=$rp1Loading, stage1LoxLoading=$stage1LoxLoading, stage2LoxLoading=$stage2LoxLoading, engineChill=$engineChill, prelaunchChecks=$prelaunchChecks, propellantPressurization=$propellantPressurization, goForLaunch=$goForLaunch, ignition=$ignition, liftoff=$liftoff, maxq=$maxq, meco=$meco, stageSep=$stageSep, secondStageIgnition=$secondStageIgnition, fairingDeploy=$fairingDeploy, firstStageEntryBurn=$firstStageEntryBurn, seco1=$seco1, firstStageLanding=$firstStageLanding, secondStageRestart=$secondStageRestart, seco2=$seco2, payloadDeploy=$payloadDeploy)"
    }
}