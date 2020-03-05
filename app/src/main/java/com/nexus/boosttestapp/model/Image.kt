package com.nexus.boosttestapp.model

import com.google.gson.annotations.SerializedName

class Image {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("source")
    var source: ImageSource? = null
}