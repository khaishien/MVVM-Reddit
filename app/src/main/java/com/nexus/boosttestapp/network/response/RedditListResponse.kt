package com.nexus.boosttestapp.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nexus.boosttestapp.model.ListData

class RedditListResponse {

    @SerializedName("data")
    @Expose
    val data: ListData? = null
}