package com.nexus.boosttestapp.network

import com.google.gson.annotations.SerializedName

class UpdateVoteRequest {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("dir")
    var dir: String? = null
}