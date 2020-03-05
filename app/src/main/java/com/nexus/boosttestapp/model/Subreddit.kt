package com.nexus.boosttestapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class Subreddit() : Parcelable {

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
    var created: Long = 0

    @SerializedName("author")
    var author: String? = null


    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        subredditNamePrefixed = parcel.readString()
        thingsId = parcel.readString()
        score = parcel.readInt()
        thumbnail = parcel.readString()
        created = parcel.readLong()
        author = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeString(title)
        dest.writeString(subredditNamePrefixed)
        dest.writeString(thingsId)
        dest.writeInt(score ?: 0)
        dest.writeString(thumbnail)
        dest.writeLong(created)
        dest.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Subreddit> {
        override fun createFromParcel(parcel: Parcel): Subreddit {
            return Subreddit(parcel)
        }

        override fun newArray(size: Int): Array<Subreddit?> {
            return arrayOfNulls(size)
        }
    }
}