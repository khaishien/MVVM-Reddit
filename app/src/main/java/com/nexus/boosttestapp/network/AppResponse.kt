package com.nexus.boosttestapp.network

import com.google.gson.annotations.SerializedName


class AppResponse<T> {

    @SerializedName("status")
    val status = false

    @SerializedName("message")
    val message: String? = null

    @SerializedName("data")
    val dataObject: T? = null

}