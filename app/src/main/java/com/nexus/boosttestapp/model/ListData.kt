package com.nexus.boosttestapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListData {

    @SerializedName("after")
    @Expose
    var after: String? = null

    @SerializedName("before")
    @Expose
    var before: String? = null

    @SerializedName("children")
    @Expose
    var children: List<SubredditData> = ArrayList()
}