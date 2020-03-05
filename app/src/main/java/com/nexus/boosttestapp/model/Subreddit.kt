package com.nexus.boosttestapp.model

import com.google.gson.annotations.SerializedName


class Subreddit {

    @SerializedName("title")
    var title: String? = null

    @SerializedName("subreddit_name_prefixed")
    var subredditNamePrefixed: String? = null

    @SerializedName("name")
    var thingsId: String? = null

    @SerializedName("score")
    var score: Int? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null

    @SerializedName("created")
    var created: Int = 0

    @SerializedName("author")
    var author: String? = null

    @SerializedName("num_comments")
    var numComments: Int = 0

    @SerializedName("preview")
    var preview: PreviewImage? = null


}