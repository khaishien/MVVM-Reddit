package com.nexus.boosttestapp.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubredditListResponse {

    @SerializedName("kind")
    @Expose
    val kind: String? = null

    @SerializedName("data")
    @Expose
    val data: BaseDataResponse? = null
}