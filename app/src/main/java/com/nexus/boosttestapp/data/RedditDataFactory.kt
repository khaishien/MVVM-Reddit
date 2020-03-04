package com.nexus.boosttestapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nexus.boosttestapp.model.SubredditData


class RedditDataFactory() : DataSource.Factory<String, SubredditData>() {


     var mutableLiveData: MutableLiveData<RedditDataSource>? = null
     var redditDataSource: RedditDataSource? = null

    init {
        mutableLiveData = MutableLiveData()
    }

    override fun create(): DataSource<String, SubredditData> {
        redditDataSource = RedditDataSource()
        mutableLiveData!!.postValue(redditDataSource)
        return redditDataSource as RedditDataSource
    }
}