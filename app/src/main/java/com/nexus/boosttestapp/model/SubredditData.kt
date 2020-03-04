package com.nexus.boosttestapp.model

import com.google.gson.annotations.SerializedName


class SubredditData {
    @SerializedName("subreddit")
    var subreddit: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("subreddit_name_prefixed")
    var subredditNamePrefixed: String? = null

    @SerializedName("name")
    var thingsId: String? = null

    @SerializedName("downs")
    var downs: Int? = null

    @SerializedName("ups")
    var ups: Int? = null

    @SerializedName("score")
    var score: Int? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null

    @SerializedName("created")
    var created: Long = 0

    @SerializedName("author")
    var author: String? = null
}