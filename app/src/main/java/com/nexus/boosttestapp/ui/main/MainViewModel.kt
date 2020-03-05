package com.nexus.boosttestapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nexus.boosttestapp.core.BaseViewModel
import com.nexus.boosttestapp.data.RedditDataFactory
import com.nexus.boosttestapp.data.RedditDataSource
import com.nexus.boosttestapp.model.SubredditData
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import com.nexus.boosttestapp.utils.NetworkState
import com.nexus.boosttestapp.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainViewModel(application: Application) : BaseViewModel(application) {

    private var redditDataFactory: RedditDataFactory = RedditDataFactory()
    private var executor: Executor = Executors.newFixedThreadPool(5)
    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null
    var subredditData: LiveData<PagedList<SubredditData>>? = null


    val onFailedUpVoteEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val onFailedDownVoteEvent: SingleLiveEvent<String> = SingleLiveEvent()


    init {
        networkState = Transformations.switchMap(
            redditDataFactory.mutableLiveData!!
        ) { dataSource: RedditDataSource -> dataSource.networkState }

        refreshState = Transformations.switchMap(
            redditDataFactory.mutableLiveData!!
        ) { dataSource: RedditDataSource -> dataSource.initialLoading }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20).build()
        subredditData =
            LivePagedListBuilder(redditDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
    }

    fun refresh() {
        redditDataFactory.redditDataSource?.invalidate()
    }

    fun upvote(thingId: String) {
        if (thingId.isNullOrEmpty()) {
            return
        }
        RedditClient.redditService.updateVote("1", thingId)
            .enqueue(object : Callback<UpdateVoteResponse> {
                override fun onFailure(
                    call: Call<UpdateVoteResponse>,
                    t: Throwable
                ) {
                    onFailedUpVoteEvent.value = thingId
                }

                override fun onResponse(
                    call: Call<UpdateVoteResponse>,
                    response: Response<UpdateVoteResponse>
                ) {
                }

            })
    }


    fun downvote(thingId: String) {
        if (thingId.isNullOrEmpty()) {
            return
        }
        RedditClient.redditService.updateVote("-1", thingId)
            .enqueue(object : Callback<UpdateVoteResponse> {
                override fun onFailure(
                    call: Call<UpdateVoteResponse>,
                    t: Throwable
                ) {
                    onFailedDownVoteEvent.value = thingId
                }

                override fun onResponse(
                    call: Call<UpdateVoteResponse>,
                    response: Response<UpdateVoteResponse>
                ) {
                }

            })
    }

}
