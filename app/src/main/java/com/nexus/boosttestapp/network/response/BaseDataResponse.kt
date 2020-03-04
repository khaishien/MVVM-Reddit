package com.nexus.boosttestapp.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nexus.boosttestapp.model.SubredditData

class BaseDataResponse {

    @SerializedName("modhash")
    @Expose
    var modhash: String? = null

    @SerializedName("dist")
    @Expose
    var dist: Int = 0

    @SerializedName("children")
    @Expose
    var children: List<SubredditData> = ArrayList()
}