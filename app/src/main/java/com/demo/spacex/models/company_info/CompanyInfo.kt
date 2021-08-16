package com.demo.spacex.models.company_info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CompanyInfo {
    /*
        Do we have an error response provided ???
        Use this from history/{{id}} api call
     */
    @SerializedName("error")
    @Expose
    var error: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("founder")
    @Expose
    var founder: String? = null

    @SerializedName("founded")
    @Expose
    var founded: Int? = null

    @SerializedName("employees")
    @Expose
    var employees: Int? = null

    @SerializedName("vehicles")
    @Expose
    var vehicles: Int? = null

    @SerializedName("launch_sites")
    @Expose
    var launchSites: Int? = null

    @SerializedName("test_sites")
    @Expose
    var testSites: Int? = null

    @SerializedName("ceo")
    @Expose
    var ceo: String? = null

    @SerializedName("cto")
    @Expose
    var cto: String? = null

    @SerializedName("coo")
    @Expose
    var coo: String? = null

    @SerializedName("cto_propulsion")
    @Expose
    var ctoPropulsion: String? = null

    @SerializedName("valuation")
    @Expose
    var valuation: Long? = null

    @SerializedName("headquarters")
    @Expose
    var headquarters: Headquarters? = null

    @SerializedName("links")
    @Expose
    var links: Links? = null

    @SerializedName("summary")
    @Expose
    var summary: String? = null

    override fun toString(): String {
        return "CompanyInfo(error=$error, name=$name, founder=$founder, founded=$founded, employees=$employees, vehicles=$vehicles, launchSites=$launchSites, testSites=$testSites, ceo=$ceo, cto=$cto, coo=$coo, ctoPropulsion=$ctoPropulsion, valuation=$valuation, headquarters=$headquarters, links=$links, summary=$summary)"
    }
}