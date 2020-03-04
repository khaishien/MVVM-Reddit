package com.nexus.boosttestapp.network.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UpdateVoteResponse {

    @SerializedName("dir")
    @Expose
    private val voteType = 0
    @SerializedName("id")
    @Expose
    private val id: String? = null
    @SerializedName("rank")
    @Expose
    private val rank = 0
}